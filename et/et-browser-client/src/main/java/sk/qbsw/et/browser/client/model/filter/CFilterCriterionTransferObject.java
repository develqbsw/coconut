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
 * @param <F> the property
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

	/** The property. */
	@NotNull
	private F property;

	/** The value. */
	@NotNull
	private Serializable value;

	/** The operator. */
	@NotNull
	private EOperator operator;

	/**
	 * Instantiates a new c filter criterion.
	 *
	 * @param variable the variable
	 * @param value the value
	 * @param operator the operator
	 */
	public CFilterCriterionTransferObject (F variable, Serializable value, EOperator operator)
	{
		this.property = variable;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * Gets the variable.
	 *
	 * @return the variable
	 */
	public F getProperty ()
	{
		return property;
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

	/**
	 * Sets the property.
	 *
	 * @param property the new property
	 */
	public void setProperty (F property)
	{
		this.property = property;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue (Serializable value)
	{
		this.value = value;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator (EOperator operator)
	{
		this.operator = operator;
	}
}
