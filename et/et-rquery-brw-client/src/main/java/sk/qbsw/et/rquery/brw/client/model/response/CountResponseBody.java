package sk.qbsw.et.rquery.brw.client.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The count response body.
 *
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
@Setter
public class CountResponseBody implements Serializable
{
	private static final long serialVersionUID = -958431419726318069L;

	private long elementsCount;

	/**
	 * Instantiates a new c count response.
	 */
	public CountResponseBody ()
	{
		// default constructor
	}

	/**
	 * Instantiates a new c count response.
	 *
	 * @param elementsCount the elements count
	 */
	public CountResponseBody (long elementsCount)
	{
		this.elementsCount = elementsCount;
	}
}
