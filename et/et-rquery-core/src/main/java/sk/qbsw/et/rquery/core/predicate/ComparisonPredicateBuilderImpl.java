package sk.qbsw.et.rquery.core.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.exception.RQUndefinedEntityMappingException;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreOperator;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The default comparison predicate builder implementation.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class ComparisonPredicateBuilderImpl implements ComparisonPredicateBuilder
{
	private static final String NULL_STRING = "null";

	private final SinglePredicateBuilder singlePredicateBuilder;

	/**
	 * Instantiates a new Comparison predicate builder.
	 *
	 * @param singlePredicateBuilder the single predicate builder
	 */
	public ComparisonPredicateBuilderImpl (SinglePredicateBuilder singlePredicateBuilder)
	{
		this.singlePredicateBuilder = singlePredicateBuilder;
	}

	@Override
	@SuppressWarnings ("unchecked")
	public <F extends CoreFilterable> Predicate buildPredicate (F property, List<String> values, CoreOperator operator, EntityConfiguration<F> mapping)
	{
		final SimpleExpression<?> expression = getExpressionFromMapping(property, mapping);
		final List<String> normalizedValues = normalizeValues(values);
		Predicate predicate;

		if ( (predicate = buildTypePredicate(expression, property, normalizedValues, operator, mapping)) != null)
		{
			return predicate;
		}
		else if ( (predicate = buildComparablePredicate(expression, property, normalizedValues, operator, mapping)) != null)
		{
			return predicate;
		}
		else if ( (predicate = buildNumberPredicate(expression, normalizedValues, operator)) != null)
		{
			return predicate;
		}
		else
		{
			return singlePredicateBuilder.buildSimpleExpressionPredicate(expression, normalizedValues, operator);
		}
	}

	private List<String> normalizeValues (List<String> values)
	{
		// replace null string with null
		return values.stream().map(s -> s != null && s.equalsIgnoreCase(NULL_STRING) ? null : s).collect(Collectors.toList());
	}

	private <F extends CoreFilterable> Predicate buildTypePredicate (SimpleExpression<?> expression, F property, List<String> values, CoreOperator operator, EntityConfiguration<F> mapping)
	{
		if (expression instanceof EntityPathBase)
		{
			return singlePredicateBuilder.buildTypePredicate((EntityPathBase) expression, values, operator, property, mapping);
		}
		else
		{
			return null;
		}
	}

	@SuppressWarnings ("unchecked")
	private <F extends CoreFilterable> Predicate buildComparablePredicate (SimpleExpression<?> expression, F property, List<String> values, CoreOperator operator, EntityConfiguration<F> mapping)
	{
		if (expression instanceof EnumPath)
		{
			return singlePredicateBuilder.buildEnumPredicate((EnumPath) expression, values, operator, property, mapping);
		}
		else if (expression instanceof BooleanPath)
		{
			return singlePredicateBuilder.buildBooleanPredicate((BooleanPath) expression, values, operator);
		}
		else if (expression instanceof DatePath)
		{
			return singlePredicateBuilder.buildDatePredicate((DatePath) expression, values, operator);
		}
		else if (expression instanceof TimePath)
		{
			return singlePredicateBuilder.buildTimePredicate((TimePath) expression, values, operator);
		}
		else if (expression instanceof DateTimePath)
		{
			return singlePredicateBuilder.buildDateTimePredicate((DateTimePath) expression, values, operator);
		}
		else if (expression instanceof StringExpression)
		{
			return singlePredicateBuilder.buildStringPredicate((StringPath) expression, values, operator);
		}
		else
		{
			return null;
		}
	}

	@SuppressWarnings ("unchecked")
	private Predicate buildNumberPredicate (SimpleExpression<?> expression, List<String> values, CoreOperator operator)
	{
		if (expression instanceof NumberPath && expression.getType().isAssignableFrom(Short.class))
		{
			return singlePredicateBuilder.buildShortPredicate((NumberPath) expression, values, operator);
		}
		else if (expression instanceof NumberPath && expression.getType().isAssignableFrom(Byte.class))
		{
			return singlePredicateBuilder.buildBytePredicate((NumberPath) expression, values, operator);
		}
		else if (expression instanceof NumberPath && expression.getType().isAssignableFrom(Integer.class))
		{
			return singlePredicateBuilder.buildIntegerPredicate((NumberPath) expression, values, operator);
		}
		else if (expression instanceof NumberPath && expression.getType().isAssignableFrom(Long.class))
		{
			return singlePredicateBuilder.buildLongPredicate((NumberPath) expression, values, operator);
		}
		else if (expression instanceof NumberPath && expression.getType().isAssignableFrom(Float.class))
		{
			return singlePredicateBuilder.buildFloatPredicate((NumberPath) expression, values, operator);
		}
		else if (expression instanceof NumberPath && expression.getType().isAssignableFrom(Double.class))
		{
			return singlePredicateBuilder.buildDoublePredicate((NumberPath) expression, values, operator);
		}
		else if (expression instanceof NumberPath && expression.getType().isAssignableFrom(BigDecimal.class))
		{
			return singlePredicateBuilder.buildBigDecimalPredicate((NumberPath) expression, values, operator);
		}
		else
		{
			return null;
		}
	}

	private <F extends CoreFilterable> SimpleExpression<?> getExpressionFromMapping (final F property, final EntityConfiguration<F> entityMapping)
	{
		SimpleExpression<?> expression = entityMapping.getExpression(property).getExpression();

		if (expression == null)
		{
			throw new RQUndefinedEntityMappingException("The entity mapping not found for property: " + property);
		}

		return expression;
	}
}
