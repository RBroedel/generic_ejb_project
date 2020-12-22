package br.com.itix.comissoescnu.dao;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;

import br.com.itix.comissoescnu.entity.Empresa;
import br.com.itix.xdk.business.BaseDAOImpl;

@Stateless
public class EmpresaDAO extends BaseDAOImpl<Empresa> {
	
	public void markIntegration( UUID tag ) throws Exception {

		try {

			StringBuilder hql = new StringBuilder();

			hql.append( "insert into WrapperEmpresa (idEmpresa, tag, dataAlteracao) " );
			hql.append( "select COD_EMPRESA_CONTRATO, :pTag, a.DATA_ALTERACAO " );
			hql.append( "  from TabEmpresa a " );
			hql.append( " where not exists (select 1 from WrapperEmpresa wr " );
			hql.append( " 					 where wr.idEmpresa = a.COD_EMPRESA_CONTRATO " );
			hql.append( " 					   and a.DATA_ALTERACAO = wr.dataAlteracao)" );

			var query = getEntityManager().createNativeQuery( hql.toString() ).setMaxResults( 1500 );
			query.setParameter( "pTag", tag );

			super.executeQuery( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}


	public void unmarkIntegration( List<Empresa> list ) throws Exception {

		try {

			for( var l : list ) {

				var query = getEntityManager().createQuery( "delete WrapperEmpresa where idEmpresa = :idEmpresa " );
				query.setParameter( "idEmpresa", l.getId() );
				query.executeUpdate();

			}

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}


	public Long getCountToIntegration( UUID tag ) throws Exception {

		var query = getEntityManager().createQuery( "select count(wr.id) from WrapperEmpresa  wr where wr.tag = :pTag" );
		query.setParameter( "pTag", tag );

		var total = super.<Long>executeQueryListCustomized( query );
		return total.get( 0 );

	}


	public List<Empresa> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		try {

			var stringBuilder = new StringBuilder();
			stringBuilder.append( "select x " );
			stringBuilder.append( "  from Empresa  x" );
			stringBuilder.append( "  join WrapperEmpresa  wr on wr.idEmpresa  = x.id" );
			stringBuilder.append( " where wr.tag = :pTag" );

			var query = getEntityManager().createQuery( stringBuilder.toString() );
			query.setMaxResults( pageSize );
			query.setFirstResult( ( page * pageSize ) - pageSize );
			query.setParameter( "pTag", tag );

			return super.<Empresa>executeQueryListCustomized( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}

}
