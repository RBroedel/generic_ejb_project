package br.com.itix.comissoescnu.dao;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;

import br.com.itix.comissoescnu.entity.FaturasPagas;
import br.com.itix.xdk.business.BaseDAOImpl;

@Stateless
public class FaturasPagasDAO extends BaseDAOImpl<FaturasPagas> {

	public void markIntegration( UUID tag ) throws Exception {

		try {

			StringBuilder hql = new StringBuilder();

			hql.append( "insert into WrapperFaturasPagas ( idFaturasPagas, tag, dataAlteracao)" );
			hql.append( "select CODIGO_FATURA, :pTag, t.DATA_ALTERACAO " );
			hql.append( " from TabFaturasPagas t " );
			hql.append( " where not exists (select 1 from WrapperFaturasPagas w " );
			hql.append( " 					 where w.idFaturasPagas = t.CODIGO_FATURA " );
			hql.append( " 					   and t.DATA_ALTERACAO = w.dataAlteracao)" );

			var query = getEntityManager().createNativeQuery( hql.toString() );
			query.setParameter( "pTag", tag );
			query.setMaxResults( 1500 );

			super.executeQuery( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}

	public void unmarkIntegration( List<FaturasPagas> faturasPagas ) throws Exception {

		try {

			for( FaturasPagas fp : faturasPagas ) {

				var query = getEntityManager().createQuery( "delete WrapperFaturasPagas where idFaturasPagas = :idFaturasPagas" );
				query.setParameter( "idFaturasPagas", fp.getId() );
				query.executeUpdate();

			}

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}

	public Long getCountToIntegration( UUID tag ) throws Exception {

		var query = getEntityManager().createQuery( "select count(w.id) from WrapperFaturasPagas w where w.tag = :pTag" );
		query.setParameter( "pTag", tag );

		var total = super.<Long>executeQueryListCustomized( query );
		return total.get( 0 );

	}

	public List<FaturasPagas> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		try {

			var stringBuilder = new StringBuilder();
			stringBuilder.append( "select t " );
			stringBuilder.append( "  from FaturasPagas t" );
			stringBuilder.append( "  join WrapperFaturasPagas w on w.idFaturasPagas = t.id" );
			stringBuilder.append( " where w.tag = :pTag" );

			var query = getEntityManager().createQuery( stringBuilder.toString() );
			query.setMaxResults( pageSize );
			query.setFirstResult( ( page * pageSize ) - pageSize );
			query.setParameter( "pTag", tag );

			return super.<FaturasPagas>executeQueryListCustomized( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}
}
