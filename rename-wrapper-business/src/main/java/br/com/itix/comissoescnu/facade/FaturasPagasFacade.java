package br.com.itix.comissoescnu.facade;

import java.math.BigDecimal;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.business.FaturasPagasBusiness;
import br.com.itix.comissoescnu.dto.FaturasPagasDTO;
import br.com.itix.comissoescnu.dto.LoadLegacyMessage;
import br.com.itix.comissoescnu.mq.QueueNames;
import br.com.itix.xdk.job.mq.enqueue.DefaultEnqueue;
import br.com.itix.xdk.util.ModelMapperUtils;

@Stateless
public class FaturasPagasFacade {

	@EJB
	private QueueNames queueNames;

	@EJB
	private FaturasPagasBusiness faturasPagasBusiness;

	public void load() {
		try {

			var tag = UUID.randomUUID();

			var count = faturasPagasBusiness.markIntegration( tag );

			if( count > 0 ) {

				int pageSize = 10000;
				int pages = ( int ) Math.ceil( count / new BigDecimal( pageSize ).doubleValue() );

				var loadLegacyMessage = new LoadLegacyMessage<FaturasPagasDTO>();
				loadLegacyMessage.setTag( tag );

				for( int page = 1; page <= pages; page++ ) {

					var legacy = faturasPagasBusiness.getFromLegacy( tag, page, pageSize );

					loadLegacyMessage.setMessage( String.format( "Sucesso. Recebendo mensagem %d	 de %d.", page, pages ) );
					loadLegacyMessage.setLegacy( ModelMapperUtils.mapAll( legacy, FaturasPagasDTO.class ) );

					try {

						new DefaultEnqueue().postMessageToQueue( queueNames.QUEUE_LOAD_FATURAS_PAGAS, loadLegacyMessage, loadLegacyMessage.getClass() );

					} catch ( Exception ex ) {

						faturasPagasBusiness.unmarkIntegration( legacy );

					}

				}

			}

		} catch ( Exception ex ) {

			ex.printStackTrace();

		}
	}
}
