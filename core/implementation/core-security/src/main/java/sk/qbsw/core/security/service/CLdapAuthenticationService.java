package sk.qbsw.core.security.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.directory.api.util.exception.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CInvalidUserException;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;
import sk.qbsw.core.security.service.ldap.CLdapProvider;

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

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The ldap provider. */
	@Autowired
	private CLdapProvider ldapProvider;

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
		CUser user = userDao.findByLogin(login, unit);

		if (user != null)
		{
			//authenticate user in ldap
			if (authenticateUser(login, password) == true)
			{
				return user;
			}
			else
			{
				throw new CWrongPasswordException("Password in ldap for user " + login + " doesn't match");
			}
		}
		else
		{
			throw new CInvalidUserException("The user with login " + login + " not found");
		}
	}

	/**
	 * Authenticate user in LDAP.
	 *
	 * @param login the login
	 * @param password the password
	 * @return true, if successful
	 */
	private boolean authenticateUser (String login, String password)
	{
		try
		{
			//create connection
			ldapProvider.createConnection(data.getServerName(), data.getServerPort());
			ldapProvider.bindOnServer(data.getUserDn(), data.getUserPassword());

			//authenticate
			ldapProvider.authenticate(data.getUserSearchBaseDn(), "(&(cn=" + login + "))", password);

			return true;
		}
		catch (CSecurityException ex)
		{
			return false;
		}
		finally
		{
			//close connection
			ldapProvider.unbindFromServer();
			ldapProvider.closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#createEncryptedPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public CAuthenticationParams createEncryptedPassword (String login, String password) throws CSecurityException
	{
		try
		{
			//create connection
			ldapProvider.createConnection(data.getServerName(), data.getServerPort());
			ldapProvider.bindOnServer(data.getUserDn(), data.getUserPassword());

			//create dn
			StringBuilder dnBuilder = new StringBuilder();
			dnBuilder.append("cn=").append(login).append(",").append(data.getUserSearchBaseDn()).toString();

			if (ldapProvider.entryExists(dnBuilder.toString()) == false)
			{
				//add auth data
				Map<String, String[]> attributes = new HashMap<String, String[]>();
				attributes.put("objectClass", new String[] {data.getUserObjectClass()});
				attributes.put("cn", new String[] {login});
				attributes.put("sn", new String[] {login});
				attributes.put("userPassword", new String[] {password});

				//add entry
				ldapProvider.addEntry(dnBuilder.toString(), attributes);
			}
			else
			{
				//change password
				changeEncryptedPassword(login, password);
			}

		}
		finally
		{
			//close connection
			ldapProvider.unbindFromServer();
			ldapProvider.closeConnection();
		}

		//and returns empty authentications params
		return new CAuthenticationParams();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeEncryptedPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void changeEncryptedPassword (String login, String password) throws CSecurityException
	{
		try
		{
			//create connection
			ldapProvider.createConnection(data.getServerName(), data.getServerPort());
			ldapProvider.bindOnServer(data.getUserDn(), data.getUserPassword());

			//change password
			StringBuilder dnBuilder = new StringBuilder();
			ldapProvider.modifyEntry(dnBuilder.append("cn=").append(login).append(",").append(data.getUserSearchBaseDn()).toString(), "userPassword", password);
		}
		finally
		{
			//close connection
			ldapProvider.unbindFromServer();
			ldapProvider.closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changePlainPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void changePlainPassword (String login, String email, String password) throws CSecurityException
	{
		throw new NotImplementedException();
	}
}
