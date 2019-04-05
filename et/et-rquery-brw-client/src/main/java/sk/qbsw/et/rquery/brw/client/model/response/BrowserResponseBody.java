package sk.qbsw.et.rquery.brw.client.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * The browser response body.
 *
 * @param <E> the data type
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
@Setter
public class BrowserResponseBody<E extends Serializable>extends FilterResponseBody<E>
{
	private static final long serialVersionUID = -4230449456817870851L;

	private long totalElements;

	/**
	 * Instantiates a new Browser response body.
	 */
	public BrowserResponseBody ()
	{
		// default constructor
	}

	/**
	 * Instantiates a new c brw response.
	 *
	 * @param content the content
	 * @param totalElements the total elements
	 */
	public BrowserResponseBody (List<E> content, long totalElements)
	{
		super(content);
		this.totalElements = totalElements;
	}
}
