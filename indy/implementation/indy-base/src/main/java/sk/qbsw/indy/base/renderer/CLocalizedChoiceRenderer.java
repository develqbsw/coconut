package sk.qbsw.indy.base.renderer;

import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import sk.qbsw.indy.base.utils.CStringResourceReader;

/**
 * Default implementation of {@link org.apache.wicket.markup.html.form.IChoiceRenderer}. Usage:
 * <p>
 * 
 * <pre>
 * new DropDownChoice&lt;User&gt;(&quot;users&quot;, new Model&lt;User&gt;(selectedUser), listOfUsers)
 * </pre>
 * 
 * creates a DropDownChoice of users and the display value will be toString() and the id the index
 * of the object in the ListOfUsers.
 * </p>
 * <p>
 * 
 * <pre>
 * new DropDownChoice&lt;User&gt;(&quot;users&quot;, new Model&lt;User&gt;(selectedUser), listOfUsers,
 * 	new ChoiceRenderer&lt;User&gt;(&quot;name&quot;))
 * </pre>
 * 
 * creates a DropDownChoice of users and the display value will be looked up by property expression
 * ("name") and the id the index of the object in the ListOfUsers
 * </p>
 * <p>
 * 
 * <pre>
 * new DropDownChoice&lt;User&gt;(&quot;users&quot;, new Model&lt;User&gt;(selectedUser), listOfUsers,
 * 	new ChoiceRenderer&lt;User&gt;(&quot;name&quot;, &quot;id&quot;))
 * </pre>
 * 
 * creates a DropDownChoice of users and the display value will be looked up by property expression
 * ("name") and the id will be looked up by the property expression "id"
 * </p>
 * 
 * @author jcompagner
 * 
 * @param <T>
 *            The model object type
 */
public class CLocalizedChoiceRenderer<T> implements IChoiceRenderer<T>
{
	private static final long serialVersionUID = 1L;

	/** expression for getting the display value. */
	private final String displayExpression;

	/** expression for getting the id. */
	private final String idExpression;

	/**
	 * Construct. When you use this constructor, the display value will be determined by calling
	 * toString() on the list object, and the id will be based on the list index. the id value will
	 * be the index
	 */
	public CLocalizedChoiceRenderer()
	{
		super();
		this.displayExpression = null;
		this.idExpression = null;
	}

	/**
	 * Construct. When you use this constructor, the display value will be determined by executing
	 * the given property expression on the list object, and the id will be based on the list index.
	 * The display value will be calculated by the given property expression
	 * 
	 * @param displayExpression
	 *            A property expression to get the display value
	 */
	public CLocalizedChoiceRenderer(String displayExpression)
	{
		super();
		this.displayExpression = displayExpression;
		this.idExpression = null;
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
	public CLocalizedChoiceRenderer(String displayExpression, String idExpression)
	{
		super();
		this.displayExpression = displayExpression;
		this.idExpression = idExpression;
	}

	/**
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(java.lang.Object)
	 */
	public Object getDisplayValue(T object)
	{
		Object returnValue = object;
		if ((displayExpression != null) && (object != null))
		{
			returnValue = PropertyResolver.getValue(displayExpression, object);
			
			if(returnValue instanceof String){
				returnValue =  CStringResourceReader.read((String)returnValue);
			}
			
		}

		if (returnValue == null)
		{
			return "";
		}

		return returnValue;
	}

	/**
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getIdValue(java.lang.Object, int)
	 */
	public String getIdValue(T object, int index)
	{
		if (idExpression == null)
		{
			return Integer.toString(index);
		}

		if (object == null)
		{
			return "";
		}

		Object returnValue = PropertyResolver.getValue(idExpression, object);
		if (returnValue == null)
		{
			return "";
		}

		return returnValue.toString();
	}
}
