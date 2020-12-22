package br.com.itix.comissoescnu.business;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.dao.ProdutoDAO;
import br.com.itix.comissoescnu.entity.Produto;
import br.com.itix.xdk.business.BaseBusiness;

@Stateless
public class ProdutoBusiness extends BaseBusiness  {

	@EJB
	private ProdutoDAO produtoDAO;


	public Long markIntegration( UUID tag ) throws Exception {

		produtoDAO.markIntegration( tag );

		return produtoDAO.getCountToIntegration( tag );

	}

	public void unmarkIntegration( List<Produto> legacy ) throws Exception {

		produtoDAO.unmarkIntegration( legacy );

	}

	public List<Produto> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		return produtoDAO.getFromLegacy( tag, page, pageSize );

	}

}
