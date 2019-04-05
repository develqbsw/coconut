package sk.qbsw.et.rquery.core.configuration.expression;

import com.querydsl.core.types.dsl.SimpleExpression;

import lombok.Getter;

/**
 * The querydsl expression with enum class.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
public class EntityPropertyEnumExpression extends EntityPropertyExpression
{
	private final Class<? extends Enum<?>> enumType;

	/**
	 * Instantiates a new Entity property enum expression.
	 *
	 * @param expression the expression
	 * @param enumType the enum type
	 */
	public EntityPropertyEnumExpression (SimpleExpression<?> expression, Class<? extends Enum<?>> enumType)
	{
		super(expression);
		this.enumType = enumType;
	}
}
