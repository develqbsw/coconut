package sk.qbsw.core.security.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CInvalidUserException;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;

/**
 * The LDAP authentication service.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Service (value = "ldapAuthenticationService")
public class CLdapAuthenticationService implements IAuthenticationService
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
	 * @see sk.qbsw.core.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean canLogin (String login, String password, CRole role)
	{
		try
		{
			return login(login, password, role) != null;
		}
		catch (CSecurityException ex)
		{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, String password) throws CSecurityException
	{
		return loginUser(login, password, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, String password, CRole role) throws CSecurityException
	{
		CUser user = loginUser(login, password, null);

		//checks if the user has the role
		if (!user.hasRole(role))
		{
			throw new CSecurityException("User " + login + " has no such role");
		}

		return user;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, String password, String unit) throws CSecurityException
	{
		CUnit databaseUnit = unitDao.findByName(unit);
		CUser user = null;

		if (databaseUnit != null)
		{
			user = loginUser(login, password, databaseUnit);
		}
		else
		{
			user = loginUser(login, password, null);
		}

		if (user.isInUnit(databaseUnit) == true)
		{
			return user;
		}
		else
		{
			throw new CSecurityException("User is not is unit with name " + unit);
		}
	}

	/**
	 * Login user - if the unit is null, use user default unit (if not null) otherwise use unit.
	 *
	 * @param login the login
	 * @param password the password
	 * @param unit the unit - optional
	 * @return the user
	 * @throws CSecurityException the security exception
	 */
	private CUser loginUser (String login, String password, CUnit unit) throws CSecurityException
	{
		//gets user from ldap - all information in this object are now from ldap
		CUser user = userLdapDao.findByLogin(login);

		if (user != null)
		{
			//authenticate user in ldap
			if (AuthenticateUser(login, password) == true)
			{
				//reads groups, organization and defaultUnit from database and replaces the data from LDAP server in user object
				initUserWithDatabaseData(user, unit);
				return user;
			}
			else
			{
				throw new CWrongPasswordException("Password in ldap for user " + login + " doesn't match");
			}
		}
		else
		{
			throw new CInvalidUserException("The user with login " + login + " not recognised");
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

	/**
	 * Authenticate user in LDAP.
	 *
	 * @param login the login
	 * @param password the password
	 * @return true, if successful
	 * @throws CSecurityException the security exception
	 */
	private boolean AuthenticateUser (String login, String password) throws CSecurityException
	{
		LdapConnection connection = null;
		EntryCursor cursor = null;

		try
		{
			//creates and bind to connection
			connection = new LdapNetworkConnection(data.getServerName(), data.getServerPort());
			connection.bind(data.getUserDn(), data.getUserPassword());

			//search user
			cursor = connection.search(data.getUserSearchBaseDn(), "(&(uid=" + login + "))", SearchScope.SUBTREE, "*");

			if (cursor.next() == true)
			{
				//get user data
				Entry userEntry = cursor.get();

				//checks if the user is unique
				if (cursor.next() == true)
				{
					throw new CInvalidUserException("The user with login " + login + " is not unique.");
				}

				try
				{
					//bind as user (with his DN) to verify password
					connection.unBind();
					connection.bind(userEntry.getDn(), password);
					return true;
				}
				catch (LdapException ex)
				{
					return false;
				}
			}
			else
			{
				throw new CInvalidUserException("The user with login " + login + " not recognised");
			}
		}
		catch (LdapInvalidAttributeValueException ex)
		{
			throw new CSecurityException("An LdapInvalidAttributeValueException exception raised: " + ex.toString());
		}
		catch (LdapException ex)
		{
			throw new CSecurityException("An LdapException exception raised: " + ex.toString());
		}
		catch (CursorException ex)
		{
			throw new CSecurityException("An CursorException exception raised: " + ex.toString());
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
			if (connection != null)
			{
				try
				{
					connection.unBind();
				}
				catch (LdapException ex)
				{
				}
				try
				{
					connection.close();
				}
				catch (IOException ex)
				{
				}
			}
		}
	}
}
