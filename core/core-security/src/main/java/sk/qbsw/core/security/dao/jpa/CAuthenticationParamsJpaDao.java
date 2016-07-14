/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.QCAuthenticationParams;

/**
 * Authentication params DAO implementation.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.6.0
 */
@Repository (value = "authenticationParamsDao")
public class CAuthenticationParamsJpaDao extends AEntityQDslDao<Long, CAuthenticationParams> implements IAuthenticationParamsDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new authentication params role jpa dao.
	 * 
	 */
	public CAuthenticationParamsJpaDao ()
	{
		super(QCAuthenticationParams.cAuthenticationParams, Long.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IAuthenticationParamsDao#findOneByUserId(java.lang.Long)
	 */
	@Override
	public CAuthenticationParams findOneByUserId (Long userId) throws NonUniqueResultException, NoResultException
	{
		QCAuthenticationParams qAuthParams = QCAuthenticationParams.cAuthenticationParams;

		//create query
		JPAQuery<CAuthenticationParams> query = queryFactory.selectFrom(qAuthParams).distinct().where(qAuthParams.user.id.eq(userId));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
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
		JPAQuery<CAuthenticationParams> query = queryFactory.selectFrom(qAuthParams).distinct().where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}
}
