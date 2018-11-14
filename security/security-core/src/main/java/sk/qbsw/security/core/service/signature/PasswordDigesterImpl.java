package sk.qbsw.security.core.service.signature;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.logging.annotation.CAuditLogged;
import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.configuration.model.AdditionalAuthenticationParamsTypes;
import sk.qbsw.security.core.configuration.model.AuthenticationSchemas;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;

/**
 * Password digester implementation
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author Marek Martinkovic
 * @version 2.0.0
 * @since 1.3.0
 */
public class PasswordDigesterImpl extends AService implements PasswordDigester
{
	private final SecurityCoreConfigurator securityCoreConfigurator;

	public PasswordDigesterImpl (SecurityCoreConfigurator securityCoreConfigurator)
	{
		this.securityCoreConfigurator = securityCoreConfigurator;
	}


	@CLogged (logResult = false)
	@CAuditLogged (actionName = "coconutPasswordDigesterGenerateAction", logResult = false)
	@Override
	public String generateDigest (@CNotLogged @CNotAuditLogged String password)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm(securityCoreConfigurator.getPasswordHashMethod().getDatabaseAlgorithm());
		passwordEncryptor2.setPlainDigest(true);

		return passwordEncryptor2.encryptPassword(password);
	}

	@Override
	public boolean checkPassword (@CNotLogged @CNotAuditLogged String plainPassword, @CNotLogged @CNotAuditLogged String encryptedPassword)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm(securityCoreConfigurator.getPasswordHashMethod().getDatabaseAlgorithm());
		passwordEncryptor2.setPlainDigest(true);

		return passwordEncryptor2.checkPassword(plainPassword, encryptedPassword);
	}

	@CLogged (logResult = false)
	@CAuditLogged (actionName = "coconutPasswordDigesterGenerateAction", logResult = false)
	@Override
	public String generateDigest (@CNotLogged @CNotAuditLogged String login, @CNotLogged @CNotAuditLogged String password)
	{
		if (!AuthenticationSchemas.CUSTOM.equals(securityCoreConfigurator.getAuthenticationSchema()))
		{
			// new auth
			ConfigurablePasswordEncryptor encryptor = new ConfigurablePasswordEncryptor();

			encryptor.setStringOutputType("hexadecimal");
			encryptor.setPlainDigest(true);
			encryptor.setAlgorithm(securityCoreConfigurator.getPasswordHashMethod().getDatabaseAlgorithm());

			String realm = getRealm();
			String encodedPasswd = login + ":" + realm + ":" + password;
			String encryptPassword = encryptor.encryptPassword(encodedPasswd);

			if (toLowerCase())
			{
				encryptPassword = encryptPassword.toLowerCase();
			}

			return encryptPassword;
		}
		else
		{
			// legacy code
			return generateDigest(password);
		}
	}

	@Override
	public boolean checkPassword (@CNotLogged @CNotAuditLogged String plainLogin, @CNotLogged @CNotAuditLogged String plainPassword, @CNotLogged @CNotAuditLogged String encryptedPassword)
	{
		if (!AuthenticationSchemas.CUSTOM.equals(securityCoreConfigurator.getAuthenticationSchema()))
		{
			ConfigurablePasswordEncryptor encryptor = new ConfigurablePasswordEncryptor();

			encryptor.setStringOutputType("hexadecimal");
			encryptor.setPlainDigest(true);
			encryptor.setAlgorithm(securityCoreConfigurator.getPasswordHashMethod().getDatabaseAlgorithm());

			String realm = getRealm();
			String encodedPassword = plainLogin + ":" + realm + ":" + plainPassword;

			return encryptor.checkPassword(encodedPassword, encryptedPassword);
		}
		else
		{
			// legacy code
			return checkPassword(plainPassword, encryptedPassword);
		}
	}

	private String getRealm ()
	{
		String realm = securityCoreConfigurator.getAdditionalAuthenticationParams().get(AdditionalAuthenticationParamsTypes.REALM);

		if (realm == null)
		{
			// if no realm
			throw new CSystemException("REALM is required for this auth schema");
		}

		return realm;
	}

	private boolean toLowerCase ()
	{
		String realm = securityCoreConfigurator.getAdditionalAuthenticationParams().get(AdditionalAuthenticationParamsTypes.HASH_TO_LOWERCASE);

		return realm != null;
	}
}
