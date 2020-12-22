package br.com.itix.comissoescnu.facade;

import java.math.BigDecimal;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.business.UnidadeNegocioBusiness;
import br.com.itix.comissoescnu.dto.LoadLegacyMessage;
import br.com.itix.comissoescnu.dto.UnidadeNegocioDTO;
import br.com.itix.comissoescnu.mq.QueueNames;
import br.com.itix.xdk.job.mq.enqueue.DefaultEnqueue;
import br.com.itix.xdk.util.ModelMapperUtils;

@Stateless
public class UnidadeNegocioFacade {

	@EJB
	private QueueNames queueNames;

	@EJB
	private UnidadeNegocioBusiness unidadeNegocioBusiness;

	public void load() {
		try {
			var tag = UUID.randomUUID();
			var count = this.unidadeNegocioBusiness.markIntegration( tag );

			if( count > 0 ) {

				int pageSize = 10000;
				int pages = ( int ) Math.ceil( count / new BigDecimal( pageSize ).doubleValue() );

				var loadLegacyMessage = new LoadLegacyMessage<UnidadeNegocioDTO>();
				loadLegacyMessage.setTag( tag );

				for( int page = 1; page <= pages; page++ ) {

					var legacy = this.unidadeNegocioBusiness.getFromLegacy( tag, page, pageSize );

					loadLegacyMessage.setMessage( String.format( "Sucesso. Recebendo mensagem %d	 de %d.", page, pages ) );
					loadLegacyMessage.setLegacy( ModelMapperUtils.mapAll( legacy, UnidadeNegocioDTO.class ) );

					try {

						new DefaultEnqueue().postMessageToQueue( queueNames.QUEUE_LOAD_UNIDADE_NEGOCIO, loadLegacyMessage, loadLegacyMessage.getClass() );

					} catch ( Exception ex ) {

						this.unidadeNegocioBusiness.unmarkIntegration( legacy );

					}

				}
			}
		} catch ( Exception ex ) {

			ex.printStackTrace();
		}
	}

}
