/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;

/**
 * Authentication params DAO implementation.
 * 
 * @author Tomas Lauro
 * @version 1.12.2
 * @since 1.6.0
 */
@Repository (value = "authenticationParamsDao")
public class CAuthenticationParamsJpaDao extends AEntityJpaDao<Long, CAuthenticationParams> implements IAuthenticationParamsDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new authentication params role jpa dao.
	 * 
	 */
	public CAuthenticationParamsJpaDao ()
	{
		super(CAuthenticationParams.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IAuthenticationParamsDao#findByUserId(java.lang.Long)
	 */
	@Override
	public CAuthenticationParams findByUserId (Long userId)
	{
		String strQuery = "select distinct(ap) from CAuthenticationParams ap join ap.user us where us.id = :userId";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("userId", userId);

		return (CAuthenticationParams) query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IAuthenticationParamsDao#findValidByUserId(java.lang.Long)
	 */
	@Override
	public CAuthenticationParams findValidByUserId (Long userId)
	{
		String strQuery = "select distinct(ap) from CAuthenticationParams ap join ap.user us where us.id = :userId and ((ap.validFrom is not null and ap.validFrom < :now) or ap.validFrom is null) and ((ap.validTo is not null and :now <= ap.validTo) or ap.validTo is null)";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("userId", userId);
		query.setParameter("now", DateTime.now());

		return (CAuthenticationParams) query.getSingleResult();
	}
}
