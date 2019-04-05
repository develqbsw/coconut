package sk.qbsw.et.rquery.core.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;

import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreOperator;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.exception.RQBusinessException;
import sk.qbsw.et.rquery.core.exception.RQUndefinedEntityMappingException;

/**
 * The default comparison predicate builder implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class ComparisonPredicateBuilderImpl implements ComparisonPredicateBuilder
{
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
	public <F extends CoreFilterable> Predicate buildPredicate (F property, String value, CoreOperator operator, EntityConfiguration<F> mapping) throws RQBusinessException
	{
		final SimpleExpression<?> expression = getExpressionFromMapping(property, mapping);

		if (expression instanceof EntityPathBase)
		{
			return singlePredicateBuilder.buildTypePredicate((EntityPathBase) expression, value, operator, property, mapping);
		}
		else if (expression instanceof EnumPath)
		{
			return singlePredicateBuilder.buildEnumPredicate((EnumPath) expression, value, operator, property, mapping);
		}
		else if (expression instanceof BooleanPath)
		{
			return singlePredicateBuilder.buildBooleanPredicate((BooleanPath) expression, value, operator);
		}
		else if (expression instanceof DatePath)
		{
			return singlePredicateBuilder.buildDatePredicate((DatePath) expression, value, operator);
		}
		else if (expression instanceof TimePath)
		{
			return singlePredicateBuilder.buildTimePredicate((TimePath) expression, value, operator);
		}
		else if (expression instanceof DateTimePath)
		{
			return singlePredicateBuilder.buildDateTimePredicate((DateTimePath) expression, value, operator);
		}
		else if (expression instanceof StringExpression)
		{
			return singlePredicateBuilder.buildStringPredicate((StringPath) expression, value, operator);
		}
		else if (expression instanceof NumberPath)
		{
			return singlePredicateBuilder.buildNumberPredicate((NumberPath) expression, value, operator);
		}
		else
		{
			return singlePredicateBuilder.buildSimpleExpressionPredicate(expression, value, operator);
		}
	}

	private <F extends CoreFilterable> SimpleExpression<?> getExpressionFromMapping (final F property, final EntityConfiguration<F> entityMapping) throws RQUndefinedEntityMappingException
	{
		SimpleExpression<?> expression = entityMapping.getExpression(property).getExpression();

		if (expression == null)
		{
			throw new RQUndefinedEntityMappingException("The entity mapping not found for property: " + property);
		}

		return expression;
	}
}
