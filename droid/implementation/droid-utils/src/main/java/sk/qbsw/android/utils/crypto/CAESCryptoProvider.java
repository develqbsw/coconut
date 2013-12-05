package sk.qbsw.android.utils.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import sk.qbsw.android.exception.CExceptionHandler;

/**
 * Implementation of encryption interface with AES encryption - 256 bit key
 * @author Tomas Lauro
 * @since 0.1.0
 * @version 0.1.0
 */
//algorithm source: http://pocket-for-android.1047292.n5.nabble.com/Encryption-method-and-reading-the-Dropbox-backup-tp4344194p4454327.html
public class CAESCryptoProvider implements ICryptoProvider
{
	private static final long serialVersionUID = 5720473473110407702L;
	
	private final Integer bufferSize = 50000;
	private final String PROVIDER = "BC";
	
	private final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	
	public void encrypt (InputStream inputStream, OutputStream outputStream) throws IOException, CCryptoException
	{
		CipherOutputStream cipherOutputStream = null;

		try
		{	
			CSecretKey secretKeyWrapper = new CSecretKey();
			
			SecretKey secretKey = secretKeyWrapper.load();

			Cipher encryptionCipher = Cipher.getInstance(CIPHER_ALGORITHM, PROVIDER);
			encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			
			cipherOutputStream = new CipherOutputStream(outputStream, encryptionCipher);
			
			// Write bytes
			int length;
			byte[] buffer = new byte[bufferSize];
			
			while ( (length = inputStream.read(buffer)) != -1)
			{
				cipherOutputStream.write(buffer, 0, length);
			}
		}
		catch (InvalidKeyException e)
		{
			CExceptionHandler.logException(CAESCryptoProvider.class, e);
			throw new CCryptoException("Encryption failed");
		}
		catch (NoSuchAlgorithmException e)
		{
			CExceptionHandler.logException(CAESCryptoProvider.class, e);
			throw new CCryptoException("Encryption failed");
		}
		catch (NoSuchPaddingException e)
		{
			CExceptionHandler.logException(CAESCryptoProvider.class, e);
			throw new CCryptoException("Encryption failed");
		}
		catch (NoSuchProviderException e)
		{
			CExceptionHandler.logException(CAESCryptoProvider.class, e);
			throw new CCryptoException("Encryption failed");
		}
		finally
		{
			// Flush and close streams.
			if (cipherOutputStream != null)
			{
				cipherOutputStream.flush();
				cipherOutputStream.close();
			}
		}
	}

	public void decrypt (InputStream inputStream, OutputStream outputStream) throws IOException, CCryptoException
	{
		CipherInputStream cipherInputStream = null;

		try
		{
			CSecretKey secretKeyWrapper = new CSecretKey();
			
			SecretKey secretKey = secretKeyWrapper.load();
			
			Cipher decryptionCipher = Cipher.getInstance(CIPHER_ALGORITHM, PROVIDER);
			decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey);

			cipherInputStream = new CipherInputStream(inputStream, decryptionCipher);
			
			int lenght;
			byte[] buffer = new byte[bufferSize];
			while ( (lenght = cipherInputStream.read(buffer)) != -1)
			{
				outputStream.write(buffer, 0, lenght);
			}
		}
		catch (InvalidKeyException e)
		{
			CExceptionHandler.logException(CAESCryptoProvider.class, e);
			throw new CCryptoException("Decryption failed");
		}
		catch (NoSuchAlgorithmException e)
		{
			CExceptionHandler.logException(CAESCryptoProvider.class, e);
			throw new CCryptoException("Decryption failed");
		}
		catch (NoSuchPaddingException e)
		{
			CExceptionHandler.logException(CAESCryptoProvider.class, e);
			throw new CCryptoException("Decryption failed");
		}
		catch (NoSuchProviderException e)
		{
			CExceptionHandler.logException(CAESCryptoProvider.class, e);
			throw new CCryptoException("Decryption failed");
		}
		finally
		{
			if (cipherInputStream != null)
			{
				cipherInputStream.close();
			}
		}
	}
}
