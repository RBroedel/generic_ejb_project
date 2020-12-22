package br.com.itix.comissoescnu.business;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.dao.BeneficiarioDAO;
import br.com.itix.comissoescnu.entity.Beneficiario;
import br.com.itix.xdk.business.BaseBusiness;

@Stateless
public class BeneficiarioBusiness extends BaseBusiness  {

	@EJB
	private BeneficiarioDAO beneficiarioDAO;


	public Long markIntegration( UUID tag ) throws Exception {

		beneficiarioDAO.markIntegration( tag );

		return beneficiarioDAO.getCountToIntegration( tag );

	}

	public void unmarkIntegration( List<Beneficiario> legacy ) throws Exception {

		beneficiarioDAO.unmarkIntegration( legacy );

	}

	public List<Beneficiario> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		return beneficiarioDAO.getFromLegacy( tag, page, pageSize );

	}

}
