package sk.qbsw.et.rquery.brw.client.model.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.NullPrecedence;
import sk.qbsw.et.rquery.brw.client.model.SortDirection;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The sorting criterion.
 *
 * @param <F> the filterable
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class SortingCriterion<F extends Filterable> implements Serializable
{
	private static final long serialVersionUID = -6534359748372988981L;

	@NotNull
	private F property;

	@NotNull
	@JsonInclude (Include.NON_NULL)
	private SortDirection direction = SortDirection.ASC;

	@NotNull
	@JsonInclude (Include.NON_NULL)
	private NullPrecedence nullPrecedence = NullPrecedence.NONE;

	/**
	 * Instantiates a new C sorting criterion transfer object.
	 */
	public SortingCriterion ()
	{
		// default constuctor
	}

	/**
	 * Instantiates a new C sorting criterion transfer object.
	 *
	 * @param property the property
	 */
	public SortingCriterion (F property)
	{
		this(property, SortDirection.ASC, NullPrecedence.NONE);
	}

	/**
	 * Instantiates a new C sorting criterion transfer object.
	 *
	 * @param property the property
	 * @param direction the direction
	 */
	public SortingCriterion (F property, SortDirection direction)
	{
		this(property, direction, NullPrecedence.NONE);
	}

	/**
	 * Instantiates a new C sorting criterion transfer object.
	 *
	 * @param property the property
	 * @param direction the direction
	 * @param nullPrecedence the null precedence
	 */
	public SortingCriterion (F property, SortDirection direction, NullPrecedence nullPrecedence)
	{
		this.property = property;
		this.direction = direction;
		this.nullPrecedence = nullPrecedence;
	}
}
