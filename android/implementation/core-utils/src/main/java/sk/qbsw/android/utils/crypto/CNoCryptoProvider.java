package sk.qbsw.android.utils.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Implementation of encryption interface with no encryption
 * @author Tomas Lauro
 * @since 0.1.0
 * @version 0.1.0
 */
public class CNoCryptoProvider implements ICryptoProvider
{
	private static final long serialVersionUID = 3668769287050356067L;

	private final Integer bufferSize = 300;

	public void encrypt (InputStream inputStream, OutputStream outputStream) throws IOException
	{
		int length;
		byte[] buffer = new byte[bufferSize];

		//just copy to output stream
		while ( (length = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, length);
		}
	}

	public void decrypt (InputStream inputStream, OutputStream outputStream) throws IOException
	{
		int length;
		byte[] buffer = new byte[bufferSize];

		//just copy to input stream
		while ( (length = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, length);
		}
	}
}
