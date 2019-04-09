package sk.qbsw.et.rquery.core.configuration.expression;

import com.querydsl.core.types.dsl.SimpleExpression;
import lombok.Getter;
import sk.qbsw.et.rquery.core.model.CoreFilterableType;

import java.util.Map;

/**
 * The querydsl expression with type informations.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
public class EntityPropertyTypeExpression extends EntityPropertyEnumExpression
{
	private final Map<CoreFilterableType, Class<?>> pairs;

	/**
	 * Instantiates a new Entity property type expression.
	 *
	 * @param expression the expression
	 * @param enumType the enum type
	 * @param pairs the pairs
	 */
	public EntityPropertyTypeExpression (SimpleExpression<?> expression, Class<? extends Enum<?>> enumType, Map<CoreFilterableType, Class<?>> pairs)
	{
		super(expression, enumType);
		this.pairs = pairs;
	}
}
