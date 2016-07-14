package sk.qbsw.et.browser.core.model;

import java.io.Serializable;

import com.querydsl.core.types.dsl.ComparableExpressionBase;

/**
 * The order parameter.
 *
 * @author Marián Oravec
 * @version 1.0.0
 * @since 1.0.0
 */
public class COrderParameter implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9099124049116673420L;

	/** The expression. */
	private final ComparableExpressionBase<? extends Comparable<?>> expression;

	/** The ascending. */
	private final boolean ascending;

	/**
	 * Instantiates a new c order parameter.
	 *
	 * @param expression the expression
	 * @param ascending the ascending
	 */
	public COrderParameter (ComparableExpressionBase<? extends Comparable<?>> expression, boolean ascending)
	{
		this.expression = expression;
		this.ascending = ascending;
	}

	/**
	 * Gets the expression.
	 *
	 * @return the expression
	 */
	public ComparableExpressionBase<? extends Comparable<?>> getExpression ()
	{
		return this.expression;
	}

	/**
	 * Checks if is ascending.
	 *
	 * @return true, if is ascending
	 */
	public boolean isAscending ()
	{
		return this.ascending;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		return "COrderParameter [expression=" + this.expression + ", ascending=" + this.ascending + "]";
	}

}
