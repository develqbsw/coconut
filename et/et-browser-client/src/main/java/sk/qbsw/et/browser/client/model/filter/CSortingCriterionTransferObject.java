/**
 * 
 */
package sk.qbsw.et.browser.client.model.filter;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import sk.qbsw.et.browser.client.model.IFilterable;

/**
 * The sorting criterion.
 *
 * @param <F> the filterable
 * 
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CSortingCriterionTransferObject<F extends IFilterable> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6534359748372988981L;

	/** The column. */
	@NotNull
	private final F column;

	/** The direction. */
	@NotNull
	private final ESortDirection direction;

	/**
	 * Instantiates a new c filter criterion.
	 *
	 * @param column the column
	 * @param direction the direction
	 */
	public CSortingCriterionTransferObject (F column, ESortDirection direction)
	{
		this.column = column;
		this.direction = direction;
	}

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public F getColumn ()
	{
		return column;
	}

	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public ESortDirection getDirection ()
	{
		return direction;
	}
}
