package sk.qbsw.et.rquery.brw.client.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * The filter response body.
 *
 * @param <E> the type parameter
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
@Setter
public class FilterResponseBody<E extends Serializable> implements Serializable
{
	private static final long serialVersionUID = -4230449456817870851L;

	private List<E> content;

	/**
	 * Instantiates a new C filter response.
	 */
	public FilterResponseBody ()
	{
		// default constructor
	}

	/**
	 * Instantiates a new C filter response.
	 *
	 * @param content the content
	 */
	public FilterResponseBody (List<E> content)
	{
		this.content = content;
	}
}
