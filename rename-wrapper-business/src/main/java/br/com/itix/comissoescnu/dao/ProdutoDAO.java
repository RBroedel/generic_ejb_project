package br.com.itix.comissoescnu.dao;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;

import br.com.itix.comissoescnu.entity.Produto;
import br.com.itix.xdk.business.BaseDAOImpl;

@Stateless
public class ProdutoDAO extends BaseDAOImpl<Produto> {
	
	public void markIntegration( UUID tag ) throws Exception {

		try {

			StringBuilder hql = new StringBuilder();

			hql.append( "insert into WrapperProduto (idProduto, tag, dataAlteracao) " );
			hql.append( "select TIPO_EMPRESA, :pTag, a.DATA_ALTERACAO " );
			hql.append( "  from TabProduto a " );
			hql.append( " where not exists (select 1 from WrapperProduto wr " );
			hql.append( " 					 where wr.idProduto = a.TIPO_EMPRESA " );
			hql.append( " 					   and a.DATA_ALTERACAO = wr.dataAlteracao)" );

			var query = getEntityManager().createNativeQuery( hql.toString() ).setMaxResults( 1500 );
			query.setParameter( "pTag", tag );

			super.executeQuery( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}


	public void unmarkIntegration( List<Produto> list ) throws Exception {

		try {

			for( var l : list ) {

				var query = getEntityManager().createQuery( "delete WrapperProduto where idProduto = :idProduto " );
				query.setParameter( "idProduto", l.getId() );
				query.executeUpdate();

			}

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}


	public Long getCountToIntegration( UUID tag ) throws Exception {

		var query = getEntityManager().createQuery( "select count(wr.id) from WrapperProduto  wr where wr.tag = :pTag" );
		query.setParameter( "pTag", tag );

		var total = super.<Long>executeQueryListCustomized( query );
		return total.get( 0 );

	}


	public List<Produto> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		try {

			var stringBuilder = new StringBuilder();
			stringBuilder.append( "select x " );
			stringBuilder.append( "  from Produto  x" );
			stringBuilder.append( "  join WrapperProduto  wr on wr.idProduto  = x.id" );
			stringBuilder.append( " where wr.tag = :pTag" );

			var query = getEntityManager().createQuery( stringBuilder.toString() );
			query.setMaxResults( pageSize );
			query.setFirstResult( ( page * pageSize ) - pageSize );
			query.setParameter( "pTag", tag );

			return super.<Produto>executeQueryListCustomized( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}

}
