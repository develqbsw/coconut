package sk.qbsw.core.filesystem.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

import sk.qbsw.core.base.exception.CBusinessException;

/**
 * The Interface FileSystemService.
 *
 * @author farkas.roman
 * @version 2.3.0
 * @since 2.3.0
 */
public interface FileSystemService
{
	/**
	 * Save.
	 *
	 * @param destFileDirectoryPath the dest file directory path
	 * @param originalFileName      the original file name
	 * @param sourceIS              the source IS
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	String save (String destFileDirectoryPath, String originalFileName, InputStream sourceIS) throws IOException;

	/**
	 * Read as input stream.
	 *
	 * @param fileDirectoryPath the file directory path
	 * @param fileName          the file name
	 * @return the input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	InputStream readAsInputStream (String fileDirectoryPath, String fileName) throws IOException;

	/**
	 * Read as byte array.
	 *
	 * @param fileDirectoryPath the file directory path
	 * @param fileName          the file name
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	byte[] readAsByteArray (String fileDirectoryPath, String fileName) throws IOException;

	/**
	 * Gets the resource.
	 *
	 * @param fileDirectoryPath the file directory path
	 * @param fileName          the file name
	 * @return the resource
	 * @throws CBusinessException the c business exception
	 */
	Resource getResource (String fileDirectoryPath, String fileName) throws CBusinessException;
}
