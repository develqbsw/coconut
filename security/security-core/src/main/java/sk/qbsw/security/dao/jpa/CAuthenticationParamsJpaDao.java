/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.dao.IAuthenticationParamsDao;
import sk.qbsw.security.model.domain.CAuthenticationParams;
import sk.qbsw.security.model.domain.QCAuthenticationParams;

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
public class CAuthenticationParamsJpaDao extends AEntityQDslDao<Long, CAuthenticationParams> implements IAuthenticationParamsDao
{
	/**
	 * Instantiates a new authentication params role jpa dao.
	 */
	public CAuthenticationParamsJpaDao ()
	{
		super(QCAuthenticationParams.cAuthenticationParams, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.dao.IAuthenticationParamsDao#findOneByUserId(java.lang.Long)
	 */
	@Override
	public CAuthenticationParams findOneByUserId (Long userId) throws NonUniqueResultException, NoResultException
	{
		QCAuthenticationParams qAuthParams = QCAuthenticationParams.cAuthenticationParams;

		// create query
		JPAQuery<CAuthenticationParams> query = queryFactory.selectFrom(qAuthParams).distinct().where(qAuthParams.user.id.eq(userId));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.dao.IAuthenticationParamsDao#findValidByUserId(java.lang.Long)
	 */
	@Override
	public CAuthenticationParams findOneValidByUserId (Long userId) throws NonUniqueResultException, NoResultException
	{
		QCAuthenticationParams qAuthParams = QCAuthenticationParams.cAuthenticationParams;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthParams.user.id.eq(userId));
		builder.and(qAuthParams.validFrom.isNull().orAllOf(qAuthParams.validFrom.isNotNull(), qAuthParams.validFrom.lt(OffsetDateTime.now())));
		builder.and(qAuthParams.validTo.isNull().orAllOf(qAuthParams.validTo.isNotNull(), qAuthParams.validTo.goe(OffsetDateTime.now())));

		// create query
		JPAQuery<CAuthenticationParams> query = queryFactory.selectFrom(qAuthParams).distinct().where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}
}
