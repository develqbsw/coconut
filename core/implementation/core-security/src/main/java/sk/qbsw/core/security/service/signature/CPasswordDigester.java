package sk.qbsw.core.security.service.signature;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.springframework.stereotype.Service;

/**
 * SHA-1 password digester implementation
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.3.0
 * 
 */
@Service
public class CPasswordDigester implements IPasswordDigester
{
	private final String DIGEST_ALGORITHM = "SHA-1";

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.signature.IPasswordDigester#generateDigest(java.lang.String)
	 */
	@Override
	public String generateDigest (String password)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm(DIGEST_ALGORITHM);
		passwordEncryptor2.setPlainDigest(true);
		return passwordEncryptor2.encryptPassword(password);
	}

	@Override
	public boolean checkPassword (String plainPassword, String encryptedPassword)
	{
		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm(DIGEST_ALGORITHM);
		passwordEncryptor2.setPlainDigest(true);

		return passwordEncryptor2.checkPassword(plainPassword, encryptedPassword);
	}
}
