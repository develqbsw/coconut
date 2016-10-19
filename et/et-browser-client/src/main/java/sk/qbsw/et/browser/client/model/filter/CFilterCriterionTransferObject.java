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

	/** The string value. */
	private String stringValue;

	/** The number value. */
	private Number numberValue;

	/** The operator. */
	@NotNull
	private EOperator operator;

	/**
	 * Instantiates a new c filter criterion transfer object.
	 */
	public CFilterCriterionTransferObject ()
	{
		//default constuctor
	}

	/**
	 * Instantiates a new c filter criterion.
	 *
	 * @param variable the variable
	 * @param value the value
	 * @param operator the operator
	 */
	public CFilterCriterionTransferObject (F variable, String stringValue, Number numberValue, EOperator operator)
	{
		this.property = variable;
		this.stringValue = stringValue;
		this.numberValue = numberValue;
		this.operator = operator;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Serializable getValue ()
	{
		if (stringValue != null)
		{
			return stringValue;
		}
		else
		{
			return numberValue;
		}
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
	 * Gets the string value.
	 *
	 * @return the string value
	 */
	public String getStringValue ()
	{
		return stringValue;
	}

	/**
	 * Gets the number value.
	 *
	 * @return the number value
	 */
	public Number getNumberValue ()
	{
		return numberValue;
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
	 * Sets the string value.
	 *
	 * @param stringValue the new string value
	 */
	public void setStringValue (String stringValue)
	{
		this.stringValue = stringValue;
	}

	/**
	 * Sets the number value.
	 *
	 * @param numberValue the new number value
	 */
	public void setNumberValue (Number numberValue)
	{
		this.numberValue = numberValue;
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
