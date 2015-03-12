package sk.qbsw.core.persistence.model;

import java.io.Serializable;

import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.SimpleExpression;

import sk.qbsw.core.persistence.dao.querydsl.EOperator;

/**
 * The Class CFilterParameter.
 *
 * @author Marián Oravec
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class CFilterParameter implements Serializable {

	/** The expression. */
	private final SimpleExpression<?> expression;
	
	/** The value. */
	private final Object value;
	
	/** The operator. */
	private final EOperator operator;

	/**
	 * Instantiates a new c filter parameter.
	 *
	 * @param expression the expression
	 * @param value the value
	 * @param operator the operator
	 */
	public CFilterParameter(SimpleExpression<?> expression, Object value, EOperator operator) {
		this.expression = expression;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * Instantiates a new c filter parameter.
	 *
	 * @param expression the expression
	 * @param value the value
	 */
	public CFilterParameter(SimpleExpression<?> expression, Object value) {
		this(expression, value, null);
	}

	/**
	 * Instantiates a new c filter parameter.
	 *
	 * @param expression the expression
	 */
	public CFilterParameter(BooleanExpression expression) {
		this(expression, null);
	}

	/**
	 * Gets the expression.
	 *
	 * @return the expression
	 */
	public SimpleExpression<?> getExpression() {
		return this.expression;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return this.value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CFilterParameter [expression=" + this.expression + ", value=" + this.value + ", operator=" + this.operator + "]";
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public EOperator getOperator() {
		return this.operator;
	}
}
