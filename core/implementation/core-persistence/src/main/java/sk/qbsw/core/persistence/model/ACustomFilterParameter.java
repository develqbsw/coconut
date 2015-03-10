package sk.qbsw.core.persistence.model;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.SimpleExpression;

import sk.qbsw.core.persistence.dao.querydsl.EOperator;

/**
 * The Class ACustomFilterParameter.
 */
@SuppressWarnings("serial")
public abstract class ACustomFilterParameter extends CFilterParameter {

	/**
	 * Instantiates a new a custom filter parameter.
	 *
	 * @param expression the expression
	 * @param value the value
	 * @param operator the operator
	 */
	public ACustomFilterParameter(SimpleExpression<?> expression, Object value, EOperator operator) {
		super(expression, value, operator);
	}

	/**
	 * Instantiates a new a custom filter parameter.
	 *
	 * @param expression the expression
	 * @param operator the operator
	 */
	public ACustomFilterParameter(SimpleExpression<?> expression, EOperator operator) {
		super(expression, operator);
	}

	/**
	 * Creates the with value.
	 *
	 * @param value the value
	 * @return the a custom filter parameter
	 */
	public abstract ACustomFilterParameter createWithValue(Object value);

	/**
	 * Gets the predicate.
	 *
	 * @return the predicate
	 */
	public abstract Predicate getPredicate();
}
