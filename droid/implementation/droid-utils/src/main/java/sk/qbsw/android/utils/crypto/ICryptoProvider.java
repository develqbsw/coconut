package sk.qbsw.android.utils.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Interface to provide encryption
 * @author Tomas Lauro
 * @since 0.1.0
 * @version 0.1.0
 */
public interface ICryptoProvider extends Serializable
{
	/**
	 * Encrypt file from source path and save it to destination path using secret key and algorithm
	 * @param inputStream input stream
	 * @param outputStream output stream
	 * @throws IOException if the file can not be opened
	 * @throws CCryptoException if there is some error in encryption algorithm
	 */
	public abstract void encrypt (InputStream inputStream, OutputStream outputStream) throws IOException, CCryptoException;

	/**
	 * Decrypt file from source path and save it to byte array using secret key and algorithm
	 * @param inputStream input stream
	 * @param outputStream output stream
	 * @throws IOException if the file can not be opened
	 * @throws CCryptoException if there is some error in encryption algorithm
	 */
	public abstract void decrypt (InputStream inputStream, OutputStream outputStream) throws IOException, CCryptoException;
}
