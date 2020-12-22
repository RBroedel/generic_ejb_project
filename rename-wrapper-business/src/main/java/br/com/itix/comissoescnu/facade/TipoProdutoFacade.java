package br.com.itix.comissoescnu.facade;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.business.TipoProdutoBusiness;
import br.com.itix.comissoescnu.dto.LoadLegacyMessage;
import br.com.itix.comissoescnu.dto.TipoProdutoDTO;
import br.com.itix.comissoescnu.entity.TipoProduto;
import br.com.itix.comissoescnu.mq.QueueNames;
import br.com.itix.xdk.job.mq.enqueue.DefaultEnqueue;
import br.com.itix.xdk.util.ModelMapperUtils;

@Stateless
public class TipoProdutoFacade {

	@EJB
	private QueueNames queueNames;

	@EJB
	private TipoProdutoBusiness tipoProdutoBusiness;

	public void load() {
		try {

			var tag = UUID.randomUUID();

			var count = tipoProdutoBusiness.markIntegration( tag );

			if( count > 0 ) {

				int pageSize = 10000;
				int pages = ( int ) Math.ceil( count / new BigDecimal( pageSize ).doubleValue() );

				var loadLegacyMessage = new LoadLegacyMessage<TipoProdutoDTO>();
				loadLegacyMessage.setTag( tag );

				for( int page = 1; page <= pages; page++ ) {

					List<TipoProduto> legacy = tipoProdutoBusiness.getFromLegacy( tag, page, pageSize );

					loadLegacyMessage.setMessage( String.format( "Sucesso. Recebendo mensagem %d de %d.", page, pages ) );
					var listDTO = ModelMapperUtils.mapAll( legacy, TipoProdutoDTO.class );
					loadLegacyMessage.setLegacy( listDTO );

					try {

						new DefaultEnqueue().postMessageToQueue( queueNames.QUEUE_LOAD_TIPO_PRODUTO, loadLegacyMessage, loadLegacyMessage.getClass() );

					} catch ( Exception ex ) {

						tipoProdutoBusiness.unmarkIntegration( legacy );

					}

				}

			}

		} catch ( Exception ex ) {

			ex.printStackTrace();

		}

	}

}
