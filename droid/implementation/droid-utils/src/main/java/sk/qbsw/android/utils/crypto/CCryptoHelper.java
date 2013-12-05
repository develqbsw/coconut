package sk.qbsw.android.utils.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.api.client.util.Base64;

/**
 * Provide methods to encryption/decryption of various sources 
 * @author Tomas Lauro
 *
 */
public class CCryptoHelper
{
	/**
	 * Encrypt text
	 * @param cryptoProvider provider for data encryption
	 * @param text non encrypted data
	 * @return encrypted data
	 * @throws IOException if the file can not be used
	 * @throws CCryptoException if the encryption algorithm fail
	 */
	public static String encryptText (ICryptoProvider cryptoProvider, String text) throws IOException, CCryptoException
	{
		if (text == null)
		{
			return null;
		}
		else
		{

			ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			try
			{
				cryptoProvider.encrypt(inputStream, outputStream);
				byte[] encryptedBytes = outputStream.toByteArray();

				byte[] encodedBytes = Base64.encode(encryptedBytes);

				return new String(encodedBytes);
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
	}

	/**
	 * Decrypt text
	 * @param cryptoProvider provider for data decryption
	 * @param text encrypted data
	 * @return non encrypted data
	 * @throws IOException if the file can not be used
	 * @throws CCryptoException if the encryption algorithm fail
	 */
	public static String decryptText (ICryptoProvider cryptoProvider, String text) throws IOException, CCryptoException
	{

		if (text == null)
		{
			return null;
		}
		else
		{
			ByteArrayInputStream inputStream = null;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			try
			{
				inputStream = new ByteArrayInputStream(Base64.decode(text.getBytes()));
				cryptoProvider.decrypt(inputStream, outputStream);

				return outputStream.toString();
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
	}
}
