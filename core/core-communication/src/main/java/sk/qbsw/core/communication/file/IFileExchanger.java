package sk.qbsw.core.communication.file;

import java.io.InputStream;
import java.util.List;

/**
 * The Interface IFileExchanger.
 * 
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
public interface IFileExchanger
{

	/**
	 * Connects to server.
	 */
	public void connect ();

	/**
	 * Disconnects from the server.
	 */
	public void disconnect ();

	/**
	 * Ls.
	 *
	 * @param path the path where to execute ls command
	 * @param fileType the file type
	 * @return the list
	 */
	public List<String> ls (String path, EFileType fileType);

	/**
	 * Gets the file from the path.
	 *
	 * @param path the path
	 * @return the input stream - red stream
	 */
	public InputStream get (String path);

	/**
	 * Uploads stream into file
	 *
	 * @param dstPath the dst path
	 * @param fileToTransfer the file to transfer
	 */
	public void put (String dstPath, InputStream fileToTransfer);

	/**
	 * Removes the file.
	 *
	 * @param path the path
	 */
	public void remove (String path);

	/**
	 * Renames the path to another.
	 *
	 * @param srcPath the source path
	 * @param destPat the destination pat
	 */
	public void rename (String srcPath, String destPat);
}
