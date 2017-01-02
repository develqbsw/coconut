package sk.qbsw.et.browser.api.mapping;

import com.querydsl.core.types.dsl.SimpleExpression;

/**
 * The querydsl expression wrapper.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.1
 * @since 1.16.1
 */
public class CBrwEntityPropertyExpression
{
	/** The expression. */
	private final SimpleExpression<?> expression;

	/**
	 * Instantiates a new c brw entity property expression.
	 *
	 * @param expression the expression
	 */
	public CBrwEntityPropertyExpression (SimpleExpression<?> expression)
	{
		this.expression = expression;
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
}
