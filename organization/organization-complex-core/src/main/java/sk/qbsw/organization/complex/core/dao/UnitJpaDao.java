package sk.qbsw.organization.complex.core.dao;

import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.organization.complex.core.model.domain.QUnit;
import sk.qbsw.organization.complex.core.model.domain.QUser;
import sk.qbsw.organization.complex.core.model.domain.Unit;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * The type Unit jpa dao.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class UnitJpaDao extends AEntityQDslDao<Long, Unit> implements UnitDao
{
	/**
	 * Instantiates a new Unit jpa dao.
	 */
	public UnitJpaDao ()
	{
		super(QUnit.unit, Long.class);
	}

	@Override
	public Unit findOneByCode (String code) throws NoResultException, CSecurityException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUnit qUnit = QUnit.unit;

		// create query
		JPAQuery<Unit> query = queryFactory.selectFrom(qUnit).distinct().leftJoin(qUnit.organization).fetchJoin().where(qUnit.code.eq(code));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public List<Unit> findByUserId (Long userId) throws CSecurityException
	{
		if (userId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUnit qUnit = QUnit.unit;
		QUser qUser = QUser.user;

		// create query
		JPAQuery<Unit> query = queryFactory.selectFrom(qUnit).distinct().leftJoin(qUnit.users, qUser).where(qUser.id.eq(userId)).orderBy(qUnit.name.asc());
		return query.fetch();
	}

	@Override
	public Unit findWithUsers (Long id)
	{
		QUnit qUnit = QUnit.unit;
		QUser qUser = QUser.user;

		// create query
		JPAQuery<Unit> query = queryFactory.selectFrom(qUnit).distinct().leftJoin(qUnit.users, qUser).fetchJoin().where(qUnit.id.eq(id));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}
}
