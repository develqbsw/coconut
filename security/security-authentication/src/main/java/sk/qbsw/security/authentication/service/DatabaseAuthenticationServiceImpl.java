package sk.qbsw.security.authentication.service;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.base.exception.InvalidAuthenticationException;
import sk.qbsw.core.security.base.exception.InvalidPasswordException;
import sk.qbsw.core.security.base.exception.InvalidUserException;
import sk.qbsw.core.security.base.exception.UserDisabledException;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.core.dao.IAuthenticationParamsDao;
import sk.qbsw.security.core.dao.IUnitDao;
import sk.qbsw.security.core.dao.IUserDao;
import sk.qbsw.security.core.model.domain.CAuthenticationParams;
import sk.qbsw.security.core.model.domain.CRole;
import sk.qbsw.security.core.model.domain.CUnit;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.core.model.domain.EAuthenticationType;
import sk.qbsw.security.core.service.signature.IPasswordDigester;

/**
 * Authentication service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Service (value = "cLoginService")
public class DatabaseAuthenticationServiceImpl extends AService implements AuthenticationService
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** LOGGER for authentication messages logging. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseAuthenticationServiceImpl.class);

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The authentication params dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

	/** Password digester *. */
	@Autowired
	private transient IPasswordDigester digester;

	/**
	 * Authenticate by digest.
	 *
	 * @param authenticationParams the authentication parameters of user
	 * @param passwordToCheck the password to check
	 * @throws InvalidPasswordException the c wrong password exception
	 */
	private void authenticateByPassword (CAuthenticationParams authenticationParams, String passwordToCheck) throws InvalidPasswordException
	{
		if (authenticationParams.getPassword() == null || !authenticationParams.getPassword().equals(passwordToCheck))
		{
			throw new InvalidPasswordException("Plain password doesn't match");
		}
	}

	/**
	 * Authenticate by password.
	 *
	 * @param authenticationParams the authentication parameters of user
	 * @param login the login
	 * @param passwordToCheck the password to check
	 * @throws InvalidPasswordException the c wrong password exception
	 */
	private void authenticateByPasswordDigest (CAuthenticationParams authenticationParams, String login, String passwordToCheck) throws InvalidPasswordException
	{
		if (authenticationParams.getPasswordDigest() == null || !digester.checkPassword(login, passwordToCheck, authenticationParams.getPasswordDigest()))
		{
			throw new InvalidPasswordException("Password dogest doesn't match");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String, sk.qbsw.security.core.core.model.domain.CRole)
	 */
	@Transactional (readOnly = true)
	public boolean canLogin (String login, @CNotLogged @CNotAuditLogged String password, CRole role)
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

	/* (non-Javadoc)
	 * @see sk.qbsw.winnetou.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		return loginWithUnit(login, password, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.security.core.core.model.domain.CRole)
	 */
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password, CRole role) throws CSecurityException
	{
		CUser user = loginWithUnit(login, password, null);

		if (!user.hasRole(role))
		{
			throw new CSecurityException("User has not a role with code " + role.getCode());
		}

		return user;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#login(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password, String unit) throws CSecurityException
	{
		//checks if the unit exists
		CUnit localUnit = null;
		try
		{
			localUnit = unitDao.findOneByName(unit);
		}
		catch (NoResultException ex)
		{
			LOGGER.error("Unit not found", ex);
			throw new CSecurityException("There is not a unit with name " + unit);
		}

		//find user
		CUser user = loginWithUnit(login, password, localUnit);

		if (user.isInUnit(localUnit))
		{
			return user;
		}
		else
		{
			throw new CSecurityException("User is not is unit with name " + localUnit.getName());
		}
	}

	/**
	 * Login with unit.
	 *
	 * @param login the login
	 * @param password the password
	 * @param unit the unit - the unit is optional parameter
	 * @return the user
	 * @throws UserDisabledException the user is disabled
	 * @throws InvalidAuthenticationException the user has invalid authentication params
	 * @throws InvalidUserException the user with given login not found
	 * @throws InvalidPasswordException the invalid password
	 */
	private CUser loginWithUnit (String login, String password, CUnit unit) throws UserDisabledException, InvalidAuthenticationException, InvalidUserException, InvalidPasswordException
	{
		LOGGER.debug("trying to login user with login {} and unit{} ", new Object[] {login, unit});

		CUser user;

		try
		{
			user = userDao.findOneByLoginAndUnit(login, unit);
		}
		catch (NoResultException | CSecurityException e)
		{
			LOGGER.error("User not found by unit and login", e);
			user = null;
		}

		if (user == null)
		{
			throw new InvalidUserException("User not recognised");
		}
		else
		{
			CAuthenticationParams userAuthParams = null;
			try
			{
				userAuthParams = authenticationParamsDao.findOneValidByUserId(user.getId());
			}
			catch (NoResultException ex)
			{
				LOGGER.debug("The authentication params of user with login {} are invalid", user.getLogin());
				LOGGER.error("Valid authentication params not found", ex);

				throw new InvalidAuthenticationException("Authentication params are invalid");
			}

			EAuthenticationType authenticationType = userAuthParams.getAuthenticationType();
			switch (authenticationType)
			{
				case BY_PASSWORD_DIGEST:
					authenticateByPasswordDigest(userAuthParams, login, password);
					break;
				case BY_PASSWORD:
					authenticateByPassword(userAuthParams, password);
					break;
				default:
					throw new InvalidPasswordException("Authentication method wrong");
			}

			// check if user is disabled
			if ( (user.getOrganization().getFlagEnabled() != null && user.getOrganization().getFlagEnabled().equals(false)) || (user.getFlagEnabled() != null && user.getFlagEnabled().equals(false)))
			{
				throw new UserDisabledException("");
			}
		}

		LOGGER.debug("user with login {} and unit{} found. ", new Object[] {login, unit});

		return user;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#isOnline()
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean isOnline ()
	{
		try
		{
			userDao.countAll();
			return true;
		}
		catch (PersistenceException ex)
		{
			LOGGER.debug("CountAll exception", ex);
			return false;
		}
	}
}
