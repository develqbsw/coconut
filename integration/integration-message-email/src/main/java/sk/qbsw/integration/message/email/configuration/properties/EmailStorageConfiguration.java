package sk.qbsw.integration.message.email.configuration.properties;

import java.io.Serializable;

/**
 * The email storage configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface EmailStorageConfiguration extends Serializable
{
	/**
	 * Gets the root path.
	 *
	 * @return the root path
	 */
	String getRootPath ();

	/**
	 * Gets the attachments path.
	 *
	 * @return the attachments path
	 */
	String getAttachmentsPath ();

	/**
	 * Gets the complete attachments file system path.
	 *
	 * @return the complete attachments file system path
	 */
	String getCompleteAttachmentsFileSystemPath ();
}
