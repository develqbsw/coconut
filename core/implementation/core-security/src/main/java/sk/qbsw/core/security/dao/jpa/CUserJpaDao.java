/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
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
 * 
 * @version 1.10.3
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
	 * Find by id. If there is no result, throws NoResultException.
	 * 
	 * @param id the id
	 * @return the c user
	 * @see sk.qbsw.core.security.dao.IUserDao#findById(java.lang.Long)
	 */
	@Override
	public CUser findById (Long id)
	{
		//get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			//set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			String strQuery = "select distinct(us) from CUser us " +
						"left join fetch us.organization " +
						"left join fetch us.defaultUnit " +
						"left join fetch us.xUserUnitGroups xuug " +
						"left join fetch xuug.group gr " +
						"left join fetch xuug.unit " +
						"left join fetch gr.roles " +
						"where us.id=:id";

			Query query = getEntityManager().createQuery(strQuery);
			query.setParameter("id", id);

			//throws exception if there is no result or multiple results
			return (CUser) query.getSingleResult();
		}
		finally
		{
			//disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findForModification(java.lang.Long)
	 */
	@Override
	public CUser findForModification (Long id)
	{
		return findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String)
	 */
	@Override
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
	@Override
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
	private CUser findByLoginAndUnit (String login, CUnit unit)
	{
		//get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);
		StringBuilder strQueryBuilder = new StringBuilder();
		Query query = null;

		try
		{
			strQueryBuilder.append("select distinct(us) from CUser us " +
							"left join fetch us.organization " +
							"left join fetch us.defaultUnit " +
							"left join fetch us.xUserUnitGroups xuug " +
							"left join fetch xuug.group gr " +
							"left join fetch xuug.unit " +
							"left join fetch gr.roles " +
							"where us.login=:login");
			// 1. The unit has been set
			if (unit != null)
			{
				strQueryBuilder.append(" and :unit in xuug.unit");

				query = getEntityManager().createQuery(strQueryBuilder.toString());
				query.setParameter("login", login);
				query.setParameter("unit", unit);
			}
			// 2. The unit has not been set
			else
			{
				//set filter to get just groups with proper default units
				session.enableFilter("userDefaultUnitFilter");

				query = getEntityManager().createQuery(strQueryBuilder.toString());
				query.setParameter("login", login);
			}

			return (CUser) query.getSingleResult();
		}
		finally
		{
			//disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.dao.IUserDao#findByPinNull(java.lang.String)
	 */
	@Override
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
	 * @see sk.qbsw.core.security.dao.IUserDao#countAllUsers()
	 */
	@Override
	public int countAllUsers ()
	{
		String strQuery = "select count(u) from CUser u";

		Query query = getEntityManager().createQuery(strQuery);
		return ((Number) query.getSingleResult()).intValue();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers()
	 */
	@Override
	public List<CUser> findAllUsers ()
	{
		return findAllUsers(null, null, null, null, null, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
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
	@Override
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
	@Override
	public List<CUser> findAllUsers (COrganization organization, Boolean enabled, CGroup group)
	{
		return findAllUsers(organization, enabled, group, null, null, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsersOrderByOrganization(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	public List<CUser> findAllUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group)
	{
		return findAllUsers(organization, enabled, group, null, null, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsersByRole(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
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
	@Override
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
	@Override
	public List<CUser> findAllUsers (String name, String surname, String login, Boolean enabled)
	{
		return findAllUsers(name, surname, login, null, enabled, null, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	public List<CUser> findAllUsers (String name, String surname, String login, Boolean enabled, COrganization organization)
	{
		return findAllUsers(name, surname, login, null, enabled, null, organization);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	@Override
	public List<CUser> findAllUsers (String name, String surname, String login, Boolean enabled, String groupCodePrefix)
	{
		return findAllUsers(name, surname, login, null, enabled, groupCodePrefix, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(java.lang.String)
	 */
	@Override
	public List<CUser> findAllUsers (String email)
	{
		if (email == null)
		{
			throw new CSystemException("The mandatory parameter email is missing");
		}
		else
		{
			return findAllUsers(null, null, null, email, null, null, null);
		}
	}

	/**
	 * Find all users.
	 *
	 * @param name the name
	 * @param surname the surname
	 * @param login the login
	 * @param email the email
	 * @param enabled the enabled
	 * @param groupCodePrefix the group code prefix
	 * @param organization the user organization
	 * @return the list
	 */
	@SuppressWarnings ("unchecked")
	private List<CUser> findAllUsers (String name, String surname, String login, String email, Boolean enabled, String groupCodePrefix, COrganization organization)
	{
		//get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);
		StringBuilder strQueryBuilder = new StringBuilder();

		try
		{
			//set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			/** Create query */
			strQueryBuilder.append("select distinct(us) from CUser us " +
							"left join us.organization " +
							"left join fetch us.defaultUnit " +
							"left join fetch us.xUserUnitGroups xuug " +
							"left join fetch xuug.group gr " +
							"left join fetch xuug.unit " +
							"where 1=1");

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

			if (email != null)
			{
				strQueryBuilder.append(" and us.email=:email");
			}

			if (groupCodePrefix != null)
			{
				strQueryBuilder.append(" and gr.code like :groupCodePrefix");
			}

			if (enabled != null)
			{
				strQueryBuilder.append(" and us.flagEnabled=:enabled");
			}

			if (organization != null)
			{
				strQueryBuilder.append(" and us.organization=:organization");
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

			if (email != null)
			{
				query.setParameter("email", email);
			}

			if (groupCodePrefix != null)
			{
				query.setParameter("groupCodePrefix", "%" + groupCodePrefix + "%");
			}

			if (enabled != null)
			{
				query.setParameter("enabled", enabled);
			}

			if (organization != null)
			{
				query.setParameter("organization", organization);
			}

			return (List<CUser>) query.getResultList();
		}
		finally
		{
			//disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
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
		//get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);
		StringBuilder strQueryBuilder = new StringBuilder();

		try
		{
			//set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			/** Create query */
			strQueryBuilder.append("select distinct(us) from CUser us " +
							"left join fetch us.organization o " +
							"left join fetch us.defaultUnit " +
							"left join fetch us.xUserUnitGroups xuug " +
							"left join fetch xuug.group gr " +
							"left join fetch xuug.unit " +
							"left join gr.roles ro " +
							"where 1=1");

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

			return (List<CUser>) query.getResultList();
		}
		finally
		{
			//disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findAllUsers(sk.qbsw.core.security.model.domain.CUnit, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public List<CUser> findAllUsers (CUnit unit, CGroup group)
	{
		StringBuilder strQueryBuilder = new StringBuilder();
		strQueryBuilder.append("select distinct(us) from CUser us " +
						"left join fetch us.xUserUnitGroups xuug " +
						"left join fetch xuug.unit un " +
						"left join fetch xuug.group gr " +
						"where 1=1");

		if (unit != null)
		{
			strQueryBuilder.append(" and un=:unit");
		}

		if (group != null)
		{
			strQueryBuilder.append(" and gr=:group");
		}

		strQueryBuilder.append(" order by us.login");

		Query query = getEntityManager().createQuery(strQueryBuilder.toString());

		if (unit != null)
		{
			query.setParameter("unit", unit);
		}

		if (group != null)
		{
			query.setParameter("group", group);
		}

		return (List<CUser>) query.getResultList();
	}
}
