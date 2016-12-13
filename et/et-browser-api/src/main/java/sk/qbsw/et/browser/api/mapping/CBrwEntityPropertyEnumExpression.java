package sk.qbsw.et.browser.api.mapping;

import com.querydsl.core.types.dsl.SimpleExpression;

/**
 * The querydsl expression with enum class.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.1
 * @since 1.16.1
 */
public class CBrwEntityPropertyEnumExpression extends CBrwEntityPropertyExpression
{
	/** The enum type. */
	private final Class<? extends Enum<?>> enumType;

	/**
	 * Instantiates a new c brw entity property enum expression.
	 *
	 * @param expression the expression
	 * @param enumType the enum type
	 */
	public CBrwEntityPropertyEnumExpression (SimpleExpression<?> expression, Class<? extends Enum<?>> enumType)
	{
		super(expression);
		this.enumType = enumType;
	}

	/**
	 * Gets the enum type.
	 *
	 * @return the enum type
	 */
	public Class<? extends Enum<?>> getEnumType ()
	{
		return enumType;
	}
}
