/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.model.domain.QAuthenticationParams;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.time.OffsetDateTime;

/**
 * Authentication params DAO implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
public class AuthenticationParamsJpaDao extends AEntityQDslDao<Long, AuthenticationParams> implements AuthenticationParamsDao
{
	/**
	 * Instantiates a new Authentication params jpa dao.
	 */
	public AuthenticationParamsJpaDao ()
	{
		super(QAuthenticationParams.authenticationParams, Long.class);
	}

	@Override
	public AuthenticationParams findOneByAccountId (Long accountId) throws NonUniqueResultException, NoResultException
	{
		QAuthenticationParams qAuthParams = QAuthenticationParams.authenticationParams;

		// create query
		JPAQuery<AuthenticationParams> query = queryFactory.selectFrom(qAuthParams).distinct().where(qAuthParams.account.id.eq(accountId));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public AuthenticationParams findOneValidByAccountId (Long accountId) throws NonUniqueResultException, NoResultException
	{
		QAuthenticationParams qAuthParams = QAuthenticationParams.authenticationParams;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthParams.account.id.eq(accountId));
		builder.and(qAuthParams.validFrom.isNull().orAllOf(qAuthParams.validFrom.isNotNull(), qAuthParams.validFrom.lt(OffsetDateTime.now())));
		builder.and(qAuthParams.validTo.isNull().orAllOf(qAuthParams.validTo.isNotNull(), qAuthParams.validTo.goe(OffsetDateTime.now())));

		// create query
		JPAQuery<AuthenticationParams> query = queryFactory.selectFrom(qAuthParams).distinct().where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}
}
