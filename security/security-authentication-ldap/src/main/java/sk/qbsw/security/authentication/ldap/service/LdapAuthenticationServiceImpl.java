package sk.qbsw.security.authentication.ldap.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.base.exception.InvalidPasswordException;
import sk.qbsw.core.security.base.exception.InvalidUserException;
import sk.qbsw.core.security.base.exception.UserDisabledException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.ldap.configuration.LdapAuthenticationConfigurator;
import sk.qbsw.security.authentication.ldap.provider.LDAPInjectionProtector;
import sk.qbsw.security.authentication.ldap.provider.LdapProvider;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Unit;

/**
 * The LDAP authentication service.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
@Service (value = "ldapAuthenticationService")
public class LdapAuthenticationServiceImpl extends AService implements AuthenticationService
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LdapAuthenticationServiceImpl.class);

	/** The data. */
	@Autowired
	private transient LdapAuthenticationConfigurator data;

	/** The unit dao. */
	@Autowired
	private UnitDao unitDao;

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

	/** The ldap provider. */
	@Autowired
	private transient LdapProvider ldapProvider;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.service.IAuthenticationService#canLogin(java.lang .String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean canLogin (String login, @CNotLogged @CNotAuditLogged String password, Role role)
	{
		try
		{
			return login(login, password, role) != null;
		}
		catch (CSecurityException ex)
		{
			LOGGER.debug("User is not allowed to login", ex);
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang. String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		return loginUser(login, password, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang. String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password, Role role) throws CSecurityException
	{
		Account user = loginUser(login, password, null);

		// checks if the user has the role
		if (!user.hasRole(role))
		{
			throw new CSecurityException("User " + login + " has no such role");
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang. String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password, String unit) throws CSecurityException
	{
		Unit databaseUnit = null;
		Account user = null;

		try
		{
			databaseUnit = unitDao.findOneByName(unit);
			user = loginUser(login, password, databaseUnit);
		}
		catch (NoResultException ex)
		{
			LOGGER.debug("User login retry", ex);
			user = loginUser(login, password, null);
		}

		if (user.isInUnit(databaseUnit))
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
	private Account loginUser (String login, String password, Unit unit) throws CSecurityException
	{
		// gets user from ldap - all information in this object are now from
		// ldap
		Account user;

		try
		{
			user = userDao.findOneByLoginAndUnit(login, unit);
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("User not found", nre);
			user = null;
		}

		if (user != null)
		{
			// check if user is disabled
			if ( (user.getOrganization().getFlagEnabled() != null && user.getOrganization().getFlagEnabled().equals(false)) || (user.getFlagEnabled() != null && user.getFlagEnabled().equals(false)))
			{
				throw new UserDisabledException("");
			}

			// authenticate user in ldap
			if (authenticateUser(login, password))
			{
				return user;
			}
			else
			{
				throw new InvalidPasswordException("Password in ldap for user " + login + " doesn't match");
			}
		}
		else
		{
			throw new InvalidUserException("The user with login " + login + " not found");
		}
	}

	/**
	 * Authenticate user in LDAP.
	 *
	 * @param login the login
	 * @param password the password
	 * @return true, if successful
	 * @throws CSecurityException the configuration is corrupted
	 */
	private boolean authenticateUser (String login, String password) throws CSecurityException
	{
		// the exceptions thrown in ldap authentication process
		List<Throwable> exceptions = new ArrayList<>();

		if (data.getUserSearchBaseDns() != null)
		{
			for (String userSearchDn : data.getUserSearchBaseDns())
			{
				try
				{
					String escapedLogin = LDAPInjectionProtector.escapeDN(login);
					LOGGER.debug("LDAP escaped login:" + escapedLogin);

					// authenticate
					ldapProvider.authenticate(userSearchDn, String.format(data.getUserSearchFilter(), escapedLogin), password);
					LOGGER.debug("User " + login + " was authenticated by LDAP in tree " + userSearchDn);

					return true;
				}
				catch (Exception ex)
				{
					exceptions.add(ex);
					continue;
				}
			}

			for (int i = 0; i < exceptions.size(); i++)
			{
				LOGGER.error("LDAP authentication error in " + (i + 1) + ". baseDn: ", exceptions.get(i));
			}

			return false;
		}
		else
		{
			throw new CSecurityException("Ldap configuration corrupted");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sk.qbsw.core.security.service.IAuthenticationService#isOnline()
	 */
	@Override
	public boolean isOnline ()
	{
		return ldapProvider.isConnected();
	}
}
