/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.indy.base.behaviour;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;


/**
 * The Class CAJAXPreparedDownload.
 *
 * @author Dalibor Rak
 * @version 1.3
 * @since 1.3
 */
@SuppressWarnings ("serial")
public abstract class CAJAXPreparedDownload extends AAJAXDownload implements Serializable
{

	/** The type. */
	private String type;

	/** The file name. */
	private String fileName;

	/** The is. */
	private transient InputStream is;

	/**
	 * Instantiates a new cAJAX prepared download.
	 *
	 * @param type the type
	 * @param fileName the file name
	 */
	public CAJAXPreparedDownload (String type, String fileName)
	{
		this.type = type;
		this.fileName = fileName;
	}

	/**
	 * Initiate.
	 *
	 * @param target the target
	 * @see sk.qbsw.indy.base.behaviour.eprieskum.web.behaviour.AAJAXDownload#initiate(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	public void initiate (AjaxRequestTarget target)
	{
		try
		{
			is = getDownloadStream();
		}
		catch (ResourceStreamNotFoundException snf)
		{
			is = null;
		}
		super.initiate(target);
	}

	/**
	 * Gets the download stream.
	 *
	 * @return the download stream
	 * @throws ResourceStreamNotFoundException the resource stream not found exception
	 */
	public abstract InputStream getDownloadStream () throws ResourceStreamNotFoundException;



	/**
	 * Gets the resource stream.
	 *
	 * @return the resource stream
	 * @see sk.qbsw.indy.base.behaviour.eprieskum.web.behaviour.AAJAXDownload#getResourceStream()
	 */
	@Override
	protected IResourceStream getResourceStream ()
	{
		return new AbstractResourceStream()
		{

			public InputStream getInputStream () throws ResourceStreamNotFoundException
			{
				return is;
			}

			public void close () throws IOException
			{
				if (is != null)
				{
					is.close();
				}
			}

			@Override
			public String getContentType ()
			{
				return type;
			}
		};
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 * @see sk.qbsw.indy.base.behaviour.eprieskum.web.behaviour.AAJAXDownload#getFileName()
	 */
	protected String getFileName ()
	{
		return fileName;
	}


}
