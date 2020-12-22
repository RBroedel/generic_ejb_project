package br.com.itix.comissoescnu.facade;

import java.math.BigDecimal;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.business.RubricaBusiness;
import br.com.itix.comissoescnu.dto.LoadLegacyMessage;
import br.com.itix.comissoescnu.dto.RubricaDTO;
import br.com.itix.comissoescnu.mq.QueueNames;
import br.com.itix.xdk.job.mq.enqueue.DefaultEnqueue;
import br.com.itix.xdk.util.ModelMapperUtils;

@Stateless
public class RubricaFacade {

	@EJB
	private QueueNames queueNames;
	
	@EJB
	private RubricaBusiness rubricaBusiness;
	
	public void load() {
		
		try {
			
			var tag = UUID.randomUUID();
			
			var count = this.rubricaBusiness.markIntegration( tag );
			
			if( count > 0 ) {
				
				int pageSize = 10000;
				int pages = ( int ) Math.ceil( count / new BigDecimal( pageSize ).doubleValue() );
				
				var loadLegacyMessage = new LoadLegacyMessage<RubricaDTO>();
				loadLegacyMessage.setTag( tag );
				
				for( int page = 1; page <= pages; page++ ) {
					
					var legacy = this.rubricaBusiness.getFromLegacy( tag, page, pageSize );
					
					loadLegacyMessage.setMessage( String.format( "Sucesso. Recebendo mensagem %d de %d.", page, pages ) );
					loadLegacyMessage.setLegacy( ModelMapperUtils.mapAll( legacy, RubricaDTO.class ) );
					
					try {
						
						new DefaultEnqueue().postMessageToQueue( queueNames.QUEUE_LOAD_RUBRICAS, loadLegacyMessage, loadLegacyMessage.getClass() );
						
					} catch ( Exception ex ) {
						this.rubricaBusiness.unmarkIntegration( legacy );
					}
					
				}
				
				
			}
			
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		
	}
	
}
