package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.model.domain.CGroup;

/**
 * The Class CSectionJpaDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
@Repository (value = "groupDao")
public class CGroupJpaDao implements IGroupDao
{

	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@PersistenceContext(name="airlinesPersistenceContext")
	private EntityManager em;

	/**
	 * @see sk.qbsw.core.security.dao.IGroupDao#persit(sk.qbsw.core.security.model.domain.CGroup)
	 */
	public void persit (CGroup group)
	{
		this.em.persist(group);
	}
		
	/**
	 * @see sk.qbsw.core.security.dao.IGroupDao#findById(java.lang.Long)
	 */
	public CGroup findById (Long id)
	{
		String strQuery = "select u from CGroup g where g.pkId=:pkId";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("pkId", id);
		return (CGroup) query.getSingleResult();
	}

	
	/**
	 * Find all by flag system.
	 *
	 * @param flagSystem the flag system
	 * @return the list
	 * @see sk.qbsw.core.security.dao.IGroupDao#findAllByFlagSystem(boolean)
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findAllByFlagSystem (boolean flagSystem)
	{
		String strQuery = "from CGroup where flagSystem=:flag order by code";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("flag", flagSystem);
		return (List<CGroup>) query.getResultList();
	}

	
	/**
	 * Find all 
	 *
	 * @return the list
	 * @see sk.qbsw.core.security.dao.IGroupDao#findAll()
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findAll()
	{
		String strQuery = "from CGroup order by code";

		Query query = this.em.createQuery(strQuery);
		return (List<CGroup>) query.getResultList();
	}
	
	/**
	 * Find all 
	 *
	 * @return the list
	 * @see sk.qbsw.core.security.dao.IGroupDao#findAll()
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findByCode(String code)
	{
		String strQuery = "from CGroup g WHERE g.code=:code order by g.code";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("code", code);
		return (List<CGroup>) query.getResultList();
	}

	
}
