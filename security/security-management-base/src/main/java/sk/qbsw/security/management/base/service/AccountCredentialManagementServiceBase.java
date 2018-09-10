package sk.qbsw.security.management.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.base.exception.PasswordFormatException;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.service.signature.PasswordDigester;

import javax.persistence.NoResultException;
import java.time.OffsetDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The account credential management service base.
 *
 * @param <A> the type parameter
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public abstract class AccountCredentialManagementServiceBase<A extends Account>extends AService
{
	/**
	 * The constant LOGGER.
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(AccountCredentialManagementServiceBase.class);

	/**
	 * The Account dao.
	 */
	protected final AccountDao<A> accountDao;

	/**
	 * The Authentication params dao.
	 */
	protected final AuthenticationParamsDao authenticationParamsDao;

	/**
	 * The Digester.
	 */
	protected final PasswordDigester digester;

	/**
	 * The Security core configurator.
	 */
	protected final SecurityCoreConfigurator securityCoreConfigurator;

	/**
	 * Instantiates a new Account credential management service base.
	 *
	 * @param accountDao the account dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param digester the digester
	 * @param securityCoreConfigurator the security core configurator
	 */
	public AccountCredentialManagementServiceBase (AccountDao<A> accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		this.accountDao = accountDao;
		this.authenticationParamsDao = authenticationParamsDao;
		this.digester = digester;
		this.securityCoreConfigurator = securityCoreConfigurator;
	}

	/**
	 * Change encrypted password base.
	 *
	 * @param login the login
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 */
	protected void changeEncryptedPasswordBase (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		validatePasswordBase(password);

		A account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		setAuthParamsDigestedPassword(authParams, account.getLogin(), password);

		authenticationParamsDao.update(authParams);
	}

	/**
	 * Change encrypted password base.
	 *
	 * @param login the login
	 * @param plainCurrentPassword the plain current password
	 * @param newPassword the new password
	 * @throws CSecurityException the c security exception
	 */
	protected void changeEncryptedPasswordBase (String login, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword) throws CSecurityException
	{
		validatePasswordBase(newPassword);

		A account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateCurrentPassword(account.getLogin(), plainCurrentPassword, authParams);
		setAuthParamsDigestedPassword(authParams, account.getLogin(), newPassword);

		authenticationParamsDao.update(authParams);
	}

	/**
	 * Change encrypted password base.
	 *
	 * @param login the login
	 * @param password the password
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @throws CSecurityException the c security exception
	 */
	protected void changeEncryptedPasswordBase (String login, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePasswordBase(password);

		A account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		setAuthParamsDigestedPassword(authParams, account.getLogin(), password);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	/**
	 * Change encrypted password base.
	 *
	 * @param login the login
	 * @param plainCurrentPassword the plain current password
	 * @param newPassword the new password
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @throws CSecurityException the c security exception
	 */
	protected void changeEncryptedPasswordBase (String login, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePasswordBase(newPassword);

		A account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateCurrentPassword(account.getLogin(), plainCurrentPassword, authParams);
		setAuthParamsDigestedPassword(authParams, account.getLogin(), newPassword);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	/**
	 * Change plain password base.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 */
	protected void changePlainPasswordBase (String login, String email, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		validatePasswordBase(password);

		A account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateMail(email, account.getEmail());
		setAuthParamsPlainPassword(authParams, password);

		authenticationParamsDao.update(authParams);
	}

	/**
	 * Change plain password base.
	 *
	 * @param login the login
	 * @param email the email
	 * @param plainCurrentPassword the plain current password
	 * @param newPassword the new password
	 * @throws CSecurityException the c security exception
	 */
	protected void changePlainPasswordBase (String login, String email, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword) throws CSecurityException
	{
		validatePasswordBase(newPassword);

		A account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateMail(email, account.getEmail());
		validateCurrentPassword(login, plainCurrentPassword, authParams);
		setAuthParamsPlainPassword(authParams, newPassword);

		authenticationParamsDao.update(authParams);
	}

	/**
	 * Change plain password base.
	 *
	 * @param login the login
	 * @param email the email
	 * @param password the password
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @throws CSecurityException the c security exception
	 */
	protected void changePlainPasswordBase (String login, String email, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePasswordBase(password);

		A account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateMail(email, account.getEmail());
		setAuthParamsPlainPassword(authParams, password);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	/**
	 * Change plain password base.
	 *
	 * @param login the login
	 * @param email the email
	 * @param plainCurrentPassword the plain current password
	 * @param newPassword the new password
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @throws CSecurityException the c security exception
	 */
	protected void changePlainPasswordBase (String login, String email, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		validatePasswordBase(newPassword);

		A account = findAccount(login);
		AuthenticationParams authParams = findOrCreateAuthParams(account);

		validateMail(email, account.getEmail());
		validateCurrentPassword(login, plainCurrentPassword, authParams);
		setAuthParamsPlainPassword(authParams, newPassword);
		setAuthParamsValidity(authParams, validFrom, validTo);

		authenticationParamsDao.update(authParams);
	}

	/**
	 * Change login base.
	 *
	 * @param accountId the account id
	 * @param login the login
	 */
	protected void changeLoginBase (Long accountId, String login)
	{
		A account = accountDao.findById(accountId);
		account.setLogin(login);

		accountDao.update(account);
	}

	/**
	 * Validate password base.
	 *
	 * @param password the password
	 * @throws PasswordFormatException the password format exception
	 */
	protected void validatePasswordBase (@CNotLogged @CNotAuditLogged String password) throws PasswordFormatException
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

	private A findAccount (String login) throws CSecurityException
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

	private AuthenticationParams findOrCreateAuthParams (A account)
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
