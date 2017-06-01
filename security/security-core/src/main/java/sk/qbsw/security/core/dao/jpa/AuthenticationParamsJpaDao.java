/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.model.domain.QAuthenticationParams;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.time.OffsetDateTime;

/**
 * Authentication params DAO implementation.
 * 
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.6.0
 */
@Repository (value = "authenticationParamsDao")
public class AuthenticationParamsJpaDao extends AEntityQDslDao<Long, AuthenticationParams> implements AuthenticationParamsDao
{
	/**
	 * Instantiates a new authentication params role jpa dao.
	 */
	public AuthenticationParamsJpaDao ()
	{
		super(QAuthenticationParams.authenticationParams, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IAuthenticationParamsDao#findOneByUserId(java.lang.Long)
	 */
	@Override
	public AuthenticationParams findOneByUserId (Long userId) throws NonUniqueResultException, NoResultException
	{
		QAuthenticationParams qAuthParams = QAuthenticationParams.authenticationParams;

		// create query
		JPAQuery<AuthenticationParams> query = queryFactory.selectFrom(qAuthParams).distinct().where(qAuthParams.user.id.eq(userId));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IAuthenticationParamsDao#findValidByUserId(java.lang.Long)
	 */
	@Override
	public AuthenticationParams findOneValidByUserId (Long userId) throws NonUniqueResultException, NoResultException
	{
		QAuthenticationParams qAuthParams = QAuthenticationParams.authenticationParams;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthParams.user.id.eq(userId));
		builder.and(qAuthParams.validFrom.isNull().orAllOf(qAuthParams.validFrom.isNotNull(), qAuthParams.validFrom.lt(OffsetDateTime.now())));
		builder.and(qAuthParams.validTo.isNull().orAllOf(qAuthParams.validTo.isNotNull(), qAuthParams.validTo.goe(OffsetDateTime.now())));

		// create query
		JPAQuery<AuthenticationParams> query = queryFactory.selectFrom(qAuthParams).distinct().where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}
}
