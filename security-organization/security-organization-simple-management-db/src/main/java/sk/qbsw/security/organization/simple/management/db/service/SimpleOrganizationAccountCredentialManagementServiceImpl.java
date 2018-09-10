package sk.qbsw.security.organization.simple.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.security.base.exception.PasswordFormatException;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.management.base.service.AccountCredentialManagementServiceBase;
import sk.qbsw.security.management.service.AccountCredentialManagementService;

import java.time.OffsetDateTime;

/**
 * The simple Organization account credential management service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SimpleOrganizationAccountCredentialManagementServiceImpl extends AccountCredentialManagementServiceBase<Account> implements AccountCredentialManagementService
{
	/**
	 * Instantiates a new Simple organization account credential management service.
	 *
	 * @param accountDao the account dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param digester the digester
	 * @param securityCoreConfigurator the security core configurator
	 */
	public SimpleOrganizationAccountCredentialManagementServiceImpl (AccountDao<Account> accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		super(accountDao, authenticationParamsDao, digester, securityCoreConfigurator);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		super.changeEncryptedPasswordBase(login, password);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword) throws CSecurityException
	{
		super.changeEncryptedPasswordBase(login, plainCurrentPassword, newPassword);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		super.changeEncryptedPasswordBase(login, password, validFrom, validTo);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		super.changeEncryptedPasswordBase(login, plainCurrentPassword, newPassword, validFrom, validTo);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		super.changePlainPasswordBase(login, email, password);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword) throws CSecurityException
	{
		super.changePlainPasswordBase(login, email, plainCurrentPassword, newPassword);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		super.changePlainPasswordBase(login, email, password, validFrom, validTo);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String plainCurrentPassword, @CNotLogged @CNotAuditLogged String newPassword, OffsetDateTime validFrom, OffsetDateTime validTo) throws CSecurityException
	{
		super.changePlainPasswordBase(login, email, plainCurrentPassword, newPassword, validFrom, validTo);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void changeLogin (Long accountId, String login)
	{
		super.changeLoginBase(accountId, login);
	}

	@Override
	public void validatePassword (@CNotLogged @CNotAuditLogged String password) throws PasswordFormatException
	{
		super.validatePasswordBase(password);
	}
}
