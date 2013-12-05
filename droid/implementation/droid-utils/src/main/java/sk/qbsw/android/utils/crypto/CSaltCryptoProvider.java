package sk.qbsw.android.utils.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 * Implementation of encryption interface with specific encryption - add salt to begin of file
 * @author Tomas Lauro
 * @since 0.1.0
 * @version 0.1.0
 */
public class CSaltCryptoProvider implements ICryptoProvider
{
	private static final long serialVersionUID = 3668769287050356067L;

	private final Integer saltSize = 100;
	private final Integer bufferSize = 300;

	public void encrypt (InputStream inputStream, OutputStream outputStream) throws IOException
	{
		byte[] randomData = new byte[saltSize];
		Random random = new Random();
		random.nextBytes(randomData);

		int length;
		byte[] buffer = new byte[bufferSize];

		outputStream.write(randomData);

		while ( (length = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, length);
		}
	}

	public void decrypt (InputStream inputStream, OutputStream outputStream) throws IOException
	{
		int length;
		byte[] buffer = new byte[bufferSize];

		inputStream.skip(saltSize);

		while ( (length = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, length);
		}
	}
}
