package sk.qbsw.core.filesystem.repository;

import java.io.IOException;
import java.io.InputStream;

/**
 * The Interface FileSystemRepository.
 *
 * @author farkas.roman
 * @version 2.3.0
 * @since 2.3.0
 */
public interface FileSystemRepository
{
	/**
	 * Save.
	 *
	 * @param destFilePath the dest file path
	 * @param sourceIS the source IS
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void save (String destFilePath, InputStream sourceIS) throws IOException;

	/**
	 * Read as input stream.
	 *
	 * @param filePath the file path
	 * @return the input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	InputStream readAsInputStream (String filePath) throws IOException;

	/**
	 * Read.
	 *
	 * @param filePath the file path
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	byte[] readAsByteArray (String filePath) throws IOException;
}
