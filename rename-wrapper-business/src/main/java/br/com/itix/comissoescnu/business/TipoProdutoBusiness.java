package br.com.itix.comissoescnu.business;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.dao.TipoProdutoDAO;
import br.com.itix.comissoescnu.entity.TipoProduto;
import br.com.itix.xdk.business.BaseBusiness;

@Stateless
public class TipoProdutoBusiness extends BaseBusiness  {

	@EJB
	private TipoProdutoDAO tipoProdutoDAO;


	public Long markIntegration( UUID tag ) throws Exception {

		tipoProdutoDAO.markIntegration( tag );

		return tipoProdutoDAO.getCountToIntegration( tag );

	}

	public void unmarkIntegration( List<TipoProduto> legacy ) throws Exception {

		tipoProdutoDAO.unmarkIntegration( legacy );

	}

	public List<TipoProduto> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		return tipoProdutoDAO.getFromLegacy( tag, page, pageSize );

	}

}
