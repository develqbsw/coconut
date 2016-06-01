package sk.qbsw.core.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The authentication service combines the database and LDAP authentication.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.10.5
 */
@Service (value = "mixedAuthenticationService")
public class CMixedAuthenticationService extends CLoginBlockingService implements IAuthenticationService
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CMixedAuthenticationService.class);

	/** The database authentication service. */
	@Autowired
	@Qualifier ("cLoginService")
	private IAuthenticationService databaseAuthenticationService;

	/** The ldap authentication service. */
	@Autowired
	@Qualifier ("ldapAuthenticationService")
	private IAuthenticationService ldapAuthenticationService;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean canLogin (String login, @CNotLogged @CNotAuditLogged String password, CRole role)
	{
		if (ldapAuthenticationService.canLogin(login, password, role) || databaseAuthenticationService.canLogin(login, password, role))
		{
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		return loginUser(login, password, null, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password, CRole role) throws CSecurityException
	{
		return loginUser(login, password, null, role);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password, String unit) throws CSecurityException
	{
		return loginUser(login, password, unit, null);
	}

	/**
	 * Login user - if the unit is null, use user default unit (if not null) otherwise use unit.
	 *
	 * @param login the login
	 * @param password the password
	 * @param unit the unit - optional
	 * @param role the role
	 * @return the user
	 * @throws CSecurityException the security exception
	 */
	private CUser loginUser (String login, String password, String unit, CRole role) throws CSecurityException
	{
		CUser user = null;

		try
		{
			user = callLdapLoginMethod(login, password, unit, role);
			LOGGER.debug("User " + login + " was authenticated by LDAP");

			return user;
		}
		catch (CSecurityException ex)
		{
			LOGGER.debug("User " + login + " was not authenticated by LDAP", ex);

			// try with DB
			user = callDatabaseLoginMethod(login, password, unit, role);
			LOGGER.debug("User " + login + " was authenticated by DB");

			return user;
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#isOnline()
	 */
	@Override
	public boolean isOnline ()
	{
		return databaseAuthenticationService.isOnline() || ldapAuthenticationService.isOnline();
	}
}
