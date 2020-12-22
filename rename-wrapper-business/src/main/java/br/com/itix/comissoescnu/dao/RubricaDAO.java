package br.com.itix.comissoescnu.dao;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;

import br.com.itix.comissoescnu.entity.Rubrica;
import br.com.itix.xdk.business.BaseDAOImpl;

@Stateless
public class RubricaDAO extends BaseDAOImpl<Rubrica> {
	
	public void markIntegration( UUID tag ) throws Exception {
		
		try {
			
			StringBuilder hql = new StringBuilder();
			
			hql.append( "insert into WrapperRubrica (idRubrica, tag, dataAlteracao)" );
			hql.append( "select COD_TIPO_RUBRICA, :pTag, r.DATA_ALTERACAO " );
			hql.append( " from TabRubrica r " );
			hql.append( " where not exists (select 1 from WrapperRubrica wr " );
			hql.append( "                    where wr.idRubrica = r.COD_TIPO_RUBRICA " );
			hql.append( "                     and r.DATA_ALTERACAO = wr.dataAlteracao)" );
			
			var query = getEntityManager().createNativeQuery( hql.toString() );
			query.setParameter( "pTag", tag );
			query.setMaxResults( 1500 );
			
			super.executeQuery( query );
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			throw e;
			
		}
		
	}
	
	public void unmarkIntegration( List<Rubrica> rubricas ) throws Exception {
		
		try {
			
			for( Rubrica r : rubricas ) {
				
				var query = getEntityManager().createQuery( "delete WrapperRubrica where idRubrica = :idRubrica" );
				query.setParameter( "idRubrica", r.getId() );
				query.executeUpdate();
			}
		} catch ( Exception e ) {
				
			e.printStackTrace();
			throw e;
				
		}
			
	}
	
	public Long getCountToIntegration( UUID tag ) throws Exception {
		
		var query = getEntityManager().createQuery( "select count(wr.id) from WrapperRubrica wr where wr.tag = :pTag" );
		query.setParameter( "pTag", tag );
		
		var total = super.<Long>executeQueryListCustomized( query );
		return total.get( 0 );
		
	}
	
	public List<Rubrica> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {
		
		try {
			
			var stringBuilder = new StringBuilder();
			stringBuilder.append( "select x " );
			stringBuilder.append( "  from Rubrica x" );
			stringBuilder.append( "  join WrapperRubrica wr on wr.idRubrica = x.id" );
			stringBuilder.append( " where wr.tag = :pTag" );
			
			var query = getEntityManager().createQuery( stringBuilder.toString() );
			query.setMaxResults( pageSize );
			query.setFirstResult( (page * pageSize) - pageSize );
			query.setParameter( "pTag", tag );
			
			return super.<Rubrica>executeQueryListCustomized( query );
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			throw e;
			
		}
		
	}
	
}
