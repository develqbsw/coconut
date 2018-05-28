package sk.qbsw.security.core.dao;

import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.model.domain.QAccount;
import sk.qbsw.security.core.model.domain.QAccountUnitGroup;
import sk.qbsw.security.core.model.domain.QUnit;
import sk.qbsw.security.core.model.domain.Unit;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * The unit jpa dao.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
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
	public Unit findOneByName (String name) throws NoResultException, CSecurityException
	{
		if (name == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUnit qUnit = QUnit.unit;

		// create query
		JPAQuery<Unit> query = queryFactory.selectFrom(qUnit).distinct().leftJoin(qUnit.groups).fetchJoin().leftJoin(qUnit.organization).fetchJoin().where(qUnit.name.eq(name));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public List<Unit> findByAccountId (Long accountId) throws CSecurityException
	{
		if (accountId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUnit qUnit = QUnit.unit;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QAccount qAccount = QAccount.account;

		// create query
		JPAQuery<Unit> query = queryFactory.selectFrom(qUnit).distinct().join(qUnit.accountUnitGroups, qAccountUnitGroup).join(qAccountUnitGroup.account, qAccount).where(qAccount.id.eq(accountId)).orderBy(qUnit.name.asc());
		return query.fetch();
	}
}
