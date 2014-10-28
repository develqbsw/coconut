package sk.qbsw.core.security.service;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.CService;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CUserDisabledException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
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
 * @version 1.11.8
 * @since 1.0.0
 */
@Service (value = "cLoginService")
public class CDatabaseAuthenticationService extends CService implements IAuthenticationService
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
	 * @throws CWrongPasswordException the c wrong password exception
	 */
	private void authenticateByPassword (CAuthenticationParams authenticationParams, String passwordToCheck) throws CWrongPasswordException
	{
		if (authenticationParams.getPassword() == null || authenticationParams.getPassword().equals(passwordToCheck) == false)
		{
			throw new CWrongPasswordException("Plain password doesn't match");
		}
	}

	/**
	 * Authenticate by password.
	 *
	 * @param authenticationParams the authentication parameters of user
	 * @param passwordToCheck the password to check
	 * @throws CWrongPasswordException the c wrong password exception
	 */
	private void authenticateByPasswordDigest (CAuthenticationParams authenticationParams, String passwordToCheck) throws CWrongPasswordException
	{
		if (authenticationParams.getPasswordDigest() == null || digester.checkPassword(passwordToCheck, authenticationParams.getPasswordDigest()) == false)
		{
			throw new CWrongPasswordException("Password dogest doesn't match");
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

		if (user.hasRole(role) == false)
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
		CUnit localUnit = unitDao.findByName(unit);

		if (localUnit == null)
		{
			throw new CSecurityException("There is not a unit with name " + unit);
		}
		else
		{
			CUser user = loginWithUnit(login, password, localUnit);

			if (user.isInUnit(localUnit) == true)
			{
				return user;
			}
			else
			{
				throw new CSecurityException("User is not is unit with name " + localUnit.getName());
			}
		}
	}


	/**
	 * Login with unit.
	 *
	 * @param login the login
	 * @param password the password
	 * @param unit the unit - the unit is optional parameter
	 * @return the user
	 * @throws CSecurityException the security exception
	 */
	@Transactional (readOnly = true)
	private CUser loginWithUnit (String login, String password, CUnit unit) throws CSecurityException
	{
		LOGGER.debug("trying to login user with login {} and unit{} ", new Object[]{login, unit});
		
		CUser user;

		try
		{
			user = userDao.findByLogin(login, unit);
		}
		catch (NoResultException nre)
		{
			user = null;
		}

		if (user == null)
		{
			throw new CWrongPasswordException("User not recognised");
		}
		else
		{
			EAuthenticationType authenticationType = user.authenticationType();
			CAuthenticationParams userAuthParams = authenticationParamsDao.findByUserId(user.getId());

			switch (authenticationType)
			{
				case BY_PASSWORD_DIGEST:
					authenticateByPasswordDigest(userAuthParams, password);
					break;
				case BY_PASSWORD:
					authenticateByPassword(userAuthParams, password);
					break;
				default:
					throw new CWrongPasswordException("Authentication method wrong");
			}

			// check if user is disabled
			if ( (user.getOrganization().getFlagEnabled() != null && user.getOrganization().getFlagEnabled().equals(false)) || (user.getFlagEnabled() != null && user.getFlagEnabled().equals(false)))
			{
				throw new CUserDisabledException("");
			}
		}
		
		LOGGER.debug("user with login {} and unit{} found. ", new Object[]{login, unit});

		return user;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeEncryptedPassword(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		changePassword(login, password, null, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changePlainPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		changePassword(login, password, email, true);
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

		userDao.save(user);
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
			userDao.countAllUsers();
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
	 * @throws CSecurityException the c security exception
	 */
	private void changePassword (String login, String password, String email, boolean encrypt) throws CSecurityException
	{
		CUser user = null;

		//validate password, if not valid throw an exception
		authDataValidationService.validatePassword(password);

		try
		{
			user = userDao.findByLogin(login);
		}
		catch (NoResultException ex)
		{
			throw new CSecurityException("Password change not allowed", "error.security.changepassworddenied");
		}

		//checks email if enctypt flag is false
		if (encrypt == false && email != null && user.getEmail() != null && email.equals(user.getEmail()) == false)
		{
			throw new CSecurityException("Password change not allowed", "error.security.changepassworddenied");
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
		}

		if (encrypt == true)
		{
			authParams.setPasswordDigest(digester.generateDigest(password));
			authParams.setPassword(null);
		}
		else
		{
			authParams.setPassword(password);
			authParams.setPasswordDigest(null);
		}
		authenticationParamsDao.save(authParams);
	}
}
