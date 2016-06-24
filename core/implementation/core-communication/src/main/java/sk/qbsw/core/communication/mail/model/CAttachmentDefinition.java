package sk.qbsw.core.communication.mail.model;

import java.io.InputStream;
import java.io.Serializable;

/**
 * The mail attachment definition for interface.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
public class CAttachmentDefinition implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The file name to show in mail client. */
	private String fileName;

	/** The data stream. */
	private transient InputStream dataStream;

	/** The content type. */
	private String contentType;

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName ()
	{
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName (String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * Gets the data stream.
	 *
	 * @return the data stream
	 */
	public InputStream getDataStream ()
	{
		return dataStream;
	}

	/**
	 * Sets the data stream.
	 *
	 * @param dataStream the new data stream
	 */
	public void setDataStream (InputStream dataStream)
	{
		this.dataStream = dataStream;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType ()
	{
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType the new content type
	 */
	public void setContentType (String contentType)
	{
		this.contentType = contentType;
	}
}
