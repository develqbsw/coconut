package sk.qbsw.et.rquery.client.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The pageable data.
 *
 * @param <E> the entity type
 * @version 2.2.0
 * @since 2.2.0
 */
@Setter
@Getter
public class PageableData<E extends Serializable> implements Serializable
{
	private static final long serialVersionUID = 7640767817652819983L;

	@NotNull
	private List<E> content = new ArrayList<>();

	@NotNull
	private long totalElements;

	/**
	 * Instantiates a new Pageable data.
	 */
	public PageableData ()
	{
		// default constructor
	}

	/**
	 * Instantiates a new Pageable data.
	 *
	 * @param content the content
	 * @param totalElements the total elements
	 */
	public PageableData (List<E> content, long totalElements)
	{
		this.content = content;
		this.totalElements = totalElements;
	}

	@Override
	public String toString ()
	{
		return "PageableData [totalElements=" + this.totalElements + ", content=" + this.content + "]";
	}
}
