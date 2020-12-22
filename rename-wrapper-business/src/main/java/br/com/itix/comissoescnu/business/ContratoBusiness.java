package br.com.itix.comissoescnu.business;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.dao.ContratoDAO;
import br.com.itix.comissoescnu.entity.Contrato;
import br.com.itix.xdk.business.BaseBusiness;

@Stateless
public class ContratoBusiness extends BaseBusiness {

	@EJB
	private ContratoDAO contratoDAO;
	
	public Long markIntegration( UUID tag ) throws Exception {

		contratoDAO.markIntegration( tag );
		
		return contratoDAO.getCountToIntegration( tag );
	
	}
	
	public void unmarkIntegration( List<Contrato> legacy ) throws Exception {
		
		contratoDAO.unmarkIntegration( legacy );
		
	}
	
	public List<Contrato> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {
		
		return contratoDAO.getFromLegacy( tag, page, pageSize );
		
	}
	
}
