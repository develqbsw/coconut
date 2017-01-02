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
 * @version 1.16.0
 * @param <F> the property
 * @since 1.16.0
 */
public class CFilterCriterionTransferObject<F extends IFilterable> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5338691866901178569L;

	/** The logical operator. */
	@NotNull
	private ELogicalOperator logicalOperator;

	/** The property. */
	@NotNull
	private F property;

	/** The value. */
	@NotNull
	private String value;

	/** The value type. */
	@NotNull
	private EValueType valueType;

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
	 * @param logicalOperator the logical operator
	 * @param variable the variable
	 * @param value the value
	 * @param valueType the value type
	 * @param operator the operator
	 */
	public CFilterCriterionTransferObject (ELogicalOperator logicalOperator, F variable, String value, EValueType valueType, EOperator operator)
	{
		this.logicalOperator = logicalOperator;
		this.property = variable;
		this.value = value;
		this.valueType = valueType;
		this.operator = operator;
	}

	/**
	 * Gets the logical operator.
	 *
	 * @return the logical operator
	 */
	public ELogicalOperator getLogicalOperator ()
	{
		return logicalOperator;
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
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator (EOperator operator)
	{
		this.operator = operator;
	}

	/**
	
	/**
	 * Sets the logical operator.
	 *
	 * @param logicalOperator the new logical operator
	 */
	public void setLogicalOperator (ELogicalOperator logicalOperator)
	{
		this.logicalOperator = logicalOperator;
	}

	/**
	 * Gets the value type.
	 *
	 * @return the value type
	 */
	public EValueType getValueType ()
	{
		return valueType;
	}

	/**
	 * Sets the value type.
	 *
	 * @param valueType the new value type
	 */
	public void setValueType (EValueType valueType)
	{
		this.valueType = valueType;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue ()
	{
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue (String value)
	{
		this.value = value;
	}
}
