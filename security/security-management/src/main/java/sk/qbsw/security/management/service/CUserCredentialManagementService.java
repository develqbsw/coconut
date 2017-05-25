package sk.qbsw.security.management.service;

import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;

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
import sk.qbsw.core.security.base.exception.CPasswordFormatException;
import sk.qbsw.security.dao.IAuthenticationParamsDao;
import sk.qbsw.security.dao.IUserDao;
import sk.qbsw.security.model.domain.CAuthenticationParams;
import sk.qbsw.security.model.domain.CUser;
import sk.qbsw.security.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.security.service.signature.IPasswordDigester;

/**
 * Authentication service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.18.0
 * @since 1.0.0
 */
@Service (value = "userCredentialManagementService")
public class CUserCredentialManagementService extends AService implements IUserCredentialManagementService
{

	/** LOGGER for authentication messages logging. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CUserCredentialManagementService.class);

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The authentication params dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

	/** Password digester *. */
	@Autowired
	private IPasswordDigester digester;

	/** The Authentication Configurator. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfigurator;



	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.IAuthenticationService#changeEncryptedPassword(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		changePassword(login, password, null, null, null, true, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.IUserCredentialManagementService#changeEncryptedPassword(java.lang.String, java.lang.String, java.time.OffsetDateTime, java.time.OffsetDateTime)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		changePassword(login, password, null, validFrom, validTo, true, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.IAuthenticationService#changePlainPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		changePassword(login, password, email, null, null, true, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.IUserCredentialManagementService#changePlainPassword(java.lang.String, java.lang.String, java.lang.String, java.time.OffsetDateTime, java.time.OffsetDateTime)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		changePassword(login, password, email, validFrom, validTo, true, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.IUserCredentialManagementService#changeLogin(java.lang.Long, java.lang.String)
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
	 * @see sk.qbsw.security.management.service.IUserCredentialManagementService#validatePassword(java.lang.String)
	 */
	@Override
	public void validatePassword (@CNotLogged @CNotAuditLogged String password) throws CPasswordFormatException
	{
		if (authenticationConfigurator.getPasswordPattern() != null)
		{
			Matcher matcher = Pattern.compile(authenticationConfigurator.getPasswordPattern()).matcher(password);

			if (matcher.matches() == false)
			{
				throw new CPasswordFormatException("Incorrect password format");
			}
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
	private void changePassword (String login, String password, String email, OffsetDateTime validFrom, OffsetDateTime validTo, boolean encrypt, boolean setValidityDate) throws CSecurityException
	{
		CUser user = null;

		//validate password, if not valid throw an exception
		validatePassword(password);

		try
		{
			user = userDao.findOneByLogin(login);
		}
		catch (NoResultException ex)
		{
			LOGGER.error("Login not found", ex);
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_CHANGE_DENIED);
		}

		//checks email if enctypt flag is false
		if (!encrypt && email != null && user.getEmail() != null && !email.equals(user.getEmail()))
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
			LOGGER.error("Authentication params not found", ex);

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
			authParams.setPasswordDigest(digester.generateDigest(login, password));
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
