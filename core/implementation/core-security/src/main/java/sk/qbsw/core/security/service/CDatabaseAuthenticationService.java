package sk.qbsw.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @version 1.6.0
 * @since 1.0.0
 */
@Service (value = "cLoginService")
public class CDatabaseAuthenticationService implements IAuthenticationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

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

	/**
	 * Authenticate by digest.
	 *
	 * @param authenticationParams the authentication parameters of user
	 * @param passwordToCheck the password to check
	 * @throws CWrongPasswordException the c wrong password exception
	 */
	private void authenticateByPassword (CAuthenticationParams authenticationParams, String passwordToCheck) throws CWrongPasswordException
	{
		if (authenticationParams.getPassword().equals(passwordToCheck) == false)
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
		if (digester.checkPassword(passwordToCheck, authenticationParams.getPasswordDigest()) == false)
		{
			throw new CWrongPasswordException("Password dogest doesn't match");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
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
	 * @see sk.qbsw.winnetou.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser login (String login, String password) throws CSecurityException
	{
		return loginWithUnit(login, password, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Transactional (readOnly = true)
	public CUser login (String login, String password, CRole role) throws CSecurityException
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
	public CUser login (String login, String password, String unit) throws CSecurityException
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
		CUser user = userDao.findByLogin(login, unit);
		if (user == null)
		{
			throw new CWrongPasswordException("User not recognised");
		}
		else
		{
			EAuthenticationType authenticationType = user.authenticationType();

			switch (authenticationType)
			{
				case BY_PASSWORD_DIGEST:
					authenticateByPasswordDigest(user.getAuthenticationParams(), password);
					break;
				case BY_PASSWORD:
					authenticateByPassword(user.getAuthenticationParams(), password);
					break;
				default:
					throw new CWrongPasswordException("Authentication method wrong");
			}

			// check if user is disabled
			if (user.getFlagEnabled().equals(false))
			{
				throw new CUserDisabledException("");
			}
		}

		return user;
	}

	@Override
	@Transactional (readOnly = false)
	public CAuthenticationParams createPasswordDigest (String password)
	{
		CAuthenticationParams authParams = new CAuthenticationParams();
		authParams.setPassword(null);
		authParams.setPasswordDigest(digester.generateDigest(password));
		authParams.setPin(null);

		authenticationParamsDao.save(authParams);

		return authParams;
	}

	@Override
	@Transactional (readOnly = false)
	public void changePasswordDigest (String login, String password) throws CSecurityException
	{
		CUser user = userDao.findByLogin(login);

		if (user == null)
		{
			throw new CSecurityException("Password change not allowed", "error.security.changepassworddenied");
		}

		user.getAuthenticationParams().setPassword(null);
		user.getAuthenticationParams().setPasswordDigest(digester.generateDigest(password));
		authenticationParamsDao.save(user.getAuthenticationParams());
	}

	@Override
	@Transactional (readOnly = false)
	public void changePasswordPlain (String login, String email, String password) throws CSecurityException
	{
		CUser user = userDao.findByLogin(login);

		if (user == null || email.equals(user.getEmail()) == false)
		{
			throw new CSecurityException("Password change not allowed", "error.security.changepassworddenied");
		}

		user.getAuthenticationParams().setPassword(password);
		authenticationParamsDao.save(user.getAuthenticationParams());
	}
}
