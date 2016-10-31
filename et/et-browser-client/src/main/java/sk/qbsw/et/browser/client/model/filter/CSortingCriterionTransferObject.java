/**
 * 
 */
package sk.qbsw.et.browser.client.model.filter;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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

	/** The property. */
	@NotNull
	private F property;

	/** The direction. */
	@NotNull
	@JsonInclude (Include.NON_NULL)
	private ESortDirection direction = ESortDirection.ASC;

	/** The null precedence. */
	@NotNull
	@JsonInclude (Include.NON_NULL)
	private ENullPrecedence nullPrecedence = ENullPrecedence.NONE;

	/**
	 * Instantiates a new c sorting criterion transfer object.
	 */
	public CSortingCriterionTransferObject ()
	{
		//default constuctor
	}

	/**
	 * Instantiates a new c sorting criterion transfer object.
	 *
	 * @param property the variable
	 */
	public CSortingCriterionTransferObject (F property)
	{
		this(property, ESortDirection.ASC, ENullPrecedence.NONE);
	}

	/**
	 * Instantiates a new c filter criterion.
	 *
	 * @param property the variable
	 * @param direction the direction
	 */
	public CSortingCriterionTransferObject (F property, ESortDirection direction)
	{
		this(property, direction, ENullPrecedence.NONE);
	}

	/**
	 * Instantiates a new c sorting criterion transfer object.
	 *
	 * @param property the variable
	 * @param direction the direction
	 * @param nullPrecedence the null precedence
	 */
	public CSortingCriterionTransferObject (F property, ESortDirection direction, ENullPrecedence nullPrecedence)
	{
		this.property = property;
		this.direction = direction;
		this.nullPrecedence = nullPrecedence;
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
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public ESortDirection getDirection ()
	{
		return direction;
	}

	/**
	 * Gets the null precedence.
	 *
	 * @return the null precedence
	 */
	public ENullPrecedence getNullPrecedence ()
	{
		return nullPrecedence;
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
	 * Sets the direction.
	 *
	 * @param direction the new direction
	 */
	public void setDirection (ESortDirection direction)
	{
		this.direction = direction;
	}

	/**
	 * Sets the null precedence.
	 *
	 * @param nullPrecedence the new null precedence
	 */
	public void setNullPrecedence (ENullPrecedence nullPrecedence)
	{
		this.nullPrecedence = nullPrecedence;
	}
}
