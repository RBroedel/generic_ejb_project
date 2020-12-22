package br.com.itix.comissoescnu.business;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.dao.UnidadeNegocioDAO;
import br.com.itix.comissoescnu.entity.UnidadeNegocio;
import br.com.itix.xdk.business.BaseBusiness;

@Stateless
public class UnidadeNegocioBusiness extends BaseBusiness {

	@EJB
	private UnidadeNegocioDAO unidadeNegocioDAO;

	public Long markIntegration( UUID tag ) throws Exception {

		unidadeNegocioDAO.markIntegration( tag );

		return unidadeNegocioDAO.getCountToIntegration( tag );

	}

	public void unmarkIntegration( List<UnidadeNegocio> legacy ) throws Exception {

		unidadeNegocioDAO.unmarkIntegration( legacy );

	}

	public List<UnidadeNegocio> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		return unidadeNegocioDAO.getFromLegacy( tag, page, pageSize );

	}
}
