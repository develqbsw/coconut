package sk.qbsw.et.rquery.client.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The count data response body.
 *
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class CountDataResponseBody implements Serializable
{
	private static final long serialVersionUID = -958431419726318069L;

	@NotNull
	private long elementsCount;

	/**
	 * Instantiates a new Count data response body.
	 */
	public CountDataResponseBody ()
	{
		// default constructor
	}

	/**
	 * Instantiates a new Count data response body.
	 *
	 * @param elementsCount the elements count
	 */
	public CountDataResponseBody (long elementsCount)
	{
		this.elementsCount = elementsCount;
	}
}
