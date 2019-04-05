package sk.qbsw.et.rquery.core.configuration;

import com.querydsl.core.types.dsl.SimpleExpression;
import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.et.rquery.core.configuration.expression.EntityPropertyEnumExpression;
import sk.qbsw.et.rquery.core.configuration.expression.EntityPropertyExpression;
import sk.qbsw.et.rquery.core.configuration.expression.EntityPropertyTypeExpression;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreFilterableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The entity configuration mapper builder.
 *
 * @param <F> the filterable
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class EntityConfigurationBuilder<F extends CoreFilterable>
{
	private final Map<F, EntityPropertyExpression> mapping = new HashMap<>();

	private final List<CJoinDescriptor<?>> joins = new ArrayList<>();

	/**
	 * Add property mapping entity configuration builder.
	 *
	 * @param property the property
	 * @param expression the expression
	 * @return the entity configuration builder
	 */
	public EntityConfigurationBuilder<F> addPropertyMapping (F property, SimpleExpression<?> expression)
	{
		mapping.put(property, new EntityPropertyExpression(expression));
		return this;
	}

	/**
	 * Add property mapping entity configuration builder.
	 *
	 * @param property the property
	 * @param expression the expression
	 * @param enumType the enum type
	 * @return the entity configuration builder
	 */
	public EntityConfigurationBuilder<F> addPropertyMapping (F property, SimpleExpression<?> expression, Class<? extends Enum<?>> enumType)
	{
		mapping.put(property, new EntityPropertyEnumExpression(expression, enumType));
		return this;
	}

	/**
	 * Add property mapping entity configuration builder.
	 *
	 * @param property the property
	 * @param expression the expression
	 * @param enumType the enum type
	 * @param typeDefinitions the type definitions
	 * @return the entity configuration builder
	 */
	public EntityConfigurationBuilder<F> addPropertyMapping (F property, SimpleExpression<?> expression, Class<? extends Enum<?>> enumType, Map<CoreFilterableType, Class<?>> typeDefinitions)
	{
		mapping.put(property, new EntityPropertyTypeExpression(expression, enumType, typeDefinitions));
		return this;
	}

	/**
	 * Add join entity configuration builder.
	 *
	 * @param join the join
	 * @return the entity configuration builder
	 */
	public EntityConfigurationBuilder<F> addJoin (CJoinDescriptor<?> join)
	{
		joins.add(join);
		return this;
	}

	/**
	 * Build entity mapping entity configuration.
	 *
	 * @return the entity configuration
	 */
	public EntityConfiguration<F> buildEntityMapping ()
	{
		return new EntityConfiguration<>(mapping, joins);
	}
}
