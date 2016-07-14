/**
 * 
 */
package sk.qbsw.et.browser.client.model.filter;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import sk.qbsw.et.browser.client.model.IFilterable;

/**
 * The filter criterion.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CFilterCriterionTransferObject<F extends IFilterable> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5338691866901178569L;

	/** The column. */
	@NotNull
	private final F column;

	/** The value. */
	@NotNull
	private final Serializable value;

	/** The operator. */
	@NotNull
	private final EOperator operator;

	/**
	 * Instantiates a new c filter criterion.
	 *
	 * @param column the column
	 * @param value the value
	 * @param operator the operator
	 */
	public CFilterCriterionTransferObject (F column, Serializable value, EOperator operator)
	{
		this.column = column;
		this.value = value;
		this.operator = operator;
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Serializable getValue ()
	{
		return value;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public EOperator getOperator ()
	{
		return operator;
	}
}
