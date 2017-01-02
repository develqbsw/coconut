package sk.qbsw.et.browser.api.mapping;

import java.util.Map;

import com.querydsl.core.types.dsl.SimpleExpression;

import sk.qbsw.et.browser.client.model.IFilterableType;

/**
 * The querydsl expression with type informations.
 *
 * @author Tomas Lauro
 * 
 * @param <T> the generic type
 * 
 * @version 1.16.1
 * @since 1.16.1
 */
public class CBrwEntityPropertyTypeExpression extends CBrwEntityPropertyEnumExpression
{
	/** The pairs. */
	private final Map<IFilterableType, Class<?>> pairs;

	/**
	 * Instantiates a new c brw entity property type expression.
	 *
	 * @param expression the expression
	 * @param enumType the enum type
	 * @param pairs the pairs
	 */
	public CBrwEntityPropertyTypeExpression (SimpleExpression<?> expression, Class<? extends Enum<?>> enumType, Map<IFilterableType, Class<?>> pairs)
	{
		super(expression, enumType);
		this.pairs = pairs;
	}

	/**
	 * Gets the pairs.
	 *
	 * @return the pairs
	 */
	public Map<IFilterableType, Class<?>> getPairs ()
	{
		return pairs;
	}
}
