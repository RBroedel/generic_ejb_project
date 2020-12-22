package br.com.itix.comissoescnu.dao;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;

import br.com.itix.comissoescnu.entity.TipoProduto;
import br.com.itix.xdk.business.BaseDAOImpl;

@Stateless
public class TipoProdutoDAO extends BaseDAOImpl<TipoProduto> {
	
	public void markIntegration( UUID tag ) throws Exception {

		try {

			StringBuilder hql = new StringBuilder();

			hql.append( "insert into WrapperTipoProduto (idTipoProduto, tag, dataAlteracao) " );
			hql.append( "select COD_TIPO_PRODUTO, :pTag, a.DATA_ALTERACAO " );
			hql.append( "  from TabTipoProduto a " );
			hql.append( " where not exists (select 1 from WrapperTipoProduto wr " );
			hql.append( " 					 where wr.idTipoProduto = a.COD_TIPO_PRODUTO " );
			hql.append( " 					   and a.DATA_ALTERACAO = wr.dataAlteracao)" );

			var query = getEntityManager().createNativeQuery( hql.toString() ).setMaxResults( 1500 );
			query.setParameter( "pTag", tag );

			super.executeQuery( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}


	public void unmarkIntegration( List<TipoProduto> list ) throws Exception {

		try {

			for( var l : list ) {

				var query = getEntityManager().createQuery( "delete WrapperTipoProduto where idTipoProduto = :idTipoProduto " );
				query.setParameter( "idTipoProduto", l.getId() );
				query.executeUpdate();

			}

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}


	public Long getCountToIntegration( UUID tag ) throws Exception {

		var query = getEntityManager().createQuery( "select count(wr.id) from WrapperTipoProduto  wr where wr.tag = :pTag" );
		query.setParameter( "pTag", tag );

		var total = super.<Long>executeQueryListCustomized( query );
		return total.get( 0 );

	}


	public List<TipoProduto> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		try {

			var stringBuilder = new StringBuilder();
			stringBuilder.append( "select x " );
			stringBuilder.append( "  from TipoProduto  x" );
			stringBuilder.append( "  join WrapperTipoProduto  wr on wr.idTipoProduto  = x.id" );
			stringBuilder.append( " where wr.tag = :pTag" );

			var query = getEntityManager().createQuery( stringBuilder.toString() );
			query.setMaxResults( pageSize );
			query.setFirstResult( ( page * pageSize ) - pageSize );
			query.setParameter( "pTag", tag );

			return super.<TipoProduto>executeQueryListCustomized( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}

}
