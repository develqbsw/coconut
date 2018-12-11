package sk.qbsw.security.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.model.domain.*;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

/**
 * The group jpa dao.
 *
 * @author Ladislav Rosenberg
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.0.0
 */
public class GroupJpaDao extends AEntityQDslDao<Long, Group> implements GroupDao
{
	/**
	 * Instantiates a new Group jpa dao.
	 */
	public GroupJpaDao ()
	{
		super(QGroup.group, Long.class);
	}

	@Override
	public List<Group> findByType (GroupTypes type)
	{
		QGroup qGroup = QGroup.group;

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).where(qGroup.type.eq(type)).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	@Override
	public List<Group> findAll ()
	{
		QGroup qGroup = QGroup.group;

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	@Override
	public Group findOneByCode (String code) throws CSecurityException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QGroup qGroup = QGroup.group;

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).where(qGroup.code.eq(code));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public Group findOneByCodeAndUnit (String code, Unit unit) throws CSecurityException, NoResultException, NonUniqueResultException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QGroup qGroup = QGroup.group;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qGroup.code.eq(code));
		if (unit != null)
		{
			builder.and(qGroup.units.contains(unit));
		}
		else
		{
			builder.and(qGroup.units.isEmpty());
		}

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).distinct().leftJoin(qGroup.roles).fetchJoin().leftJoin(qGroup.units).fetchJoin().where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public List<Group> findByUnit (Unit unit)
	{
		QGroup qGroup = QGroup.group;
		QUnit qUnit = QUnit.unit;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (unit != null)
		{
			builder.and(qGroup.units.contains(unit));
		}

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).distinct().leftJoin(qGroup.units, qUnit).fetchJoin().where(builder).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	@Override
	public List<Group> findByUnitAndAccountId (Unit unit, Long accountId)
	{
		QGroup qGroup = QGroup.group;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QUnit qUnit = QUnit.unit;
		QAccount qAccount = QAccount.account;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (unit != null)
		{
			builder.and(qUnit.eq(unit));
		}
		if (accountId != null)
		{
			builder.and(qAccount.id.eq(accountId));
		}

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).distinct().leftJoin(qGroup.accountUnitGroups, qAccountUnitGroup).fetchJoin().leftJoin(qAccountUnitGroup.unit, qUnit).fetchJoin().leftJoin(qAccountUnitGroup.account, qAccount).fetchJoin().where(builder).orderBy(qGroup.code.asc());
		return query.fetch();
	}
}
