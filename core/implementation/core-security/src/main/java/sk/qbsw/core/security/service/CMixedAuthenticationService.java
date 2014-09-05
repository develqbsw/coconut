package sk.qbsw.core.security.service;

import org.apache.directory.api.util.exception.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The authentication service combines the database and LDAP authentication.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.10.5
 * @since 1.10.5
 */
@Service (value = "mixedAuthenticationService")
public class CMixedAuthenticationService implements IAuthenticationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(CMixedAuthenticationService.class);

	@Autowired
	@Qualifier ("cLoginService")
	private IAuthenticationService databaseAuthenticationService;

	@Autowired
	@Qualifier ("ldapAuthenticationService")
	private IAuthenticationService ldapAuthenticationService;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean canLogin (String login, String password, CRole role)
	{
		if (ldapAuthenticationService.canLogin(login, password, role) == true || databaseAuthenticationService.canLogin(login, password, role) == true)
		{
			return true;
		}
		else
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
		return loginUser(login, password, null, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, String password, CRole role) throws CSecurityException
	{
		return loginUser(login, password, null, role);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, String password, String unit) throws CSecurityException
	{
		return loginUser(login, password, unit, null);
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
	private CUser loginUser (String login, String password, String unit, CRole role) throws CSecurityException
	{
		CUser user = null;

		try
		{
			user = callLdapLoginMethod(login, password, unit, role);
			logger.debug("User " + login + " was authenticated by LDAP");

			return user;
		}
		catch (CSecurityException ex)
		{
			try
			{
				user = callDatabaseLoginMethod(login, password, unit, role);
				logger.debug("User " + login + " was authenticated by DB");

				return user;
			}
			catch (CSecurityException exa)
			{
				throw exa;
			}
		}
	}

	/**
	 * Call ldap login method.
	 *
	 * @param login the login
	 * @param password the password
	 * @param unit the unit
	 * @param role the role
	 * @return the c user
	 * @throws CSecurityException the c security exception
	 */
	private CUser callLdapLoginMethod (String login, String password, String unit, CRole role) throws CSecurityException
	{
		if (unit != null)
		{
			return ldapAuthenticationService.login(login, password, unit);
		}
		else if (role != null)
		{
			return ldapAuthenticationService.login(login, password, role);
		}
		else
		{
			return ldapAuthenticationService.login(login, password);
		}
	}

	/**
	 * Call database login method.
	 *
	 * @param login the login
	 * @param password the password
	 * @param unit the unit
	 * @param role the role
	 * @return the c user
	 * @throws CSecurityException the c security exception
	 */
	private CUser callDatabaseLoginMethod (String login, String password, String unit, CRole role) throws CSecurityException
	{
		if (unit != null)
		{
			return databaseAuthenticationService.login(login, password, unit);
		}
		else if (role != null)
		{
			return databaseAuthenticationService.login(login, password, role);
		}
		else
		{
			return databaseAuthenticationService.login(login, password);
		}
	}

	/**
	 * Not implemented method.
	 *
	 * @param login the login
	 * @param password the password
	 */
	@Override
	public void changeEncryptedPassword (String login, String password)
	{
		throw new NotImplementedException();
	}

	/**
	 * Not implemented method.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 */
	@Override
	public void changePlainPassword (String login, String email, String password) throws CSecurityException
	{
		throw new NotImplementedException();
	}

	/**
	 * Not implemented method.
	 *
	 * @param userId the user ID
	 * @param login the login
	 */
	@Override
	public void changeLogin (Long userId, String login) throws CSecurityException
	{
		throw new NotImplementedException();
	}

	/**
	 * Not implemented method.
	 */
	@Override
	public boolean isOnline ()
	{
		throw new NotImplementedException();
	}
}
