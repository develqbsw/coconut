package sk.qbsw.security.management.db.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.management.db.model.domain.GroupManageable;
import sk.qbsw.security.management.db.model.domain.QGroupManageable;

/**
 * The group manageable jpa dao.
 *
 * @author Michal Slezák
 * @version 2.5.0
 * @since 2.5.0
 */
public class GroupManageableJpaDao extends AEntityQDslDao<Long, GroupManageable> implements GroupManageableDao
{
	/**
	 * Instantiates a new Manageable Group jpa dao.
	 */
	public GroupManageableJpaDao ()
	{
		super(QGroupManageable.groupManageable, Long.class);
	}

	@Override
	public List<GroupManageable> findAll ()
	{
		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;

		// create query
		JPAQuery<GroupManageable> query = queryFactory.selectFrom(qGroupManageable).orderBy(qGroupManageable.code.asc());

		return query.fetch();
	}

	@Override
	public GroupManageable findOneById (Long groupId)
	{
		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;
		QRole qRole = QRole.role;

		// create query
		JPAQuery<GroupManageable> query = queryFactory.selectFrom(qGroupManageable).leftJoin(qGroupManageable.roles, qRole).fetchJoin().leftJoin(qGroupManageable.updatedBy).fetchJoin().leftJoin(qGroupManageable.accountUnitGroups).fetchJoin().leftJoin(qGroupManageable.excludedGroups).fetchJoin().leftJoin(qGroupManageable.units).fetchJoin().where(qGroupManageable.id.eq(groupId)).orderBy(qRole.id.asc());

		return query.fetchOne();
	}

	@Override
	public List<GroupManageable> findByType (GroupTypes type)
	{
		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;

		// create query
		JPAQuery<GroupManageable> query = queryFactory.selectFrom(qGroupManageable).where(qGroupManageable.type.eq(type)).orderBy(qGroupManageable.code.asc());
		return query.fetch();
	}

	@Override
	public GroupManageable findOneByCode (String code) throws CSecurityException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;

		// create query
		JPAQuery<GroupManageable> query = queryFactory.selectFrom(qGroupManageable).where(qGroupManageable.code.eq(code));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public GroupManageable findOneByCodeAndUnitId (String code, Long unitId) throws CSecurityException, NoResultException, NonUniqueResultException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qGroupManageable.code.eq(code));
		if (unitId != null)
		{
			builder.and(qGroupManageable.units.any().id.eq(unitId));
		}
		else
		{
			builder.and(qGroupManageable.units.isEmpty());
		}

		// create query
		JPAQuery<GroupManageable> query = queryFactory.selectFrom(qGroupManageable).distinct().leftJoin(qGroupManageable.roles).fetchJoin().leftJoin(qGroupManageable.units).fetchJoin().where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public List<GroupManageable> findByUnitId (Long unitId)
	{
		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;
		QUnit qUnit = QUnit.unit;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (unitId != null)
		{
			builder.and(qGroupManageable.units.any().id.eq(unitId));
		}

		// create query
		JPAQuery<GroupManageable> query = queryFactory.selectFrom(qGroupManageable).distinct().leftJoin(qGroupManageable.units, qUnit).fetchJoin().where(builder).orderBy(qGroupManageable.code.asc());
		return query.fetch();
	}

	@Override
	public List<GroupManageable> findByUnitIdAndAccountId (Long unitId, Long accountId)
	{
		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QUnit qUnit = QUnit.unit;
		QAccount qAccount = QAccount.account;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (unitId != null)
		{
			builder.and(qUnit.id.eq(unitId));
		}
		if (accountId != null)
		{
			builder.and(qAccount.id.eq(accountId));
		}

		// create query
		JPAQuery<GroupManageable> query = queryFactory.selectFrom(qGroupManageable).distinct().leftJoin(qGroupManageable.accountUnitGroups, qAccountUnitGroup).fetchJoin().leftJoin(qAccountUnitGroup.unit, qUnit).fetchJoin().leftJoin(qAccountUnitGroup.account, qAccount).fetchJoin().where(builder).orderBy(qGroupManageable.code.asc());
		return query.fetch();
	}

	@Override
	public List<GroupManageable> findAllByIdIn (Set<Long> groupIds)
	{
		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;

		// create query
		JPAQuery<GroupManageable> query = queryFactory.selectFrom(qGroupManageable).distinct().where(qGroupManageable.id.in(groupIds));

		return query.fetch();
	}

	@Override
	public long deleteById (Long id)
	{
		QGroupManageable qGroupManageable = QGroupManageable.groupManageable;

		// create query
		JPADeleteClause deleteClause = queryFactory.delete(qGroupManageable).where(qGroupManageable.id.eq(id));

		return deleteClause.execute();
	}
}
