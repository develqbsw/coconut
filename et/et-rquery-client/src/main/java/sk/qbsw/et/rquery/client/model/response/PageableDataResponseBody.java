package sk.qbsw.et.rquery.client.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * The pageable data response body.
 *
 * @param <E> the entity type
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class PageableDataResponseBody<E extends Serializable>extends DataResponseBody<E>
{
	private static final long serialVersionUID = -4230449456817870851L;

	@NotNull
	private long totalElements;

	/**
	 * Instantiates a new Pageable data response body.
	 */
	public PageableDataResponseBody ()
	{
		// default constructor
	}

	/**
	 * Instantiates a new Pageable data response body.
	 *
	 * @param content the content
	 * @param totalElements the total elements
	 */
	public PageableDataResponseBody (List<E> content, long totalElements)
	{
		super(content);
		this.totalElements = totalElements;
	}
}
