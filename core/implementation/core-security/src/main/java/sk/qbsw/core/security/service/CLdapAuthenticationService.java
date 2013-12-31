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
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CInvalidUserException;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;

// TODO: Auto-generated Javadoc
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, String password) throws CSecurityException
	{
		//gets user from ldap - all information in this object are now from ldap
		CUser user = userLdapDao.findByLogin(login);

		if (user != null)
		{
			//authenticate user in ldap
			if (AuthenticateUser(login, password) == true)
			{
				//reads groups and organization from database and replaces the data from LDAP server in user object
				//the user object is connected to licenses and roles after this step
				user.setGroups(getDBGroups(user.getGroups()));
				user.setOrganization(getDBOrganization());
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, String password, CRole role) throws CSecurityException
	{
		CUser user = login(login, password);

		//checks if the user has the role
		if (!user.hasRole(role))
		{
			throw new CSecurityException("User " + login + " has no such role");
		}

		return user;
	}

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

	/**
	 * Gets the groups from DB based on the informations from LDAP.
	 *
	 * @param ldapGroups the LDAP groups
	 * @return the DB groups
	 */
	private Set<CGroup> getDBGroups (Set<CGroup> ldapGroups)
	{
		Set<CGroup> dbGroups = new HashSet<CGroup>();

		//find groups in database
		for (CGroup ldapGroup : ldapGroups)
		{
			List<CGroup> findByCodeResult = groupDao.findByCodeFetchRoles(ldapGroup.getCode());

			dbGroups.addAll(findByCodeResult);
		}

		return dbGroups;
	}

	/**
	 * Gets the organization from DB based on the informations from LDAP.
	 *
	 * @return the DB organization
	 */
	private COrganization getDBOrganization ()
	{
		return organizationDao.findById(data.getUserOrganizationId());
	}

	/**
	 * Authenticate user in LDAP.
	 *
	 * @param login the login
	 * @param password the password
	 * @return true, if successful
	 * @throws CSecurityException the c security exception
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public CUser login (String login, String password, String organizationUnit) throws CSecurityException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
