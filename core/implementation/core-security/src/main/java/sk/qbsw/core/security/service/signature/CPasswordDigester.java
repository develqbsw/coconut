package sk.qbsw.core.security.service.signature;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.logging.annotation.CAuditLogged;
import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator;

/**
 * Password digester implementation
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.11.8
 * @since 1.3.0
 * 
 */
@Service
public class CPasswordDigester extends AService implements IPasswordDigester
{
	/** The authentication configuration. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfiguration;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.signature.IPasswordDigester#generateDigest(java.lang.String)
	 */
	@CLogged (logResult = false)
	@CAuditLogged (actionName = "coconutPasswordDigesterGenerateAction", logResult = false)
	@Override
	public String generateDigest (@CNotLogged @CNotAuditLogged String password)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm(authenticationConfiguration.getDatabasePasswordHashMethod().getDatabaseAlgorithm());
		passwordEncryptor2.setPlainDigest(true);

		return passwordEncryptor2.encryptPassword(password);
	}

	@Override
	public boolean checkPassword(@CNotLogged @CNotAuditLogged String plainPassword, @CNotLogged @CNotAuditLogged String encryptedPassword)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm(authenticationConfiguration.getDatabasePasswordHashMethod().getDatabaseAlgorithm());
		passwordEncryptor2.setPlainDigest(true);

		return passwordEncryptor2.checkPassword(plainPassword, encryptedPassword);
	}
}
