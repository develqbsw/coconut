package sk.qbsw.et.browser.client.model.response;

import java.io.Serializable;

/**
 * The count response.
 *
 * @version 1.16.0
 * @since 1.16.0
 */
public class CCountResponse implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -958431419726318069L;

	/** The elements count. */
	private long elementsCount;

	/**
	 * Instantiates a new c count response.
	 */
	public CCountResponse ()
	{
		//default constructor
	}

	/**
	 * Instantiates a new c count response.
	 *
	 * @param elementsCount the elements count
	 */
	public CCountResponse (long elementsCount)
	{
		this.elementsCount = elementsCount;
	}

	/**
	 * Gets the elements count.
	 *
	 * @return the elements count
	 */
	public long getElementsCount ()
	{
		return elementsCount;
	}

	/**
	 * Sets the elements count.
	 *
	 * @param elementsCount the new elements count
	 */
	public void setElementsCount (long elementsCount)
	{
		this.elementsCount = elementsCount;
	}
}
