package sk.qbsw.core.security.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CInvalidUserException;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;

/**
 * The LDAP authorization service.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Service (value = "ldapAuthorizationService")
public class CLdapAuthorizationService implements IAuthorizationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data. */
	@Autowired
	private ILdapAuthenticationConfigurator data;

	/** The user ldap dao. */
	@Autowired
	private IUserDao userLdapDao;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** The organization dao. */
	@Autowired
	private IOrganizationDao organizationDao;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthorizationService#checkAccessRights(java.lang.String, sk.qbsw.core.security.model.domain.CRole, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public void checkAccessRights (String login, CRole role, String unit, String category) throws CSecurityException
	{
		CUnit databaseUnit = getDatabaseUnit(unit);
		CUser user = getUserByLoginAndUnit(login, databaseUnit);

		if (user == null)
		{
			throw new CInvalidUserException("The user with login " + login + " not recognised");
		}
		else
		{
			//if the user has not a role throw an exception
			if (user.hasRole(role) == false)
			{
				throw new CSecurityException("User has not a role with code " + role.getCode());
			}

			if (databaseUnit != null)
			{
				if (user.isInUnit(databaseUnit) == false)
				{
					throw new CSecurityException("User is not is unit with name " + databaseUnit.getName());
				}
			}

			if (category != null && user.hasCategory(category, role) == false)
			{
				throw new CSecurityException("User has not a category with name " + category);
			}
		}
	}

	/**
	 * Gets user with specified login and unit. If unit is null, the user default unit is used.
	 *
	 * @param login the login
	 * @param unit the unit - optional
	 * @return the user
	 * @throws CSecurityException the security exception
	 */
	private CUser getUserByLoginAndUnit (String login, CUnit unit) throws CSecurityException
	{
		//gets user from ldap - all information in this object are now from ldap
		CUser user = userLdapDao.findByLogin(login);

		if (user != null)
		{
			//reads groups, organization and defaultUnit from database and replaces the data from LDAP server in user object
			initUserWithDatabaseData(user, unit);
			return user;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Initializes the user with database data.
	 *
	 * @param user the user
	 * @param unit the unit - optional
	 * @throws CSecurityException the c security exception
	 */
	private void initUserWithDatabaseData (CUser user, CUnit unit) throws CSecurityException
	{
		CUnit databaseUnit = null;

		//get unit from parameter. If it is null, get unit from user defaultUnit
		if (unit != null)
		{
			databaseUnit = unit;
		}
		else if (unit == null && user.getDefaultUnit() != null && user.getDefaultUnit().getName() != null)
		{
			databaseUnit = getDatabaseUnit(user.getDefaultUnit().getName());
			user.setDefaultUnit(databaseUnit);
		}

		//create a set of users with one ldap user
		Set<CUser> usersToSet = new HashSet<CUser>();
		usersToSet.add(user);

		Set<CGroup> databaseGroups = getDatabaseGroups(user, databaseUnit);
		for (CGroup databaseGroup : databaseGroups)
		{
			databaseGroup.setUsers(usersToSet);
		}

		user.setGroups(databaseGroups);
		user.setOrganization(getDatabaseOrganization());

	}

	/**
	 * Gets the groups from database based on the informations from LDAP.
	 *
	 * @param ldapUser user got from ldap
	 * @param databaseUnit the database unit - optional
	 * @return the database groups
	 */
	private Set<CGroup> getDatabaseGroups (CUser ldapUser, CUnit databaseUnit)
	{
		Set<CGroup> databaseGroups = new HashSet<CGroup>();

		//find groups in database
		for (CGroup ldapGroup : ldapUser.getGroups())
		{
			List<CGroup> findByCodeResult = groupDao.findByCode(ldapGroup.getCode(), databaseUnit);
			databaseGroups.addAll(findByCodeResult);
		}

		return databaseGroups;
	}

	/**
	 * Gets the organization from database based on the informations from LDAP.
	 *
	 * @return the database organization
	 */
	private COrganization getDatabaseOrganization ()
	{
		return organizationDao.findById(data.getUserOrganizationId());
	}

	/**
	 * Gets the unit from database based on the unit name.
	 *
	 * @param unitName the unit name
	 * @return the database unit
	 * @throws CSecurityException the security exception
	 */
	private CUnit getDatabaseUnit (String unitName) throws CSecurityException
	{
		CUnit databaseUnit = null;

		//get unit from database, if there is a unit name
		if (unitName != null)
		{
			databaseUnit = unitDao.findByName(unitName);
		}

		//if there is a unit without object in a database, throw exception
		if (unitName != null && databaseUnit == null)
		{
			throw new CSecurityException("The unit with name " + unitName + " was not found in the DB");
		}
		else
		{
			return databaseUnit;
		}
	}
}
