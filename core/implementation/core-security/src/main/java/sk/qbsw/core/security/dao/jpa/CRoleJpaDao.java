/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

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
public class CRoleJpaDao implements IRoleDao
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext (name = "airlinesPersistenceContext")
	private EntityManager em;

	/**
	 * @see sk.qbsw.core.security.dao.IRoleDao#persit(sk.qbsw.core.security.model.domain.CRole)
	 */
	public void persit (CRole role)
	{
		this.em.persist(role);
	}

	/**
	 * @see sk.qbsw.core.security.dao.IRoleDao#findById(java.lang.Long)
	 **/
	public CRole findById (Long id)
	{
		String strQuery = "select u from CRole l where l.pkId=:pkId";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("pkId", id);
		return (CRole) query.getSingleResult();
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

		Query query = this.em.createQuery(strQuery);
		query.setParameter("user", user);
		return (List<CRole>) query.getResultList();
	}

	@SuppressWarnings ("unchecked")
	public List<CRole> findByCode (String code)
	{

		String strQuery = "select r from CRole r where r.code =:code";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("code", code);
		return (List<CRole>) query.getResultList();
	}


}
