package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Class CGroupJpaDao.
 *
 * @author Ladislav Rosenberg
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.6.0
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
	 * @see sk.qbsw.core.security.dao.IGroupDao#findAllByFlagSystem(boolean)
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findAllByFlagSystem (boolean flagSystem)
	{
		String strQuery = "from CGroup where flagSystem=:flag order by code";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("flag", flagSystem);
		return (List<CGroup>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findAll()
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findAll ()
	{
		String strQuery = "from CGroup order by code";

		Query query = getEntityManager().createQuery(strQuery);
		return (List<CGroup>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IGroupDao#findByCode(java.lang.String)
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findByCode (String code)
	{
		String strQuery = "select g from CGroup g WHERE g.code=:code order by g.code";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("code", code);
		return (List<CGroup>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IGroupDao#findByCodeFetchRoles(java.lang.String)
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findByCode (String code, CUnit unit)
	{
		String strQuery = "select distinct(gr) from CGroup gr " +
					"left join fetch gr.roles " +
					"left join fetch gr.units " +
					"where gr.code=:code and ((:unit is not null and :unit in elements(gr.units)) or (:unit is null and gr.units is empty))" +
					"order by gr.code";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("code", code);
		query.setParameter("unit", unit);
		return (List<CGroup>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IGroupDao#findByUnit(sk.qbsw.core.security.model.domain.CUnit)
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findByUnit (CUnit unit)
	{
		String strQuery = "select distinct(gr) from CGroup gr left join fetch gr.units";
		if(unit != null)
		{
			strQuery += " where :unit in elements(gr.units)";
		}
		
		strQuery += " order by gr.code";

		Query query = getEntityManager().createQuery(strQuery);
		
		if(unit != null)
		{
			query.setParameter("unit", unit);
		}
		
		return (List<CGroup>) query.getResultList();
	}
	
	@Override
	@SuppressWarnings ("unchecked")
	public List<CGroup> findByUnitUser(CUnit unit, CUser user)
	{
		String q = "select distinct(gr) from CGroup gr left join fetch gr.xUserUnitGroups xuug left join fetch xuug.unit un left join fetch xuug.user us where 1=1";
		
		if(unit != null)
		{
			q += " and un = :unit ";
		}
		
		if(user != null)
		{
			q += " and us = :user";
		}
		
		q += " order by gr.code";
		
		Query query = getEntityManager().createQuery(q);
		
		if(unit != null)
		{
			query.setParameter("unit", unit);
		}
		
		if(user != null)
		{
			query.setParameter("user", user);
		}
		
		return (List<CGroup>) query.getResultList();
	}
}
