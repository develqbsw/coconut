package sk.qbsw.security.management.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.base.exception.PasswordFormatException;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.management.service.UserCredentialManagementService;

import javax.persistence.NoResultException;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Authentication service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.0.0
 */
@Service (value = "userCredentialManagementService")
public class UserCredentialManagementServiceImpl extends AService implements UserCredentialManagementService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserCredentialManagementServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private AuthenticationParamsDao authenticationParamsDao;

	@Autowired
	private PasswordDigester digester;

	@Autowired
	private IAuthenticationConfigurator authenticationConfigurator;

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		validatePassword(password);

		User user = findUser(login);
		AuthenticationParams authParams = findOrCreateAuthParams(user);

		setAuthParamsDigestedPassword(authParams, user.getLogin(), password);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword) throws CSecurityException
	{
		validatePassword(newPassword);

		User user = findUser(login);
		AuthenticationParams authParams = findOrCreateAuthParams(user);

		validateCurrentPassword(user.getLogin(), plainCurrentPassword, authParams);
		setAuthParamsDigestedPassword(authParams, user.getLogin(), newPassword);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePassword(password);

		User user = findUser(login);
		AuthenticationParams authParams = findOrCreateAuthParams(user);

		setAuthParamsDigestedPassword(authParams, user.getLogin(), password);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePassword(newPassword);

		User user = findUser(login);
		AuthenticationParams authParams = findOrCreateAuthParams(user);

		validateCurrentPassword(user.getLogin(), plainCurrentPassword, authParams);
		setAuthParamsDigestedPassword(authParams, user.getLogin(), newPassword);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		validatePassword(password);

		User user = findUser(login);
		AuthenticationParams authParams = findOrCreateAuthParams(user);

		validateMail(email, user.getEmail());
		setAuthParamsPlainPassword(authParams, password);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword) throws CSecurityException
	{
		validatePassword(newPassword);

		User user = findUser(login);
		AuthenticationParams authParams = findOrCreateAuthParams(user);

		validateMail(email, user.getEmail());
		validateCurrentPassword(login, plainCurrentPassword, authParams);
		setAuthParamsPlainPassword(authParams, newPassword);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePassword(password);

		User user = findUser(login);
		AuthenticationParams authParams = findOrCreateAuthParams(user);

		validateMail(email, user.getEmail());
		setAuthParamsPlainPassword(authParams, password);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePassword(newPassword);

		User user = findUser(login);
		AuthenticationParams authParams = findOrCreateAuthParams(user);

		validateMail(email, user.getEmail());
		validateCurrentPassword(login, plainCurrentPassword, authParams);
		setAuthParamsPlainPassword(authParams, newPassword);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeLogin (Long userId, String login)
	{
		User user = userDao.findById(userId);
		user.setLogin(login);

		userDao.update(user);
	}

	@Override
	public void validatePassword (@CNotLogged @CNotAuditLogged String password) throws PasswordFormatException
	{
		if (authenticationConfigurator.getPasswordPattern() != null)
		{
			Matcher matcher = Pattern.compile(authenticationConfigurator.getPasswordPattern()).matcher(password);

			if (!matcher.matches())
			{
				throw new PasswordFormatException("Incorrect password format");
			}
		}
	}

	private User findUser (String login) throws CSecurityException
	{
		try
		{
			return userDao.findOneByLogin(login);
		}
		catch (NoResultException ex)
		{
			LOGGER.error("Login not found", ex);
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_CHANGE_DENIED);
		}
	}

	private AuthenticationParams findOrCreateAuthParams (User user)
	{
		try
		{
			return authenticationParamsDao.findOneByUserId(user.getId());
		}
		catch (NoResultException ex)
		{
			// create new one
			AuthenticationParams authParams = new AuthenticationParams();
			authParams.setUser(user);

			return authParams;
		}
	}

	private void setAuthParamsDigestedPassword (AuthenticationParams authParams, String login, String password)
	{
		authParams.setPasswordDigest(digester.generateDigest(login, password));
		authParams.setPassword(null);
	}

	private void setAuthParamsPlainPassword (AuthenticationParams authParams, String password)
	{
		authParams.setPasswordDigest(null);
		authParams.setPassword(password);
	}

	private void setAuthParamsValidity (AuthenticationParams authParams, OffsetDateTime validFrom, OffsetDateTime validTo)
	{
		authParams.setValidFrom(validFrom != null ? validFrom : OffsetDateTime.now());
		authParams.setValidTo(validTo);
	}

	private void validateMail (String inputMail, String currentMail) throws CSecurityException
	{
		// checks email
		if (inputMail != null && currentMail != null && !inputMail.equals(currentMail))
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_CHANGE_DENIED);
		}
	}

	private void validateCurrentPassword (String login, @CNotLogged @CNotAuditLogged String plainCurrentPasswordInput, AuthenticationParams authParams) throws CSecurityException
	{
		if (authParams.getPassword() != null)
		{
			validatePlainCurrentPassword(plainCurrentPasswordInput, authParams.getPassword());
		}
		else if (authParams.getPasswordDigest() != null)
		{
			validateDigestedCurrentPassword(login, plainCurrentPasswordInput, authParams.getPasswordDigest());
		}
	}

	private void validateDigestedCurrentPassword (String login, @CNotLogged @CNotAuditLogged String plainCurrentPasswordInput, @CNotLogged @CNotAuditLogged String digestedCurrentPassword) throws CSecurityException
	{
		if (digestedCurrentPassword != null && !digester.checkPassword(login, plainCurrentPasswordInput, digestedCurrentPassword))
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_CHANGE_DENIED);
		}
	}

	private void validatePlainCurrentPassword (@CNotLogged @CNotAuditLogged String plainCurrentPasswordInput, @CNotLogged @CNotAuditLogged String plainCurrentPassword) throws CSecurityException
	{
		if (plainCurrentPassword != null && !plainCurrentPassword.equals(plainCurrentPasswordInput))
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_CHANGE_DENIED);
		}
	}
}
