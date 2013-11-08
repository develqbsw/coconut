package sk.qbsw.core.security.service;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CUserDisabledException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.EAuthenticationType;

/**
 * Authentication service
 * 
 * @author Dalibor Rak
 * @version 1.2.0
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
		CUser user = userDao.findByLogin(login);
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Transactional (readOnly = true)
	public CUser login (String login, String password, CRole role) throws CSecurityException
	{
		CUser user = login(login, password);
		if (!user.hasRole(role))
		{
			throw new CSecurityException("User has no such role");
		}
		return user;
	}
}
