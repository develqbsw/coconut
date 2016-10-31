package sk.qbsw.et.browser.api.mapping;

import com.querydsl.core.types.dsl.SimpleExpression;

/**
 * The querydsl expression and property name pair.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwEntityPropertyIdentityPair
{
	/** The expression. */
	private final SimpleExpression<?> expression;

	/** The property. */
	private final String propertyName;

	/**
	 * Instantiates a new c brw expression property pair.
	 *
	 * @param expression the expression
	 * @param propertyName the property
	 */
	public CBrwEntityPropertyIdentityPair (SimpleExpression<?> expression, String propertyName)
	{
		this.expression = expression;
		this.propertyName = propertyName;
	}

	/**
	 * Gets the expression.
	 *
	 * @return the expression
	 */
	public SimpleExpression<?> getExpression ()
	{
		return expression;
	}

	/**
	 * Gets the property.
	 *
	 * @return the property
	 */
	public String getPropertyName ()
	{
		return propertyName;
	}
}
