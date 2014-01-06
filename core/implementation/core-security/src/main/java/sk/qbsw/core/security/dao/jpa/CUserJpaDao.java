/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.HashSet;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * User DAO implementation.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.0.0
 */
@Repository (value = "userDao")
public class CUserJpaDao extends AEntityJpaDao<Long, CUser> implements IUserDao
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new c role jpa dao.
	 *
	 */
	public CUserJpaDao ()
	{
		super(CUser.class);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the c user
	 * @see sk.qbsw.core.security.dao.IUserDao#findById(java.lang.Long)
	 */
	public CUser findById (Long id)
	{
		String strQuery = "select u from CUser u left join fetch u.organization o join fetch u.groups g where u.pkId=:pkId";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("pkId", id);
		return (CUser) query.getSingleResult();
	}
	
	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String)
	 */
	public CUser findByLogin (String login)
	{
		return findByLoginAndUnit(login, null);
	}
	
	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String, sk.qbsw.core.security.model.domain.CUnit)
	 */
	public CUser findByLogin (String login, CUnit unit)
	{
		return findByLoginAndUnit(login, unit);
	}
	
	@SuppressWarnings ("unchecked")
	private CUser findByLoginAndUnit (String login, CUnit unit)
	{
		CUser userToReturn = null;

		String strQuery = "select distinct(us) from CUser us " +
					"left join fetch us.organization " +
					"left join fetch us.defaultUnit " +
					"where us.login=:login";
		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("login", login);

		List<CUser> usersWithoutGroups = query.getResultList();
		
		//if there is a user with the specified login
		if (usersWithoutGroups.size() > 0)
		{
			//1. The unit has been set
			if (unit != null)
			{
				strQuery = "select distinct(us) from CUser us " +
					"left join fetch us.organization " +
					"left join fetch us.defaultUnit deun " +
					"left join fetch us.groups gr " +
					"left join fetch gr.units " +
					"left join fetch gr.roles " +
					"where us.login=:login and :unit in elements(gr.units)";
				query = getEntityManager().createQuery(strQuery);
				query.setParameter("login", login);
				query.setParameter("unit", unit);
			}
			//2. The user default unit is not null
			else if (usersWithoutGroups.get(0).getDefaultUnit() != null)
			{
				strQuery = "select distinct(us) from CUser us " +
						"left join fetch us.organization " +
						"left join fetch us.defaultUnit deun " +
						"left join fetch us.groups gr " +
						"left join fetch gr.units " +
						"left join fetch gr.roles " +
						"where us.login=:login and deun in elements(gr.units)";
				query = getEntityManager().createQuery(strQuery);
				query.setParameter("login", login);
			}
			//3. The user default unit is null
			else
			{
				strQuery = "select distinct(us) from CUser us " +
						"left join fetch us.organization " +
						"left join fetch us.defaultUnit " +
						"left join fetch us.groups gr " +
						"left join fetch gr.units un " +
						"left join fetch gr.roles " +
						"where us.login=:login and un is null";
				query = getEntityManager().createQuery(strQuery);
				query.setParameter("login", login);
			}

			List<CUser> usersWithGroups = query.getResultList();

			//a. there is a user with groups according the select - the list of users is not null
			if (usersWithGroups.size() > 0)
			{
				userToReturn = usersWithGroups.get(0);
			}
			//b. there is not a user with some groups - the query above returns a null so the list from the very first select is used and to groups is set a empty list
			else
			{
				userToReturn = usersWithoutGroups.get(0);
				userToReturn.setGroups(new HashSet<CGroup>());
			}
		}
		
		return userToReturn;
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

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("organization", organization);
		return (List<CUser>) query.getResultList();
	}

	/**
	 * Find all users.
	 *
	 * @param organization the organization
	 * @param enabled the enabled
	 * @param group the group
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

		Query query = getEntityManager().createQuery(strQuery);

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
	 * Find for modification.
	 *
	 * @param pkId id
	 * @return the user
	 * @see sk.qbsw.core.security.dao.IUserDao#findForModification(java.lang.Long)
	 */
	public CUser findForModification (Long pkId)
	{
		String strQuery = "from CUser u left join fetch u.groups where u.pkId=:pkId";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("pkId", pkId);
		return (CUser) query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers()
	 */
	@SuppressWarnings ("unchecked")
	public List<CUser> findAllUsers ()
	{
		String strQuery = "from CUser order by login";

		Query query = getEntityManager().createQuery(strQuery);

		return (List<CUser>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByPinNull(java.lang.String)
	 */
	@SuppressWarnings ("unchecked")
	public CUser findByPinNull (String pinCode)
	{
		if (pinCode == null)
		{
			return null;
		}

		CUser userToReturn;
		String strQuery = "select u from CUser u left join fetch u.organization o where u.pin=:pinCode";

		Query query = getEntityManager().createQuery(strQuery);
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


	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#getOtherActiveUsers(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUser)
	 */
	@SuppressWarnings ("unchecked")
	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{

		String strQuery = "select u from CUser as u " + "join u.groups g " + "where g.pkId = :group and " + "u.organization = :organization and " + "u.flagEnabled = true and " + "u != :user";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("group", group.getPkId());
		query.setParameter("organization", organization);
		query.setParameter("user", user);

		return (List<CUser>) query.getResultList();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsersByRole(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CRole)
	 */
	@SuppressWarnings ("unchecked")
	public List<CUser> findAllUsersByRole (COrganization organization, CRole role)
	{
		String strQuery = "select u from CUser u join u.groups g join g.roles r where u.organization=:organization ";

		if (role != null)
		{
			strQuery = strQuery.concat("and r.code =:role ");
		}
		strQuery = strQuery.concat("order by login");

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("organization", organization);
		if (role != null)
		{
			query.setParameter("role", role.getCode());
		}

		return (List<CUser>) query.getResultList();
	}

}
