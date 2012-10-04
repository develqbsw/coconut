package sk.qbsw;

import junit.framework.Assert;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
import org.junit.Test;

public class CJasyptTestCase
{

	@Test
	public void testPasswordOk ()
	{
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm("SHA-1");
		passwordEncryptor.setPlainDigest(true);
		String encryptedPassword = passwordEncryptor.encryptPassword("Test password");

		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm("SHA-1");
		passwordEncryptor2.setPlainDigest(true);

		if (!passwordEncryptor2.checkPassword("Test password", encryptedPassword))
		{
			// wrong password
			Assert.fail("Password should be ok");
		}
	}

	@Test
	public void testPasswordWrong ()
	{
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm("SHA-1");
		passwordEncryptor.setPlainDigest(true);
		String encryptedPassword = passwordEncryptor.encryptPassword("Test password");

		ConfigurablePasswordEncryptor passwordEncryptor2 = new ConfigurablePasswordEncryptor();
		passwordEncryptor2.setAlgorithm("SHA-1");
		passwordEncryptor2.setPlainDigest(true);

		if (passwordEncryptor2.checkPassword("Test password2", encryptedPassword))
		{
			// wrong password
			Assert.fail("Password should be wrong");
		}
	}

}
