package sk.qbsw.indy.base.renderer;

import java.security.InvalidParameterException;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import sk.qbsw.indy.base.utils.CStringResourceReader;

/**
 * Localized Choice renderer which reads values from properties of object or from property file (for String values and enumerations)
 * @author Dalibor Rak
 * @author Tomas Leken
 * @author Marek Martinkovic
 * 
 * @version 1.3.0
 * @since 1.0.0
 *
 * @param <T> model for rendering
 */
public class CLocalizedChoiceRenderer<T> implements IChoiceRenderer<T>
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 
	 * Property expression for getting the display value. 
	 **/
	private final String displayExpression;

	/** 
	 * Property expression for getting the id. 
	 **/
	private final String idExpression;

	/** 
	 * Prefix for searching in the property file. 
	 **/
	private final String prefix;

	/**
	 * Construct. When you use this constructor, the display value will be determined by executing
	 * the given property expression on the list object, and the id will be based on the list index.
	 * The display value will be calculated by the given property expression
	 * 
	 * @param displayExpression
	 *            A property expression to get the display value
	 */
	public CLocalizedChoiceRenderer (String displayExpression)
	{
		this(displayExpression, null, null);
	}

	/**
	 * Construct. When you use this constructor, both the id and the display value will be
	 * determined by executing the given property expressions on the list object.
	 * 
	 * @param displayExpression
	 *            A property expression to get the display value
	 * @param idExpression
	 *            A property expression to get the id value
	 */
	public CLocalizedChoiceRenderer (String displayExpression, String idExpression)
	{
		this(displayExpression, idExpression, null);
	}

	/**
	 * Instantiates a new c localized choice renderer.
	 *
	 * @param displayExpression the display expression
	 * @param idExpression the id expression
	 * @param prefix the prefix used during the reading from property file
	 */
	public CLocalizedChoiceRenderer (String displayExpression, String idExpression, String prefix)
	{
		super();

		if (displayExpression == null || displayExpression.length() == 0)
		{
			throw new InvalidParameterException("Display Expression cannot be null or empty.");
		}

		this.displayExpression = displayExpression;
		this.idExpression = idExpression;
		this.prefix = prefix;
	}

	/**
	 * Gets the display value.
	 *
	 * @param object the object
	 * @return the display value
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(java.lang.Object)
	 */
	public Object getDisplayValue (T object)
	{
		// object is known - display value should be red from the properties
		if (object != null)
		{
			Object returnValue = PropertyResolver.getValue(displayExpression, object);

			if (returnValue instanceof String)
			{
				return getPropertyValue((String) returnValue);
			}
			return returnValue;
		}
		// object is null - display value should be empty string
		else
		{
			return "";
		}
	}

	/**
	 * Gets the id value.
	 *
	 * @param object the object
	 * @param index the index
	 * @return the id value
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getIdValue(java.lang.Object, int)
	 */
	public String getIdValue (T object, int index)
	{
		// if id expression is not set, return index of the value
		if (idExpression == null)
		{
			return Integer.toString(index);
		}

		// object is known - id value should be red from the properties
		if (object != null)
		{
			Object returnValue = PropertyResolver.getValue(idExpression, object);
			return returnValue != null ? returnValue.toString() : "";
		}
		// object is known - id value should be empty
		else
		{
			return "";
		}
	}

	/**
	 * Gets the property value.
	 *
	 * @param fileExpression the property expression
	 * @return the property value
	 */
	private String getPropertyValue (String fileExpression)
	{
		// nothing to read
		if (fileExpression == null)
		{
			return "";
		}

		// property expression is known
		String expression = fileExpression;
		if (prefix != null && prefix.length() > 0)
		{
			expression = prefix.trim() + "." + fileExpression;
		}

		// read property from file or return ""
		String retVal = CStringResourceReader.read(expression);
		return retVal != null ? retVal : "";
	}
}
