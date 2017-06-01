package sk.qbsw.security.core.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.model.domain.*;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class GroupJpaDao.
 *
 * @author Ladislav Rosenberg
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.0.0
 */
@Repository (value = "groupDao")
public class GroupJpaDao extends AEntityQDslDao<Long, Group> implements GroupDao
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupJpaDao.class);

	/**
	 * Default constructor - nothing special.
	 */
	public GroupJpaDao ()
	{
		super(QGroup.group, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findByFlagSystem(boolean)
	 */
	@Override
	public List<Group> findByFlagSystem (boolean flagSystem)
	{
		QGroup qGroup = QGroup.group;

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).where(qGroup.flagSystem.eq(flagSystem)).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findAll()
	 */
	@Override
	public List<Group> findAll ()
	{
		QGroup qGroup = QGroup.group;

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @deprecated
	 * @see sk.qbsw.security.core.GroupDao.dao.IGroupDao#findByCode(java.lang.String)
	 */
	@Override
	@Deprecated
	public List<Group> findByCode (String code)
	{
		QGroup qGroup = QGroup.group;

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).where(qGroup.code.eq(code)).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findOneByCode(java.lang.String)
	 */
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

	/**
	 * (non-Javadoc)
	 * 
	 * @deprecated
	 * @see sk.qbsw.security.core.GroupDao.dao.IGroupDao#findByCodeAndUnit(java.lang.String, sk.qbsw.security.core.Unit.model.domain.CUnit)
	 */
	@Override
	@Deprecated
	public List<Group> findByCodeAndUnit (String code, Unit unit) throws CSecurityException
	{
		List<Group> groups = new ArrayList<>();

		try
		{
			Group group = findOneByCodeAndUnit(code, unit);

			groups.add(group);
			return groups;
		}
		catch (NoResultException ex)
		{
			LOGGER.error("No groups found", ex);
			return groups;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findOneByCodeAndUnit(java.lang.String, sk.qbsw.security.core.core.model.domain.CUnit)
	 */
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

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findByUnit(sk.qbsw.security.core.core.model.domain.CUnit)
	 */
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

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findByUnitAndUser(sk.qbsw.security.core.core.model.domain.CUnit, sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	public List<Group> findByUnitAndUser (Unit unit, User user)
	{
		QGroup qGroup = QGroup.group;
		QUserUnitGroup qXuserUnitGroup = QUserUnitGroup.userUnitGroup;
		QUnit qUnit = QUnit.unit;
		QUser qUser = QUser.user;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (unit != null)
		{
			builder.and(qUnit.eq(unit));
		}
		if (user != null)
		{
			builder.and(qUser.eq(user));
		}

		// create query
		JPAQuery<Group> query = queryFactory.selectFrom(qGroup).distinct().leftJoin(qGroup.xUserUnitGroups, qXuserUnitGroup).fetchJoin().leftJoin(qXuserUnitGroup.unit, qUnit).fetchJoin().leftJoin(qXuserUnitGroup.user, qUser).fetchJoin().where(builder).orderBy(qGroup.code.asc());
		return query.fetch();
	}
}
