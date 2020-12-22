package br.com.itix.comissoescnu.dao;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;

import br.com.itix.comissoescnu.entity.Contrato;
import br.com.itix.xdk.business.BaseDAOImpl;

@Stateless
public class ContratoDAO extends BaseDAOImpl<Contrato>{

	public void markIntegration( UUID tag ) throws Exception {
		
		try {

			StringBuilder hql = new StringBuilder();
			
			hql.append( "insert into WrapperContrato (idContrato, tag, dataAlteracao) " );
			hql.append( "select c.COD_CONTRATO, :pTag, c.DATA_ALTERACAO " );
			hql.append( " from CARGO_CONTRATO c " );
			hql.append( " where not exists ( select 1 from WrapperContrato wr " );
			hql.append( "                     where wr.idContrato = c.COD_CONTRATO " );
			hql.append( "                       and c.DATA_ALTERACAO = wr.dataAlteracao)" );
			
			var query = getEntityManager().createNativeQuery( hql.toString() ).setMaxResults( 1500 );;
			query.setParameter( "pTag", tag );
			
			super.executeQuery( query );
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			throw e;
			
		}
		
	}
	
	public void unmarkIntegration( List<Contrato> contratos ) throws Exception {
		
		try {
			
			for( Contrato c : contratos ) {
				
				var query = getEntityManager().createQuery( "delete WrapperContrato where idContrato = :idContrato" );
				query.setParameter( "idContrato", c.getId() );
				query.executeUpdate();
				
			}
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			throw e;
			
		}
		
	}
	
	public Long getCountToIntegration( UUID tag ) throws Exception {
		
		var query = getEntityManager().createQuery( "select count(wr.id) from WrapperContrato wr where wr.tag = :pTag" );
		query.setParameter( "pTag", tag );
		
		var total = super.<Long>executeQueryListCustomized( query );
		return total.get( 0 );
		
	}
	
	public List<Contrato> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {
		
		try {
			
			var stringBuilder = new StringBuilder();
			stringBuilder.append( "select x " );
			stringBuilder.append( "  from Contrato x" );
			stringBuilder.append( "  join WrapperContrato wr on wr.idContrato = x.id" );
			stringBuilder.append( " where wr.tag = :pTag");
			
			var query = getEntityManager().createQuery( stringBuilder.toString() );
			query.setMaxResults( pageSize );
			query.setFirstResult( ( page * pageSize ) - pageSize );
			query.setParameter( "pTag", tag );
			
			return super.<Contrato>executeQueryListCustomized( query );
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			throw e;
			
		}
		
	}

}
