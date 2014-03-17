package sk.qbsw.core.security.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.directory.api.util.exception.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
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
import sk.qbsw.core.security.service.ldap.CLdapProvider.EModificationOperation;

/**
 * The LDAP authentication service.
 * 
 * @author Tomas Lauro
 * @version 1.7.2
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

	/** The authentication params dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

	/** The ldap provider. */
	@Autowired
	private CLdapProvider ldapProvider;

	/** The authentication data validation service. */
	@Autowired
	private IAuthDataValidationService authDataValidationService;

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
		CUser user;

		try
		{
			user = userDao.findByLogin(login, unit);
		}
		catch (NoResultException nre)
		{
			user = null;
		}

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
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeEncryptedPassword(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeEncryptedPassword (String login, String password) throws CSecurityException
	{
		CUser user = userDao.findByLogin(login);

		//validate password, if not valid throw an exception
		authDataValidationService.validatePassword(password);

		try
		{
			//create connection
			ldapProvider.createConnection(data.getServerName(), data.getServerPort());
			ldapProvider.bindOnServer(data.getUserDn(), data.getUserPassword());

			//create dn
			String userDn = new StringBuilder().append("cn=").append(login).append(",").append(data.getUserSearchBaseDn()).toString();

			if (ldapProvider.entryExists(userDn) == true)
			{
				//change password
				ldapProvider.modifyEntry(userDn, "userPassword", password, EModificationOperation.REPLACE_ATTRIBUTE);
			}
			else
			{
				//add auth data
				Map<String, String[]> attributes = new HashMap<String, String[]>();
				attributes.put("objectClass", new String[] {data.getUserObjectClass()});
				attributes.put("cn", new String[] {login});
				attributes.put("sn", new String[] {user.getSurname()});
				attributes.put("userPassword", new String[] {password});

				//add entry
				ldapProvider.addEntry(userDn, attributes);
			}

			//set auth params
			CAuthenticationParams authParams = null;
			try
			{
				authParams = authenticationParamsDao.findByUserId(user.getId());
			}
			catch (NoResultException ex)
			{
				//create new because user has no auth params
				authParams = new CAuthenticationParams();
				authParams.setUser(user);
				authParams.setPassword(null);
				authParams.setPasswordDigest(null);
				authParams.setPin(null);
				authenticationParamsDao.save(authParams);
			}
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeLogin(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeLogin (Long userId, String login) throws CSecurityException
	{
		CUser user = userDao.findById(userId);

		try
		{
			//create connection
			ldapProvider.createConnection(data.getServerName(), data.getServerPort());
			ldapProvider.bindOnServer(data.getUserDn(), data.getUserPassword());

			//create dn
			String userDn = new StringBuilder().append("cn=").append(user.getLogin()).append(",").append(data.getUserSearchBaseDn()).toString();
			String newRdn = new StringBuilder().append("cn=").append(login).toString();

			//the record in LDAP is updated only if it had existed
			if (ldapProvider.entryExists(userDn) == true)
			{
				//change login
				ldapProvider.renameEntry(userDn, newRdn, true);
			}

			//save user login in DB
			user.setLogin(login);
			userDao.save(user);
		}
		finally
		{
			//close connection
			ldapProvider.unbindFromServer();
			ldapProvider.closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#isOnline()
	 */
	@Override
	public boolean isOnline ()
	{
		try
		{
			ldapProvider.createConnection(data.getServerName(), data.getServerPort());
			return true;
		}
		catch (CSystemException ex)
		{
			return false;
		}
		finally
		{
			ldapProvider.closeConnection();
		}
	}
}
