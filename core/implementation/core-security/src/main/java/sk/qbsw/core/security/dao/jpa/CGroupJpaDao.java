package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.model.domain.CGroup;

/**
 * The Class CSectionJpaDao.
 *
 * @author Ladislav Rosenberg
 * @author Dalibor Rak
 * @version 1.2.1
 * @since 1.0.0
 */
@Repository (value = "groupDao")
public class CGroupJpaDao extends AEntityJpaDao<Long, CGroup> implements IGroupDao
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor - nothing special.
	 */
	public CGroupJpaDao ()
	{
		super(CGroup.class);
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

		Query query = getEntityManager().createQuery(strQuery);
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
	public List<CGroup> findAll ()
	{
		String strQuery = "from CGroup order by code";

		Query query = getEntityManager().createQuery(strQuery);
		return (List<CGroup>) query.getResultList();
	}

	/**
	 * Find all 
	 *
	 * @return the list
	 * @see sk.qbsw.core.security.dao.IGroupDao#findAll()
	 */
	@SuppressWarnings ("unchecked")
	public List<CGroup> findByCode (String code)
	{
		String strQuery = "select g from CGroup g WHERE g.code=:code order by g.code";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("code", code);
		return (List<CGroup>) query.getResultList();
	}
}
