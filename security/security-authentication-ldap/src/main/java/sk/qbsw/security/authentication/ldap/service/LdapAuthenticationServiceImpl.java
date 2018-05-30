package sk.qbsw.security.authentication.ldap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.exception.AccountDisabledException;
import sk.qbsw.core.security.base.exception.InvalidAccountException;
import sk.qbsw.core.security.base.exception.InvalidPasswordException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.ldap.configuration.SecurityLdapAuthenticationConfigurator;
import sk.qbsw.security.authentication.ldap.provider.LDAPInjectionProtector;
import sk.qbsw.security.authentication.ldap.provider.LdapProvider;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Unit;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * The LDAP authentication service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
public class LdapAuthenticationServiceImpl extends AService implements AuthenticationService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LdapAuthenticationServiceImpl.class);

	private final SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator;

	private final UnitDao unitDao;

	private final AccountDao accountDao;

	private final LdapProvider ldapProvider;

	/**
	 * Instantiates a new Ldap authentication service.
	 *
	 * @param ldapAuthenticationConfigurator the ldap authentication configurator
	 * @param unitDao the unit dao
	 * @param accountDao the account dao
	 * @param ldapProvider the ldap provider
	 */
	public LdapAuthenticationServiceImpl (SecurityLdapAuthenticationConfigurator ldapAuthenticationConfigurator, UnitDao unitDao, AccountDao accountDao, LdapProvider ldapProvider)
	{
		this.ldapAuthenticationConfigurator = ldapAuthenticationConfigurator;
		this.unitDao = unitDao;
		this.accountDao = accountDao;
		this.ldapProvider = ldapProvider;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean canLogin (String login, @CNotLogged @CNotAuditLogged String password, Role role)
	{
		try
		{
			return login(login, password, role) != null;
		}
		catch (CSecurityException ex)
		{
			LOGGER.debug("Account is not allowed to login", ex);
			return false;
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		return loginAccount(login, password, null);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password, Role role) throws CSecurityException
	{
		Account account = loginAccount(login, password, null);

		// checks if the account has the role
		if (!account.hasRole(role))
		{
			throw new CSecurityException("Account " + login + " has no such role");
		}

		return account;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password, String unit) throws CSecurityException
	{
		Unit databaseUnit = null;
		Account account = null;

		try
		{
			databaseUnit = unitDao.findOneByName(unit);
			account = loginAccount(login, password, databaseUnit);
		}
		catch (NoResultException ex)
		{
			LOGGER.debug("Account login retry", ex);
			account = loginAccount(login, password, null);
		}

		if (account.isInUnit(databaseUnit))
		{
			return account;
		}
		else
		{
			throw new CSecurityException("Account is not is unit with name " + unit);
		}
	}

	private Account loginAccount (String login, String password, Unit unit) throws CSecurityException
	{
		// gets account from ldap - all information in this object are now from
		// ldap
		Account account;

		try
		{
			account = accountDao.findOneByLoginAndUnit(login, unit);
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("Account not found", nre);
			account = null;
		}

		if (account != null)
		{
			// check if account is disabled
			if ( (account.getOrganization().getState() != null && account.getOrganization().getState().equals(ActivityStates.INACTIVE)) || (account.getState() != null && account.getState().equals(ActivityStates.INACTIVE)))
			{
				throw new AccountDisabledException("");
			}

			// authenticate account in ldap
			if (authenticateAccount(login, password))
			{
				return account;
			}
			else
			{
				throw new InvalidPasswordException("Password in ldap for account " + login + " doesn't match");
			}
		}
		else
		{
			throw new InvalidAccountException("The account with login " + login + " not found");
		}
	}

	private boolean authenticateAccount (String login, String password) throws CSecurityException
	{
		// the exceptions thrown in ldap authentication process
		List<Throwable> exceptions = new ArrayList<>();

		if (ldapAuthenticationConfigurator.getAccountSearchBaseDns() != null)
		{
			for (String accountSearchDn : ldapAuthenticationConfigurator.getAccountSearchBaseDns())
			{
				try
				{
					String escapedLogin = LDAPInjectionProtector.escapeDN(login);
					LOGGER.debug("LDAP escaped login:" + escapedLogin);

					// authenticate
					ldapProvider.authenticate(accountSearchDn, String.format(ldapAuthenticationConfigurator.getAccountSearchFilter(), escapedLogin), password);
					LOGGER.debug("Account " + login + " was authenticated by LDAP in tree " + accountSearchDn);

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

	@Override
	public boolean isOnline ()
	{
		return ldapProvider.isConnected();
	}
}
