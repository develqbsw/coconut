package sk.qbsw.core.persistence.model;

import java.io.Serializable;

import com.mysema.query.types.expr.ComparableExpressionBase;

/**
 * @author Marián Oravec
 * @since 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class COrderParameter implements Serializable {

	private final ComparableExpressionBase<? extends Comparable<?>> expression;
	private final boolean ascending;

	/**
	 * Instantiates a new c order parameter.
	 *
	 * @param expression the expression
	 * @param ascending the ascending
	 */
	public COrderParameter(ComparableExpressionBase<? extends Comparable<?>> expression, boolean ascending) {
		this.expression = expression;
		this.ascending = ascending;
	}

	public ComparableExpressionBase<? extends Comparable<?>> getExpression() {
		return this.expression;
	}

	public boolean isAscending() {
		return this.ascending;
	}

	@Override
	public String toString() {
		return "COrderParameter [expression=" + this.expression + ", ascending=" + this.ascending + "]";
	}

}
