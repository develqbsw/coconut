package sk.qbsw.et.rquery.client.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The data response body.
 *
 * @param <E> the entity type
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class DataResponseBody<E extends Serializable> implements Serializable
{
	private static final long serialVersionUID = -4230449456817870851L;

	@NotNull
	private List<E> content = new ArrayList<>();

	/**
	 * Instantiates a new Data response body.
	 */
	public DataResponseBody ()
	{
		// default constructor
	}

	/**
	 * Instantiates a new Data response body.
	 *
	 * @param content the content
	 */
	public DataResponseBody (List<E> content)
	{
		this.content = content;
	}
}
