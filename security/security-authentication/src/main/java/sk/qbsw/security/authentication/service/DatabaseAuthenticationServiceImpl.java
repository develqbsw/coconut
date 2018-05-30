package sk.qbsw.security.authentication.service;

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
import sk.qbsw.core.security.base.exception.InvalidAuthenticationException;
import sk.qbsw.core.security.base.exception.InvalidPasswordException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.core.service.signature.PasswordDigester;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

/**
 * Authentication service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public class DatabaseAuthenticationServiceImpl extends AService implements AuthenticationService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseAuthenticationServiceImpl.class);

	private final AccountDao accountDao;

	private final UnitDao unitDao;

	private final AuthenticationParamsDao authenticationParamsDao;

	private final PasswordDigester digester;

	/**
	 * Instantiates a new Database authentication service.
	 *
	 * @param accountDao the account dao
	 * @param unitDao the unit dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param digester the digester
	 */
	public DatabaseAuthenticationServiceImpl (AccountDao accountDao, UnitDao unitDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester)
	{
		this.accountDao = accountDao;
		this.unitDao = unitDao;
		this.authenticationParamsDao = authenticationParamsDao;
		this.digester = digester;
	}

	private void authenticateByPassword (AuthenticationParams authenticationParams, String passwordToCheck) throws InvalidPasswordException
	{
		if (authenticationParams.getPassword() == null || !authenticationParams.getPassword().equals(passwordToCheck))
		{
			throw new InvalidPasswordException("Plain password doesn't match");
		}
	}

	private void authenticateByPasswordDigest (AuthenticationParams authenticationParams, String login, String passwordToCheck) throws InvalidPasswordException
	{
		if (authenticationParams.getPasswordDigest() == null || !digester.checkPassword(login, passwordToCheck, authenticationParams.getPasswordDigest()))
		{
			throw new InvalidPasswordException("Password digest doesn't match");
		}
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
			LOGGER.debug("canLogin failed", ex);
			return false;
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		return loginWithUnit(login, password, null);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password, Role role) throws CSecurityException
	{
		Account account = loginWithUnit(login, password, null);

		if (!account.hasRole(role))
		{
			throw new CSecurityException("Account has not a role with code " + role.getCode());
		}

		return account;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Account login (String login, @CNotLogged @CNotAuditLogged String password, String unit) throws CSecurityException
	{
		// checks if the unit exists
		Unit localUnit;
		try
		{
			localUnit = unitDao.findOneByName(unit);
		}
		catch (NoResultException ex)
		{
			LOGGER.error("Unit not found", ex);
			throw new CSecurityException("There is not a unit with name " + unit);
		}

		// find account
		Account account = loginWithUnit(login, password, localUnit);

		if (account.isInUnit(localUnit))
		{
			return account;
		}
		else
		{
			throw new CSecurityException("Account is not is unit with name " + localUnit.getName());
		}
	}

	private Account loginWithUnit (String login, String password, Unit unit) throws AccountDisabledException, InvalidAuthenticationException, InvalidAccountException, InvalidPasswordException
	{
		LOGGER.debug("trying to login account with login {} and unit{} ", new Object[] {login, unit});

		Account account;

		try
		{
			account = accountDao.findOneByLoginAndUnit(login, unit);
		}
		catch (NoResultException | CSecurityException e)
		{
			LOGGER.error("Account not found by unit and login", e);
			account = null;
		}

		if (account == null)
		{
			throw new InvalidAccountException("Account not recognised");
		}
		else
		{
			AuthenticationParams accountAuthParams;
			try
			{
				accountAuthParams = authenticationParamsDao.findOneValidByAccountId(account.getId());
			}
			catch (NoResultException ex)
			{
				LOGGER.debug("The authentication params of account with login {} are invalid", account.getLogin());
				LOGGER.error("Valid authentication params not found", ex);

				throw new InvalidAuthenticationException("Authentication params are invalid");
			}

			AuthenticationTypes authenticationType = accountAuthParams.getAuthenticationType();
			switch (authenticationType)
			{
				case BY_PASSWORD_DIGEST:
					authenticateByPasswordDigest(accountAuthParams, login, password);
					break;
				case BY_PASSWORD:
					authenticateByPassword(accountAuthParams, password);
					break;
				default:
					throw new InvalidPasswordException("Authentication method wrong");
			}

			// check if account is disabled
			if ( (account.getOrganization().getState() != null && account.getOrganization().getState().equals(ActivityStates.INACTIVE)) || (account.getState() != null && account.getState().equals(ActivityStates.INACTIVE)))
			{
				throw new AccountDisabledException("");
			}
		}

		LOGGER.debug("account with login {} and unit{} found. ", new Object[] {login, unit});

		return account;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean isOnline ()
	{
		try
		{
			accountDao.countAll();
			return true;
		}
		catch (PersistenceException ex)
		{
			LOGGER.debug("CountAll exception", ex);
			return false;
		}
	}
}
