package br.com.itix.comissoescnu.dao;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;

import br.com.itix.comissoescnu.entity.Beneficiario;
import br.com.itix.xdk.business.BaseDAOImpl;

@Stateless
public class BeneficiarioDAO extends BaseDAOImpl<Beneficiario> {
	
	public void markIntegration( UUID tag ) throws Exception {

		try {

			StringBuilder hql = new StringBuilder();

			hql.append( "insert into WrapperBeneficiario (idBeneficiario, tag, dataAlteracao) " );
			hql.append( "select COD_BENEFICIARIO, :pTag, a.DATA_ALTERACAO " );
			hql.append( "  from TabBeneficiario a " );
			hql.append( " where not exists (select 1 from WrapperBeneficiario wr " );
			hql.append( " 					 where wr.idBeneficiario = a.COD_BENEFICIARIO " );
			hql.append( " 					   and a.DATA_ALTERACAO = wr.dataAlteracao)" );

			var query = getEntityManager().createNativeQuery( hql.toString() ).setMaxResults( 1500 );
			query.setParameter( "pTag", tag );

			super.executeQuery( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}


	public void unmarkIntegration( List<Beneficiario> list ) throws Exception {

		try {

			for( var l : list ) {

				var query = getEntityManager().createQuery( "delete WrapperBeneficiario where idBeneficiario = :idBeneficiario " );
				query.setParameter( "idBeneficiario", l.getId() );
				query.executeUpdate();

			}

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}


	public Long getCountToIntegration( UUID tag ) throws Exception {

		var query = getEntityManager().createQuery( "select count(wr.id) from WrapperBeneficiario  wr where wr.tag = :pTag" );
		query.setParameter( "pTag", tag );

		var total = super.<Long>executeQueryListCustomized( query );
		return total.get( 0 );

	}


	public List<Beneficiario> getFromLegacy( UUID tag, int page, int pageSize ) throws Exception {

		try {

			var stringBuilder = new StringBuilder();
			stringBuilder.append( "select x " );
			stringBuilder.append( "  from Beneficiario  x" );
			stringBuilder.append( "  join WrapperBeneficiario  wr on wr.idBeneficiario  = x.id" );
			stringBuilder.append( " where wr.tag = :pTag" );

			var query = getEntityManager().createQuery( stringBuilder.toString() );
			query.setMaxResults( pageSize );
			query.setFirstResult( ( page * pageSize ) - pageSize );
			query.setParameter( "pTag", tag );

			return super.<Beneficiario>executeQueryListCustomized( query );

		} catch ( Exception e ) {

			e.printStackTrace();
			throw e;

		}

	}

}
