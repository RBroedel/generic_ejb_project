package br.com.itix.comissoescnu.business;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.itix.comissoescnu.dao.RubricaDAO;
import br.com.itix.comissoescnu.entity.Rubrica;
import br.com.itix.xdk.business.BaseBusiness;

@Stateless
public class RubricaBusiness extends BaseBusiness {
	
	@EJB
	private RubricaDAO rubricaDAO;
	
	public RubricaBusiness() {}
	
	public RubricaBusiness( RubricaDAO rubricaDAO ) {
		this.rubricaDAO = rubricaDAO;
	}
	
	public Long markIntegration( UUID tag ) throws Exception {
		
		rubricaDAO.markIntegration( tag );
		
		return rubricaDAO.getCountToIntegration( tag );
		
	}
	
	public void unmarkIntegration( List<Rubrica> legacy ) throws Exception {
		
		rubricaDAO.unmarkIntegration( legacy );
		
	}
	
	public List<Rubrica> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {
		
		return rubricaDAO.getFromLegacy( tag, page, pageSize );
		
	}

}
