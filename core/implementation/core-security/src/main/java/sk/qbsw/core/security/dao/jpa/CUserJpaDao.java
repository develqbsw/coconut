/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.HashSet;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.base.exception.CSystemException;
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
 * @version 1.6.1
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
	 * @param id
	 *            the id
	 * @return the c user
	 * @see sk.qbsw.core.security.dao.IUserDao#findById(java.lang.Long)
	 */
	@SuppressWarnings ("unchecked")
	public CUser findById (Long id)
	{
		String strQuery = "select distinct(us) from CUser us " + "left join fetch us.organization " + "left join fetch us.defaultUnit " + "where us.pkId=:pkId";
		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("pkId", id);

		//throws exception if there is no result or multiple results
		CUser userWithoutGroups = (CUser) query.getSingleResult();

		// if there is a user with the specified id
		if (userWithoutGroups != null)
		{
			// 1. The user default unit is not null
			if (userWithoutGroups.getDefaultUnit() != null)
			{
				strQuery = "select distinct(us) from CUser us " + "left join fetch us.organization " + "left join fetch us.defaultUnit deun " + "left join fetch us.groups gr " + "left join fetch gr.units " + "left join fetch gr.roles " + "where us.pkId=:pkId and deun in elements(gr.units)";

			}
			// 2. The user default unit is null
			else
			{
				strQuery = "select distinct(us) from CUser us " + "left join fetch us.organization " + "left join fetch us.defaultUnit " + "left join fetch us.groups gr " + "left join fetch gr.units un " + "left join fetch gr.roles " + "where us.pkId=:pkId and un is null";
			}

			query = getEntityManager().createQuery(strQuery);
			query.setParameter("pkId", id);

			List<CUser> usersWithGroups = query.getResultList();

			// a. there is a user with groups according the select - the list of
			// users is not null
			if (usersWithGroups.size() > 0)
			{
				return usersWithGroups.get(0);
			}
			// b. there is not a user with some groups - the query above returns
			// a null so the list from the very first select is used and to
			// groups is set a empty list
			else
			{
				userWithoutGroups.setGroups(new HashSet<CGroup>());
				return userWithoutGroups;
			}
		}
		else
		{
			throw new NoResultException("There is no user with id " + id);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findForModification(java.lang.Long)
	 */
	public CUser findForModification (Long pkId)
	{
		return findById(pkId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String)
	 */
	public CUser findByLogin (String login)
	{
		return findByLoginAndUnit(login, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String,
	 * sk.qbsw.core.security.model.domain.CUnit)
	 */
	public CUser findByLogin (String login, CUnit unit)
	{
		return findByLoginAndUnit(login, unit);
	}

	/**
	 * Find by login and unit.
	 *
	 * @param login the login
	 * @param unit the unit
	 * @return the c user
	 */
	@SuppressWarnings ("unchecked")
	private CUser findByLoginAndUnit (String login, CUnit unit)
	{
		CUser userToReturn = null;

		String strQuery = "select distinct(us) from CUser us " + "left join fetch us.organization " + "left join fetch us.defaultUnit " + "where us.login=:login";
		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("login", login);

		List<CUser> usersWithoutGroups = query.getResultList();

		// if there is a user with the specified login
		if (usersWithoutGroups.size() > 0)
		{
			// 1. The unit has been set
			if (unit != null)
			{
				strQuery = "select distinct(us) from CUser us " + "left join fetch us.organization " + "left join fetch us.defaultUnit deun " + "left join fetch us.groups gr " + "left join fetch gr.units " + "left join fetch gr.roles " + "where us.login=:login and :unit in elements(gr.units)";
				query = getEntityManager().createQuery(strQuery);
				query.setParameter("login", login);
				query.setParameter("unit", unit);
			}
			// 2. The user default unit is not null
			else if (usersWithoutGroups.get(0).getDefaultUnit() != null)
			{
				strQuery = "select distinct(us) from CUser us " + "left join fetch us.organization " + "left join fetch us.defaultUnit deun " + "left join fetch us.groups gr " + "left join fetch gr.units " + "left join fetch gr.roles " + "where us.login=:login and deun in elements(gr.units)";
				query = getEntityManager().createQuery(strQuery);
				query.setParameter("login", login);
			}
			// 3. The user default unit is null
			else
			{
				strQuery = "select distinct(us) from CUser us " + "left join fetch us.organization " + "left join fetch us.defaultUnit " + "left join fetch us.groups gr " + "left join fetch gr.units un " + "left join fetch gr.roles " + "where us.login=:login and un is null";
				query = getEntityManager().createQuery(strQuery);
				query.setParameter("login", login);
			}

			List<CUser> usersWithGroups = query.getResultList();

			// a. there is a user with groups according the select - the list of
			// users is not null
			if (usersWithGroups.size() > 0)
			{
				userToReturn = usersWithGroups.get(0);
			}
			// b. there is not a user with some groups - the query above returns
			// a null so the list from the very first select is used and to
			// groups is set a empty list
			else
			{
				userToReturn = usersWithoutGroups.get(0);
				userToReturn.setGroups(new HashSet<CGroup>());
			}
		}

		return userToReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers()
	 */
	public List<CUser> findAllUsers ()
	{
		return findAllUsers(null, null, null, null, null, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.COrganization)
	 */
	public List<CUser> findAllUsers (COrganization organization)
	{
		if (organization == null)
		{
			throw new CSystemException("The mandatory parameter organization is missing");
		}
		else
		{
			return findAllUsers(organization, null, null, null, null, false);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean)
	 */
	public List<CUser> findAllUsers (COrganization organization, Boolean enabled)
	{
		if (enabled == null)
		{
			throw new CSystemException("The mandatory parameter enabled is missing");
		}
		else
		{
			return findAllUsers(organization, enabled, null, null, null, false);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	public List<CUser> findAllUsers (COrganization organization, Boolean enabled, CGroup group)
	{
		return findAllUsers(organization, enabled, group, null, null, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsersOrderByOrganization(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	public List<CUser> findAllUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group)
	{
		return findAllUsers(organization, enabled, group, null, null, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsersByRole(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CRole)
	 */
	public List<CUser> findAllUsersByRole (COrganization organization, CRole role)
	{
		if (role == null)
		{
			throw new CSystemException("The mandatory parameter role is missing");
		}
		else
		{
			return findAllUsers(organization, null, null, role, null, false);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#getOtherActiveUsers(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUser)
	 */
	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{
		if (user == null)
		{
			throw new CSystemException("The mandatory parameter user is missing");
		}
		else
		{
			return findAllUsers(organization, true, group, null, user, false);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public List<CUser> findAllUsers (String name, String surname, String login, Boolean enabled)
	{
		return findAllUsers(name, surname, login, enabled, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	@SuppressWarnings ("unchecked")
	public List<CUser> findAllUsers (String name, String surname, String login, Boolean enabled, String groupCodePrefix)
	{
		StringBuilder strQueryBuilder = new StringBuilder();

		/** Create query */
		strQueryBuilder.append("select distinct(us) from CUser us left join us.groups gr where 1=1");

		if (name != null)
		{
			strQueryBuilder.append(" and us.name=:name");
		}

		if (surname != null)
		{
			strQueryBuilder.append(" and us.surname=:surname");
		}

		if (login != null)
		{
			strQueryBuilder.append(" and us.login=:login");
		}

		if (groupCodePrefix != null)
		{
			strQueryBuilder.append(" and gr.code like :groupCodePrefix");
		}

		if (enabled != null)
		{
			strQueryBuilder.append(" and us.flagEnabled=:enabled");
		}

		/** Create order by section. */
		strQueryBuilder.append(" order by us.login");

		//create query
		Query query = getEntityManager().createQuery(strQueryBuilder.toString());

		/** Set parameters. */
		if (name != null)
		{
			query.setParameter("name", name);
		}

		if (surname != null)
		{
			query.setParameter("surname", surname);
		}

		if (login != null)
		{
			query.setParameter("login", login);
		}

		if (groupCodePrefix != null)
		{
			query.setParameter("groupCodePrefix", "%" + groupCodePrefix + "%");
		}

		if (enabled != null)
		{
			query.setParameter("enabled", enabled);
		}

		List<CUser> users = (List<CUser>) query.getResultList();

		return users;
	}

	/**
	 * Find all users.
	 *
	 * @param organization the organization (optional)
	 * @param enabled the enabled (optional)
	 * @param group the group (optional)
	 * @param role the role (optional)
	 * @param excludedUser the excluded user (optional)
	 * @param orderByOrganization the order by organization
	 * @return the list of users
	 */
	@SuppressWarnings ("unchecked")
	private List<CUser> findAllUsers (COrganization organization, Boolean enabled, CGroup group, CRole role, CUser excludedUser, boolean orderByOrganization)
	{
		StringBuilder strQueryBuilder = new StringBuilder();

		/** Create query */
		//if the group is not null, select groups according the default unit of the user
		if (group != null || role != null)
		{
			strQueryBuilder.append("select us from CUser us " + "left join fetch us.organization o " + "left join us.defaultUnit deun " + "left join fetch us.groups gr " + "left join gr.units " + "left join gr.roles ro " + "where ((deun is not null and deun in elements(gr.units)) or (deun is null and gr.units is empty))");
		}
		else
		{
			strQueryBuilder.append("select us from CUser us " + "left join fetch us.organization o " + "where 1=1");
		}

		if (group != null)
		{
			strQueryBuilder.append(" and gr=:group");
		}

		if (role != null)
		{
			strQueryBuilder.append(" and ro=:role");
		}

		if (organization != null)
		{
			strQueryBuilder.append(" and o=:organization");
		}

		if (enabled != null)
		{
			strQueryBuilder.append(" and us.flagEnabled=:enabled");
		}

		if (excludedUser != null)
		{
			strQueryBuilder.append(" and us!=:excludedUser");
		}

		/** Create group by section.  */
		//workaround - use distinct with order by
		strQueryBuilder.append(" group by us.pkId, us.login, us.name, us.surname, us.email, us.flagEnabled, us.userType, us.authenticationParams, us.organization, us.defaultUnit, o.pkId, o.email, o.phone, o.flagEnabled, o.name, o.code");

		/** Create order by section. */
		if (orderByOrganization == true)
		{
			strQueryBuilder.append(" order by o.name, us.login");
		}
		else
		{
			strQueryBuilder.append(" order by us.login");
		}

		//create query
		Query query = getEntityManager().createQuery(strQueryBuilder.toString());

		/** Set parameters. */
		if (group != null)
		{
			query.setParameter("group", group);
		}

		if (role != null)
		{
			query.setParameter("role", role);
		}

		if (organization != null)
		{
			query.setParameter("organization", organization);
		}

		if (enabled != null)
		{
			query.setParameter("enabled", enabled);
		}

		if (excludedUser != null)
		{
			query.setParameter("excludedUser", excludedUser);
		}

		List<CUser> users = (List<CUser>) query.getResultList();

		return users;
	}
}
