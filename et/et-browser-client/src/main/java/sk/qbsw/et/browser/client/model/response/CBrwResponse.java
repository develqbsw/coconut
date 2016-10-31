package sk.qbsw.et.browser.client.model.response;

import java.util.List;

/**
 * The brw response.
 *
 * @version 1.16.0
 * @since 1.16.0
 * 
 * @param <T> the data type
 */
public class CBrwResponse<T>extends CFilterResponse<T>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4230449456817870851L;

	/** The total elements. */
	private long totalElements;

	public CBrwResponse ()
	{
		//default constructor
	}

	/**
	 * Instantiates a new c brw response.
	 *
	 * @param content the content
	 * @param totalElements the total elements
	 */
	public CBrwResponse (List<T> content, long totalElements)
	{
		super(content);
		this.totalElements = totalElements;
	}

	/**
	 * Gets the total elements.
	 *
	 * @return the total elements
	 */
	public long getTotalElements ()
	{
		return totalElements;
	}

	/**
	 * Sets the total elements.
	 *
	 * @param totalElements the new total elements
	 */
	public void setTotalElements (long totalElements)
	{
		this.totalElements = totalElements;
	}
}
