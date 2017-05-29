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
import sk.qbsw.security.core.dao.IGroupDao;
import sk.qbsw.security.core.model.domain.*;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class CGroupJpaDao.
 *
 * @author Ladislav Rosenberg
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.0.0
 */
@Repository (value = "groupDao")
public class CGroupJpaDao extends AEntityQDslDao<Long, CGroup> implements IGroupDao
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CGroupJpaDao.class);

	/**
	 * Default constructor - nothing special.
	 */
	public CGroupJpaDao ()
	{
		super(QCGroup.cGroup, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findByFlagSystem(boolean)
	 */
	@Override
	public List<CGroup> findByFlagSystem (boolean flagSystem)
	{
		QCGroup qGroup = QCGroup.cGroup;

		// create query
		JPAQuery<CGroup> query = queryFactory.selectFrom(qGroup).where(qGroup.flagSystem.eq(flagSystem)).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findAll()
	 */
	@Override
	public List<CGroup> findAll ()
	{
		QCGroup qGroup = QCGroup.cGroup;

		// create query
		JPAQuery<CGroup> query = queryFactory.selectFrom(qGroup).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @deprecated
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findByCode(java.lang.String)
	 */
	@Override
	@Deprecated
	public List<CGroup> findByCode (String code)
	{
		QCGroup qGroup = QCGroup.cGroup;

		// create query
		JPAQuery<CGroup> query = queryFactory.selectFrom(qGroup).where(qGroup.code.eq(code)).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findOneByCode(java.lang.String)
	 */
	@Override
	public CGroup findOneByCode (String code) throws CSecurityException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCGroup qGroup = QCGroup.cGroup;

		// create query
		JPAQuery<CGroup> query = queryFactory.selectFrom(qGroup).where(qGroup.code.eq(code));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @deprecated
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findByCodeAndUnit(java.lang.String, sk.qbsw.security.core.core.model.domain.CUnit)
	 */
	@Override
	@Deprecated
	public List<CGroup> findByCodeAndUnit (String code, CUnit unit) throws CSecurityException
	{
		List<CGroup> groups = new ArrayList<>();

		try
		{
			CGroup group = findOneByCodeAndUnit(code, unit);

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
	public CGroup findOneByCodeAndUnit (String code, CUnit unit) throws CSecurityException, NoResultException, NonUniqueResultException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCGroup qGroup = QCGroup.cGroup;

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
		JPAQuery<CGroup> query = queryFactory.selectFrom(qGroup).distinct().leftJoin(qGroup.roles).fetchJoin().leftJoin(qGroup.units).fetchJoin().where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findByUnit(sk.qbsw.security.core.core.model.domain.CUnit)
	 */
	@Override
	public List<CGroup> findByUnit (CUnit unit)
	{
		QCGroup qGroup = QCGroup.cGroup;
		QCUnit qUnit = QCUnit.cUnit;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (unit != null)
		{
			builder.and(qGroup.units.contains(unit));
		}

		// create query
		JPAQuery<CGroup> query = queryFactory.selectFrom(qGroup).distinct().leftJoin(qGroup.units, qUnit).fetchJoin().where(builder).orderBy(qGroup.code.asc());
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IGroupDao#findByUnitAndUser(sk.qbsw.security.core.core.model.domain.CUnit, sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	public List<CGroup> findByUnitAndUser (CUnit unit, CUser user)
	{
		QCGroup qGroup = QCGroup.cGroup;
		QCXUserUnitGroup qXuserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
		QCUnit qUnit = QCUnit.cUnit;
		QCUser qUser = QCUser.cUser;

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
		JPAQuery<CGroup> query = queryFactory.selectFrom(qGroup).distinct().leftJoin(qGroup.xUserUnitGroups, qXuserUnitGroup).fetchJoin().leftJoin(qXuserUnitGroup.unit, qUnit).fetchJoin().leftJoin(qXuserUnitGroup.user, qUser).fetchJoin().where(builder).orderBy(qGroup.code.asc());
		return query.fetch();
	}
}
