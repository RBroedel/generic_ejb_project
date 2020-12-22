package br.com.itix.comissoescnu.dao;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;

import br.com.itix.comissoescnu.entity.UnidadeNegocio;
import br.com.itix.xdk.business.BaseDAOImpl;

@Stateless
public class UnidadeNegocioDAO extends BaseDAOImpl<UnidadeNegocio> {

	public void markIntegration( UUID tag ) throws Exception {

		try {

			StringBuilder hql = new StringBuilder();

			hql.append( "insert into WrapperUnidadeNegocio ( idUnidadeNegocio, tag, dataAlteracao)" );
			hql.append( "select COD_UNIDADE_DE_NEGOCIO, :pTag, t.DT_ALTERACAO " );
			hql.append( " from TabUnidadeNegocio t " );
			hql.append( " where not exists (select 1 from WrapperUnidadeNegocio w " );
			hql.append( " 					 where w.idUnidadeNegocio = t.COD_UNIDADE_DE_NEGOCIO " );
			hql.append( " 					   and t.DT_ALTERACAO = w.dataAlteracao)" );

			var query = getEntityManager().createNativeQuery( hql.toString() );
			query.setParameter( "pTag", tag );
			query.setMaxResults( 1500 );

			super.executeQuery( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}

	public void unmarkIntegration( List<UnidadeNegocio> unidadeNegocios ) throws Exception {

		try {

			for( UnidadeNegocio un : unidadeNegocios ) {

				var query = getEntityManager().createQuery( "delete WrapperUnidadeNegocio where idUnidadeNegocio = :idUnidadeNegocio" );
				query.setParameter( "idUnidadeNegocio", un.getId() );
				query.executeUpdate();

			}

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}

	public Long getCountToIntegration( UUID tag ) throws Exception {

		var query = getEntityManager().createQuery( "select count(w.id) from WrapperUnidadeNegocio w where w.tag = :pTag" );
		query.setParameter( "pTag", tag );

		var total = super.<Long>executeQueryListCustomized( query );
		return total.get( 0 );

	}

	public List<UnidadeNegocio> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		try {

			var stringBuilder = new StringBuilder();
			stringBuilder.append( "select t " );
			stringBuilder.append( "  from UnidadeNegocio t" );
			stringBuilder.append( "  join WrapperUnidadeNegocio w on w.idUnidadeNegocio = t.id" );
			stringBuilder.append( " where w.tag = :pTag" );

			var query = getEntityManager().createQuery( stringBuilder.toString() );
			query.setMaxResults( pageSize );
			query.setFirstResult( ( page * pageSize ) - pageSize );
			query.setParameter( "pTag", tag );

			return super.<UnidadeNegocio>executeQueryListCustomized( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}
}
