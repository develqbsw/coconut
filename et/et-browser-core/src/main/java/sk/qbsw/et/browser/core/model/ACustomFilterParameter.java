package sk.qbsw.et.browser.core.model;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.SimpleExpression;

import sk.qbsw.core.persistence.dao.jpa.qdsl.EOperator;

/**
 * The custom filter parameter.
 * 
 * @author Marian Oravec
 * @author Tomas Lauro
 * 
 * @since 1.15.0
 * @version 1.15.0
 */
public abstract class ACustomFilterParameter extends CFilterParameter
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -198081563050839488L;

	/**
	 * Instantiates a new a custom filter parameter.
	 *
	 * @param expression the expression
	 * @param value the value
	 * @param operator the operator
	 */
	public ACustomFilterParameter (SimpleExpression<?> expression, Object value, EOperator operator)
	{
		super(expression, value, operator);
	}

	/**
	 * Instantiates a new a custom filter parameter.
	 *
	 * @param expression the expression
	 * @param operator the operator
	 */
	public ACustomFilterParameter (SimpleExpression<?> expression, EOperator operator)
	{
		super(expression, operator);
	}

	/**
	 * Creates the with value.
	 *
	 * @param value the value
	 * @return the a custom filter parameter
	 */
	public abstract ACustomFilterParameter createWithValue (Object value);

	/**
	 * Gets the predicate.
	 *
	 * @return the predicate
	 */
	public abstract Predicate getPredicate ();
}
