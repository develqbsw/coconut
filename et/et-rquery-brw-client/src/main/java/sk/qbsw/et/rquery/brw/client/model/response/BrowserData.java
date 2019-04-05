package sk.qbsw.et.rquery.brw.client.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * The browser data.
 *
 * @param <E> the entity type
 * @version 2.1.0
 * @since 2.1.0
 */
@Setter
@Getter
public class BrowserData<E extends Serializable> implements Serializable
{
	private static final long serialVersionUID = 7640767817652819983L;

	private List<E> content;

	private long totalElements;

	/**
	 * Instantiates a new C brw dto.
	 */
	public BrowserData ()
	{
		// default constructor
	}

	/**
	 * Instantiates a new C brw dto.
	 *
	 * @param content the content
	 * @param totalElements the total elements
	 */
	public BrowserData (List<E> content, long totalElements)
	{
		this.content = content;
		this.totalElements = totalElements;
	}

	@Override
	public String toString ()
	{
		return "BrowserData [totalElements=" + this.totalElements + ", content=" + this.content + "]";
	}
}
