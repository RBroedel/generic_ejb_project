package br.com.itix.comissoescnu.facade;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.business.ContratoBusiness;
import br.com.itix.comissoescnu.dto.ContratoDTO;
import br.com.itix.comissoescnu.dto.LoadLegacyMessage;
import br.com.itix.comissoescnu.entity.Contrato;
import br.com.itix.comissoescnu.mq.QueueNames;
import br.com.itix.xdk.job.mq.enqueue.DefaultEnqueue;
import br.com.itix.xdk.util.ModelMapperUtils;

@Stateless
public class ContratoFacade {
	
	@EJB
	private QueueNames queueNames;
	
	@EJB
	private ContratoBusiness contratoBusiness;
	
	public void load() {

		try {
			
			var tag = UUID.randomUUID();
			
			var count = this.contratoBusiness.markIntegration( tag );
			
			if( count > 0 ) {
				
				int pageSize = 10000;
				int pages = ( int ) Math.ceil( count / new BigDecimal( pageSize ).doubleValue() );
				
				var loadLegacyMessage = new LoadLegacyMessage<ContratoDTO>();
				loadLegacyMessage.setTag( tag );
				
				for( int page = 1; page <= pages; page++) {
					
					List<Contrato> legacy = this.contratoBusiness.getFromLegacy( tag, page, pageSize );
					
					loadLegacyMessage.setMessage( String.format( "Sucesso. Recebendo mensagem %d de %d", page, pages ) );
					var listDTO = ModelMapperUtils.mapAll( legacy, ContratoDTO.class );
					loadLegacyMessage.setLegacy( listDTO );
					
					try {
						
						new DefaultEnqueue().postMessageToQueue( queueNames.QUEUE_LOAD_CONTRATOS, loadLegacyMessage, loadLegacyMessage.getClass() );
						
					} catch ( Exception e ) {
						
						this.contratoBusiness.unmarkIntegration( legacy );
						
					}
					
				}
				
			}
			
		} catch ( Exception ex ) {
			
			ex.printStackTrace();
		}
		
	}
	
}
