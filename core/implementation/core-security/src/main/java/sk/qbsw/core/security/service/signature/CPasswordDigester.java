package sk.qbsw.core.security.service.signature;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator;

/**
 * Password digester implementation
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.8.0
 * @since 1.3.0
 * 
 */
@Service
public class CPasswordDigester implements IPasswordDigester
{
	/** The authentication configuration. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfiguration;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.signature.IPasswordDigester#generateDigest(java.lang.String)
	 */
	@Override
	public String generateDigest (String password)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm(authenticationConfiguration.getDatabasePasswordHashMethod().getDatabaseAlgorithm());
		passwordEncryptor2.setPlainDigest(true);

		return passwordEncryptor2.encryptPassword(password);
	}

	@Override
	public boolean checkPassword (String plainPassword, String encryptedPassword)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm(authenticationConfiguration.getDatabasePasswordHashMethod().getDatabaseAlgorithm());
		passwordEncryptor2.setPlainDigest(true);

		return passwordEncryptor2.checkPassword(plainPassword, encryptedPassword);
	}
}
