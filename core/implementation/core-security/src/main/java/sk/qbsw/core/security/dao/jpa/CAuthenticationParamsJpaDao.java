/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.QCAuthenticationParams;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * Authentication params DAO implementation.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
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
	 * @see sk.qbsw.core.security.dao.IAuthenticationParamsDao#findOneByUserId(java.lang.Long)
	 */
	@Override
	public CAuthenticationParams findOneByUserId (Long userId) throws NonUniqueResultException, NoResultException
	{
		QCAuthenticationParams qAuthParams = QCAuthenticationParams.cAuthenticationParams;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager()).distinct().from(qAuthParams).where(qAuthParams.user.id.eq(userId));
		return CJpaDaoHelper.handleUniqueResultQuery(query, qAuthParams);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IAuthenticationParamsDao#findValidByUserId(java.lang.Long)
	 */
	@Override
	public CAuthenticationParams findOneValidByUserId (Long userId) throws NonUniqueResultException, NoResultException
	{
		QCAuthenticationParams qAuthParams = QCAuthenticationParams.cAuthenticationParams;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthParams.user.id.eq(userId));
		builder.and(qAuthParams.validFrom.isNull().orAllOf(qAuthParams.validFrom.isNotNull(), qAuthParams.validFrom.lt(DateTime.now())));
		builder.and(qAuthParams.validTo.isNull().orAllOf(qAuthParams.validTo.isNotNull(), qAuthParams.validTo.goe(DateTime.now())));

		//create query
		JPAQuery query = new JPAQuery(getEntityManager()).distinct().from(qAuthParams).where(builder);
		return CJpaDaoHelper.handleUniqueResultQuery(query, qAuthParams);
	}
}
