package sk.qbsw.core.security.service;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CUserDisabledException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.EAuthenticationType;

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

	/**
	 * Authenticate by digest.
	 *
	 * @param user the user
	 * @param passwordToCheck the password to check
	 * @throws CWrongPasswordException the c wrong password exception
	 */
	private void authenticateByPassword (CUser user, String passwordToCheck) throws CWrongPasswordException
	{
		if (!user.getPassword().equals(passwordToCheck))
		{
			throw new CWrongPasswordException("Plain password doesn't match");
		}
	}


	/**
	 * Authenticate by password.
	 *
	 * @param user the user
	 * @param passwordToCheck the password to check
	 * @throws CWrongPasswordException the c wrong password exception
	 */
	private void authenticateByPasswordDigest (CUser user, String passwordToCheck) throws CWrongPasswordException
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm("SHA-1");
		passwordEncryptor2.setPlainDigest(true);

		if (!passwordEncryptor2.checkPassword(passwordToCheck, user.getPasswordDigest()))
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
					authenticateByPasswordDigest(user, password);
					break;
				case BY_PASSWORD:
					authenticateByPassword(user, password);
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
}
