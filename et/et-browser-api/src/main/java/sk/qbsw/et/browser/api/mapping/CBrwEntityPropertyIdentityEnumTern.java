package sk.qbsw.et.browser.api.mapping;

import com.querydsl.core.types.dsl.SimpleExpression;

/**
 * The querydsl expression and property name pair with enum class.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwEntityPropertyIdentityEnumTern extends CBrwEntityPropertyIdentityPair
{
	/** The enum type. */
	private final Class<? extends Enum<?>> enumType;

	/**
	 * Instantiates a new c brw entity property identity enum tern.
	 *
	 * @param expression the expression
	 * @param propertyName the property name
	 * @param enumType the enum type
	 */
	public CBrwEntityPropertyIdentityEnumTern (SimpleExpression<?> expression, String propertyName, Class<? extends Enum<?>> enumType)
	{
		super(expression, propertyName);
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
