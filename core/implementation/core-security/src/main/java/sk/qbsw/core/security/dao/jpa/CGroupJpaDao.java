package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.QCGroup;
import sk.qbsw.core.security.model.domain.QCUnit;
import sk.qbsw.core.security.model.domain.QCUser;
import sk.qbsw.core.security.model.domain.QCXUserUnitGroup;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * The Class CGroupJpaDao.
 *
 * @author Ladislav Rosenberg
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Repository (value = "groupDao")
public class CGroupJpaDao extends AEntityJpaDao<Long, CGroup> implements IGroupDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor - nothing special.
	 */
	public CGroupJpaDao ()
	{
		super(CGroup.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IGroupDao#findByFlagSystem(boolean)
	 */
	@Override
	public List<CGroup> findByFlagSystem (boolean flagSystem)
	{
		QCGroup qGroup = QCGroup.cGroup;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(qGroup).where(qGroup.flagSystem.eq(flagSystem)).orderBy(qGroup.code.asc()).list(qGroup);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findAll()
	 */
	@Override
	public List<CGroup> findAll ()
	{
		QCGroup qGroup = QCGroup.cGroup;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(qGroup).orderBy(qGroup.code.asc()).list(qGroup);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IGroupDao#findByCode(java.lang.String)
	 */
	@Override
	public List<CGroup> findByCode (String code)
	{
		QCGroup qGroup = QCGroup.cGroup;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(qGroup).where(qGroup.code.eq(code)).orderBy(qGroup.code.asc()).list(qGroup);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IGroupDao#findByCodeAndUnit(java.lang.String, sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	public List<CGroup> findByCodeAndUnit (String code, CUnit unit)
	{
		QCGroup qGroup = QCGroup.cGroup;

		//create where condition
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

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.distinct().from(qGroup).leftJoin(qGroup.roles).fetch().leftJoin(qGroup.units).fetch().where(builder).orderBy(qGroup.code.asc()).list(qGroup);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IGroupDao#findByUnit(sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	public List<CGroup> findByUnit (CUnit unit)
	{
		QCGroup qGroup = QCGroup.cGroup;
		QCUnit qUnit = QCUnit.cUnit;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (unit != null)
		{
			builder.and(qGroup.units.contains(unit));
		}

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.distinct().from(qGroup).leftJoin(qGroup.units, qUnit).fetch().where(builder).orderBy(qGroup.code.asc()).list(qGroup);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IGroupDao#findByUnitAndUser(sk.qbsw.core.security.model.domain.CUnit, sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	public List<CGroup> findByUnitAndUser (CUnit unit, CUser user)
	{
		QCGroup qGroup = QCGroup.cGroup;
		QCXUserUnitGroup qXuserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
		QCUnit qUnit = QCUnit.cUnit;
		QCUser qUser = QCUser.cUser;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (unit != null)
		{
			builder.and(qUnit.eq(unit));
		}
		if (user != null)
		{
			builder.and(qUser.eq(user));
		}

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.distinct().from(qGroup).leftJoin(qGroup.xUserUnitGroups, qXuserUnitGroup).fetch().leftJoin(qXuserUnitGroup.unit, qUnit).fetch().leftJoin(qXuserUnitGroup.user, qUser).fetch().where(builder).orderBy(qGroup.code.asc()).list(qGroup);
	}
}
