/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Class CSectionJpaDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
@Repository (value = "roleDao")
public class CRoleJpaDao extends AEntityJpaDao<CRole> implements IRoleDao
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new c role jpa dao.
	 *
	 * @param entityClass the entity class
	 */
	public CRoleJpaDao ()
	{
		super(CRole.class);
	}
	
	/**
	 * Find all by user.
	 *
	 * @param user the user
	 * @return the list
	 * @see sk.qbsw.core.security.dao.IRoleDao#findAllByUser()
	 */
	@SuppressWarnings ("unchecked")
	public List<CRole> findAllByUser (CUser user)
	{
		String strQuery = "select distinct(r) from CRole r join r.groups g join g.users u where u = :user";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("user", user);
		return (List<CRole>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IRoleDao#findByCode(java.lang.String)
	 */
	@SuppressWarnings ("unchecked")
	public List<CRole> findByCode (String code)
	{

		String strQuery = "select r from CRole r where r.code =:code";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("code", code);
		return (List<CRole>) query.getResultList();
	}


}
