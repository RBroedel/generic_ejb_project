package br.com.itix.comissoescnu.business;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.dao.FaturasPagasDAO;
import br.com.itix.comissoescnu.entity.FaturasPagas;
import br.com.itix.xdk.business.BaseBusiness;

@Stateless
public class FaturasPagasBusiness extends BaseBusiness {

	@EJB
	private FaturasPagasDAO faturasPagasDAO;

	public Long markIntegration( UUID tag ) throws Exception {

		faturasPagasDAO.markIntegration( tag );

		return faturasPagasDAO.getCountToIntegration( tag );

	}

	public void unmarkIntegration( List<FaturasPagas> legacy ) throws Exception {

		faturasPagasDAO.unmarkIntegration( legacy );

	}

	public List<FaturasPagas> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		return faturasPagasDAO.getFromLegacy( tag, page, pageSize );

	}
}
