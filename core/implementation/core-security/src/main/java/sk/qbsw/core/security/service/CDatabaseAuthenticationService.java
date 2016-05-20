package sk.qbsw.core.security.service;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CInvalidAuthenticationException;
import sk.qbsw.core.security.exception.CInvalidPasswordException;
import sk.qbsw.core.security.exception.CInvalidUserException;
import sk.qbsw.core.security.exception.CUserDisabledException;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.EAuthenticationType;
import sk.qbsw.core.security.service.signature.IPasswordDigester;

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
public class CDatabaseAuthenticationService extends AService implements IAuthenticationService, IAuthenticationModifierService
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * LOGGER for authentication messages logging
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CDatabaseAuthenticationService.class);

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
	private IPasswordDigester digester;

	/** The authentication data validation service. */
	@Autowired
	private IAuthDataValidationService authDataValidationService;

	/**
	 * Authenticate by digest.
	 *
	 * @param authenticationParams the authentication parameters of user
	 * @param passwordToCheck the password to check
	 * @throws CInvalidPasswordException the c wrong password exception
	 */
	private void authenticateByPassword (CAuthenticationParams authenticationParams, String passwordToCheck) throws CInvalidPasswordException
	{
		if (authenticationParams.getPassword() == null || !authenticationParams.getPassword().equals(passwordToCheck))
		{
			throw new CInvalidPasswordException("Plain password doesn't match");
		}
	}

	/**
	 * Authenticate by password.
	 *
	 * @param authenticationParams the authentication parameters of user
	 * @param passwordToCheck the password to check
	 * @throws CInvalidPasswordException the c wrong password exception
	 */
	private void authenticateByPasswordDigest (CAuthenticationParams authenticationParams, String login,String passwordToCheck) throws CInvalidPasswordException
	{
		if (authenticationParams.getPasswordDigest() == null || !digester.checkPassword(login,passwordToCheck, authenticationParams.getPasswordDigest()))
		{
			throw new CInvalidPasswordException("Password dogest doesn't match");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
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
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
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
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, java.lang.String)
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
	 * @throws CUserDisabledException the user is disabled
	 * @throws CInvalidAuthenticationException the user has invalid authentication params
	 * @throws CInvalidUserException the user with given login not found
	 * @throws CInvalidPasswordException the invalid password
	 */
	private CUser loginWithUnit (String login, String password, CUnit unit) throws CUserDisabledException, CInvalidAuthenticationException, CInvalidUserException, CInvalidPasswordException
	{
		LOGGER.debug("trying to login user with login {} and unit{} ", new Object[] {login, unit});

		CUser user;

		try
		{
			user = userDao.findOneByLoginAndUnit(login, unit);
		}
		catch (NoResultException | CSecurityException e)
		{
			user = null;
		}

		if (user == null)
		{
			throw new CInvalidUserException("User not recognised");
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
				throw new CInvalidAuthenticationException("Authentication params are invalid");
			}

			EAuthenticationType authenticationType = userAuthParams.getAuthenticationType();
			switch (authenticationType)
			{
				case BY_PASSWORD_DIGEST:
					authenticateByPasswordDigest(userAuthParams,login, password);
					break;
				case BY_PASSWORD:
					authenticateByPassword(userAuthParams, password);
					break;
				default:
					throw new CInvalidPasswordException("Authentication method wrong");
			}

			// check if user is disabled
			if ( (user.getOrganization().getFlagEnabled() != null && user.getOrganization().getFlagEnabled().equals(false)) || (user.getFlagEnabled() != null && user.getFlagEnabled().equals(false)))
			{
				throw new CUserDisabledException("");
			}
		}

		LOGGER.debug("user with login {} and unit{} found. ", new Object[] {login, unit});

		return user;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeEncryptedPassword(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		changePassword(login, password, null, null, null, true, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeEncryptedPassword(java.lang.String, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password, DateTime validFrom, DateTime validTo) throws CSecurityException
	{
		changePassword(login, password, null, validFrom, validTo, true, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changePlainPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		changePassword(login, password, email, null, null, true, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changePlainPassword(java.lang.String, java.lang.String, java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password, DateTime validFrom, DateTime validTo) throws CSecurityException
	{
		changePassword(login, password, email, validFrom, validTo, true, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeLogin(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeLogin (Long userId, String login)
	{
		CUser user = userDao.findById(userId);
		user.setLogin(login);

		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#isOnline()
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
			return false;
		}
	}

	/**
	 * Change password.
	 *
	 * @param login the login
	 * @param password the password
	 * @param email the email
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @param encrypt the encrypt
	 * @param setValidityDate flag indicates the valid dates are going to be overridden
	 * @throws CSecurityException the c security exception
	 */
	private void changePassword (String login, String password, String email, DateTime validFrom, DateTime validTo, boolean encrypt, boolean setValidityDate) throws CSecurityException
	{
		CUser user = null;

		//validate password, if not valid throw an exception
		authDataValidationService.validatePassword(password);

		try
		{
			user = userDao.findOneByLogin(login);
		}
		catch (NoResultException ex)
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_CHANGE_DENIED);
		}

		//checks email if enctypt flag is false
		if (!encrypt  && email != null && user.getEmail() != null && !email.equals(user.getEmail()))
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_CHANGE_DENIED);
		}

		//set auth params
		CAuthenticationParams authParams = null;
		try
		{
			authParams = authenticationParamsDao.findOneByUserId(user.getId());
		}
		catch (NoResultException ex)
		{
			//create new because user has no auth params
			authParams = new CAuthenticationParams();
			authParams.setUser(user);
		}

		if (setValidityDate)
		{
			authParams.setValidFrom(validFrom);
			authParams.setValidTo(validTo);
		}

		if (encrypt)
		{
			authParams.setPasswordDigest(digester.generateDigest(login,password));
			authParams.setPassword(null);
		}
		else
		{
			authParams.setPassword(password);
			authParams.setPasswordDigest(null);
		}

		authenticationParamsDao.update(authParams);
	}
}
