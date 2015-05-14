package sk.qbsw.android.utils.crypto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sk.qbsw.android.exception.CExceptionHandler;

import com.google.api.client.util.Base64;

/**
 * Handle secret key for encryption algorithm
 * @author Tomas Lauro
 * @since 0.1.0
 * @version 0.1.0
 * 
 * TODO: refactor
 */
//algorithm source: http://pocket-for-android.1047292.n5.nabble.com/Encryption-method-and-reading-the-Dropbox-backup-tp4344194p4454327.html
public class CSecretKey
{
	private final String KEY_FILE_NAME = "/data/data/sk.qbsw.asp.mobhliadka/key";

	private final String PBE_ALGORITHM = "PBEWithSHA256And256BitAES-CBC-BC";
	private final String SECRET_KEY_ALGORITHM = "AES";
	private final String RANDOM_ALGORITHM = "SHA1PRNG";

	private final String PROVIDER = "BC";
	private final int PBE_ITERATION_COUNT = 100;
	private final int SALT_LENGTH = 20;
	private final int PASSWORD_LENGTH = 10;
	

	/**
	 * Create file with generated secret key inside
	 * @throws IOException if the file can not be opened
	 * @throws CCryptoException if there is some error in encryption algorithm
	 */
	public void create () throws IOException, CCryptoException
	{
		FileOutputStream outputStream = null;

		try
		{
			outputStream = new FileOutputStream(new File(KEY_FILE_NAME), false);

			SecretKey secretKey = generateKey(generatePassword(), generateSalt());
			byte[] encodedSecretKey = Base64.encode(secretKey.getEncoded());

			outputStream.write(encodedSecretKey);
		}
		catch (NoSuchAlgorithmException e)
		{
			CExceptionHandler.logException(CSecretKey.class, e);
			throw new CCryptoException("Secret key can not be created", e);
		}
		catch (NoSuchProviderException e)
		{
			CExceptionHandler.logException(CSecretKey.class, e);
			throw new CCryptoException("Secret key can not be created", e);
		}
		catch (InvalidKeySpecException e)
		{
			CExceptionHandler.logException(CSecretKey.class, e);
			throw new CCryptoException("Secret key can not be created", e);
		}
		finally
		{
			if (outputStream != null)
			{
				outputStream.close();
			}
		}
	}

	public SecretKey load () throws IOException
	{
		FileInputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;

		try
		{
			inputStream = new FileInputStream(new File(KEY_FILE_NAME));
			outputStream = new ByteArrayOutputStream();

			int length;
			byte[] buffer = new byte[20];

			while ( (length = inputStream.read(buffer)) != -1)
			{
				outputStream.write(buffer, 0, length);
			}

			SecretKey secretKey = new SecretKeySpec(Base64.decode(outputStream.toByteArray()), SECRET_KEY_ALGORITHM);

			return secretKey;
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
			if (outputStream != null)
			{
				outputStream.close();
			}
		}
	}

	public Boolean exist () throws IOException
	{
		FileInputStream inputStream = null;

		try
		{
			inputStream = new FileInputStream(new File(KEY_FILE_NAME));

			return true;
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
		}
	}

	private SecretKey generateKey (String password, byte[] salt) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException
	{
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, PBE_ITERATION_COUNT, 256);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(PBE_ALGORITHM, PROVIDER);
		SecretKey tmp = factory.generateSecret(pbeKeySpec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), SECRET_KEY_ALGORITHM);
		return secret;
	}

	private byte[] generateSalt () throws NoSuchAlgorithmException
	{
		SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITHM);
		byte[] salt = new byte[SALT_LENGTH];
		random.nextBytes(salt);
		return salt;
	}
	
	private String generatePassword ()
	{
		String generatedPassword = "";
		Random random = new Random();
		for (int i = 0; i < PASSWORD_LENGTH; i++)
		{
			//generate visible characters from ascii table - last 95 character except last DEL
			char randomCharacter = (char) (random.nextInt(94) + 32);
			generatedPassword += randomCharacter;
		}
		return generatedPassword;
	}
}
