package sk.qbsw.et.browser.client.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * The brw response.
 *
 * @version 1.16.0
 * @since 1.16.0
 * 
 * @param <T> the data type
 */
public class CFilterResponse<T> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4230449456817870851L;

	/** The content. */
	private List<T> content;

	public CFilterResponse ()
	{
		//default constructor
	}

	public CFilterResponse (List<T> content)
	{
		this.content = content;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public List<T> getContent ()
	{
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent (List<T> content)
	{
		this.content = content;
	}
}
