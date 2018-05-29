package sk.qbsw.security.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.base.exception.PasswordFormatException;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.service.signature.PasswordDigester;

import javax.persistence.NoResultException;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The account credential management service.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public class AccountCredentialManagementServiceImpl extends AService implements AccountCredentialManagementService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountCredentialManagementServiceImpl.class);

	private final AccountDao accountDao;

	private final AuthenticationParamsDao authenticationParamsDao;

	private final PasswordDigester digester;

	private final SecurityCoreConfigurator securityCoreConfigurator;

	/**
	 * Instantiates a new Account credential management service.
	 *
	 * @param accountDao the account dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param digester the digester
	 * @param securityCoreConfigurator the security core configurator
	 */
	@Autowired
	public AccountCredentialManagementServiceImpl (AccountDao accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		this.accountDao = accountDao;
		this.authenticationParamsDao = authenticationParamsDao;
		this.digester = digester;
		this.securityCoreConfigurator = securityCoreConfigurator;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		validatePassword(password);

		Account account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		setAuthParamsDigestedPassword(authParams, account.getLogin(), password);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword) throws CSecurityException
	{
		validatePassword(newPassword);

		Account account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateCurrentPassword(account.getLogin(), plainCurrentPassword, authParams);
		setAuthParamsDigestedPassword(authParams, account.getLogin(), newPassword);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePassword(password);

		Account account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		setAuthParamsDigestedPassword(authParams, account.getLogin(), password);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePassword(newPassword);

		Account account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateCurrentPassword(account.getLogin(), plainCurrentPassword, authParams);
		setAuthParamsDigestedPassword(authParams, account.getLogin(), newPassword);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		validatePassword(password);

		Account account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateMail(email, account.getEmail());
		setAuthParamsPlainPassword(authParams, password);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword) throws CSecurityException
	{
		validatePassword(newPassword);

		Account account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateMail(email, account.getEmail());
		validateCurrentPassword(login, plainCurrentPassword, authParams);
		setAuthParamsPlainPassword(authParams, newPassword);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePassword(password);

		Account account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateMail(email, account.getEmail());
		setAuthParamsPlainPassword(authParams, password);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePassword(newPassword);

		Account account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateMail(email, account.getEmail());
		validateCurrentPassword(login, plainCurrentPassword, authParams);
		setAuthParamsPlainPassword(authParams, newPassword);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeLogin (Long accountId, String login)
	{
		Account account = accountDao.findById(accountId);
		account.setLogin(login);

		accountDao.update(account);
	}

	@Override
	public void validatePassword (@CNotLogged @CNotAuditLogged String password) throws PasswordFormatException
	{
		if (securityCoreConfigurator.getPasswordPattern() != null)
		{
			Matcher matcher = Pattern.compile(securityCoreConfigurator.getPasswordPattern()).matcher(password);

			if (!matcher.matches())
			{
				throw new PasswordFormatException("Incorrect password format");
			}
		}
	}

	private Account findAccount (String login) throws CSecurityException
	{
		try
		{
			return accountDao.findOneByLogin(login);
		}
		catch (NoResultException ex)
		{
			LOGGER.error("Login not found", ex);
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_CHANGE_DENIED);
		}
	}

	private AuthenticationParams findOrCreateAuthParams (Account account)
	{
		try
		{
			return authenticationParamsDao.findOneByAccountId(account.getId());
		}
		catch (NoResultException ex)
		{
			// create new one
			AuthenticationParams authParams = new AuthenticationParams();
			authParams.setAccount(account);

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
