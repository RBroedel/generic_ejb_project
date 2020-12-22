package br.com.itix.comissoescnu.business;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.dao.EmpresaDAO;
import br.com.itix.comissoescnu.entity.Empresa;
import br.com.itix.xdk.business.BaseBusiness;

@Stateless
public class EmpresaBusiness extends BaseBusiness  {

	@EJB
	private EmpresaDAO empresaDAO;


	public Long markIntegration( UUID tag ) throws Exception {

		empresaDAO.markIntegration( tag );

		return empresaDAO.getCountToIntegration( tag );

	}

	public void unmarkIntegration( List<Empresa> legacy ) throws Exception {

		empresaDAO.unmarkIntegration( legacy );

	}

	public List<Empresa> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		return empresaDAO.getFromLegacy( tag, page, pageSize );

	}

}
