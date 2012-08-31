/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Class CSectionJpaDao.
 *
 * @author rosenberg
 * @version 1.0
 * @since 1.0
 */
@Repository (value = "userDao")
public class CUserJpaDao implements IUserDao
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext (name = "airlinesPersistenceContext")
	private EntityManager em;


	/**
	 * @see sk.qbsw.core.security.dao.IUserDao#persit(sk.qbsw.core.security.model.domain.CUser)
	 */
	public void persit (CUser user)
	{
		this.em.persist(user);

	}

	/**
	 * @see sk.qbsw.core.security.dao.IUserDao#findById(java.lang.Long)
	 */
	public CUser findById (Long id)
	{
		String strQuery = "select u from CUser u left join fetch u.organization o join fetch u.groups g where u.pkId=:pkId";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("pkId", id);
		return (CUser) query.getSingleResult();
	}

	/**
	 * Find by login.
	 *
	 * @param login the login
	 * @return the c user
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String)
	 */
	public CUser findByLogin (String login)
	{
		String strQuery = "select u from CUser u left join fetch u.organization o where u.login=:login";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("login", login);
		return (CUser) query.getSingleResult();
	}

	/**
	 * Find all users.
	 *
	 * @param organization the organization
	 * @return the list
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@SuppressWarnings ("unchecked")
	public List<CUser> findAllUsers (COrganization organization)
	{
		String strQuery = "from CUser where organization=:organization order by login";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("organization", organization);
		return (List<CUser>) query.getResultList();
	}

	/**
	 * Find all users.
	 *
	 * @param organization the organization
	 * @param group the group
	 * @param enabled the enabled
	 * @return the list
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@SuppressWarnings ("unchecked")
	public List<CUser> findAllUsers (COrganization organization, Boolean enabled, CGroup group)
	{
		String strQuery = "select u from CUser u join fetch u.groups g where u.organization=:organization ";

		if (group != null)
		{
			strQuery = strQuery.concat("and g=:group ");
		}

		if (enabled != null)
		{
			strQuery = strQuery.concat("and u.flagEnabled=:enabled ");
		}

		strQuery = strQuery.concat("order by u.login ");

		Query query = this.em.createQuery(strQuery);

		query.setParameter("organization", organization);

		if (group != null)
		{
			query.setParameter("group", group);
		}

		if (enabled != null)
		{
			query.setParameter("enabled", enabled);
		}

		return (List<CUser>) query.getResultList();
	}

	/**
	 * Merge.
	 *
	 * @param user the user
	 * @see sk.qbsw.core.security.dao.IUserDao#merge(sk.qbsw.core.security.model.domain.CUser)
	 */
	public void merge (CUser user)
	{
		this.em.merge(user);
	}

	/**
	 * Find for modification.
	 *
	 * @param pkId id
	 * @return the user
	 * @see sk.qbsw.core.security.dao.IUserDao#findForModification(java.lang.Long)
	 */
	public CUser findForModification (Long pkId)
	{
		String strQuery = "from CUser u left join fetch u.groups where u.pkId=:pkId";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("pkId", pkId);
		return (CUser) query.getSingleResult();
	}

	@SuppressWarnings ("unchecked")
	public List<CUser> findAllUsers ()
	{
		String strQuery = "from CUser order by login";

		Query query = this.em.createQuery(strQuery);

		return (List<CUser>) query.getResultList();
	}


	@SuppressWarnings ("unchecked")
	public CUser findForLogin (String login, String password)
	{
		String strQuery = "select distinct u from CUser as u " + "join fetch u.organization o" + " join fetch u.groups g " + " join fetch g.roles r where u.login = :login and u.password = :password";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("login", login);
		query.setParameter("password", password);

		List<CUser> found = (List<CUser>) query.getResultList();

		if (found.size() > 0)
		{
			return found.get(0);
		}
		return null;
	}

	/**
	 * Find by login  and return NULL if user not exist - NOT exeption.
	 *
	 * @param login the login
	 * @return the c user or null if user not exist
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String)
	 */
	@SuppressWarnings ("unchecked")
	public CUser findByLoginNull (String login)
	{

		CUser userToReturn;
		String strQuery = "select u from CUser u left join fetch u.organization o where u.login=:login";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("login", login);

		List<CUser> users = query.getResultList();

		if (users.size() < 1)
		{
			userToReturn = null;
		}
		else
		{
			userToReturn = users.get(0);
		}

		return userToReturn;
	}

	@SuppressWarnings ("unchecked")
	public CUser findByPinNull (String pinCode)
	{
		if (pinCode == null)
		{
			return null;
		}

		CUser userToReturn;
		String strQuery = "select u from CUser u left join fetch u.organization o where u.pin=:pinCode";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("pinCode", pinCode);

		List<CUser> users = query.getResultList();

		if (users.size() < 1)
		{
			userToReturn = null;
		}
		else
		{
			userToReturn = users.get(0);
		}

		return userToReturn;
	}

	@SuppressWarnings ("unchecked")
	public CUser findByLoginAndRole (String login, String password, CRole role)
	{
		String strQuery = "select distinct u from CUser as u " + "join fetch u.organization o" + " join u.groups g " + " join g.roles r " + " where r.code =:role and " + "u.login = :login and " + "u.password = :password and " + "u.flagEnabled = true";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("login", login);
		query.setParameter("role", role.getCode());
		query.setParameter("password", password);

		List<CUser> found = (List<CUser>) query.getResultList();

		if (found.size() > 0)
		{
			return found.get(0);
		}
		return null;
	}

	@SuppressWarnings ("unchecked")
	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{

		String strQuery = "select u from CUser as u " + "join u.groups g " + "where g.pkId = :group and " + "u.organization = :organization and " + "u.flagEnabled = true and " + "u != :user";

		Query query = this.em.createQuery(strQuery);
		query.setParameter("group", group.getPkId());
		query.setParameter("organization", organization);
		query.setParameter("user", user);

		return (List<CUser>) query.getResultList();
	}

	@SuppressWarnings ("unchecked")
	public List<CUser> findAllUsersByRole (COrganization organization, CRole role)
	{
		String strQuery = "select u from CUser u join u.groups g join g.roles r where u.organization=:organization ";

		if (role != null)
		{
			strQuery = strQuery.concat("and r.code =:role ");
		}
		strQuery = strQuery.concat("order by login");

		Query query = this.em.createQuery(strQuery);
		query.setParameter("organization", organization);
		if (role != null)
		{
			query.setParameter("role", role.getCode());
		}

		return (List<CUser>) query.getResultList();
	}

}
