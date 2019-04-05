package sk.qbsw.et.rquery.core.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.configuration.expression.EntityPropertyEnumExpression;
import sk.qbsw.et.rquery.core.configuration.expression.EntityPropertyTypeExpression;
import sk.qbsw.et.rquery.core.exception.RQBusinessException;
import sk.qbsw.et.rquery.core.exception.RQUndefinedEntityMappingException;
import sk.qbsw.et.rquery.core.exception.RQUnsupportedOperatorException;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreFilterableType;
import sk.qbsw.et.rquery.core.model.CoreOperator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The default single predicate builder.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class SinglePredicateBuilderImpl implements SinglePredicateBuilder
{
	private static final String UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE = "Unsupported operator with type predicate: ";

	@Override
	@SuppressWarnings ("unchecked")
	public <F extends CoreFilterable> Predicate buildTypePredicate (EntityPathBase path, String value, CoreOperator operator, F property, EntityConfiguration<F> mapping) throws RQBusinessException
	{
		if (value != null)
		{
			Class<?> convertedValue = getTypeFromMapping(property, (CoreFilterableType) Enum.valueOf((Class<? extends Enum>) getEnumTypeFromMapping(property, mapping), value), mapping);

			switch (operator)
			{
				case EQ:
					return path.instanceOf(convertedValue);
				case NE:
					return path.instanceOf(convertedValue).not();
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}
		else
		{
			switch (operator)
			{
				case EQ:
					return path.isNull();
				case NE:
					return path.isNotNull();
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}

	}

	@Override
	@SuppressWarnings ("unchecked")
	public <F extends CoreFilterable> Predicate buildEnumPredicate (EnumPath path, String value, CoreOperator operator, F property, EntityConfiguration<F> mapping) throws RQBusinessException
	{
		Enum<? extends Enum<?>> enumValue = value != null ? Enum.valueOf((Class<? extends Enum>) getEnumTypeFromMapping(property, mapping), value) : null;
		return buildComparablePredicate(path, enumValue, operator);
	}

	@Override
	public Predicate buildBooleanPredicate (BooleanPath path, String value, CoreOperator operator) throws RQUnsupportedOperatorException
	{
		Boolean convertedValue = value != null ? Boolean.valueOf(value) : null;
		return buildComparablePredicate(path, convertedValue, operator);
	}

	@Override
	public Predicate buildDatePredicate (DatePath<LocalDate> path, String value, CoreOperator operator) throws RQUnsupportedOperatorException
	{
		LocalDate convertedValue = value != null ? LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE) : null;
		return buildComparablePredicate(path, convertedValue, operator);
	}

	@Override
	public Predicate buildTimePredicate (TimePath<LocalTime> path, String value, CoreOperator operator) throws RQUnsupportedOperatorException
	{
		LocalTime convertedValue = value != null ? LocalTime.parse(value, DateTimeFormatter.ISO_LOCAL_TIME) : null;
		return buildComparablePredicate(path, convertedValue, operator);
	}

	@Override
	public Predicate buildDateTimePredicate (DateTimePath<OffsetDateTime> path, String value, CoreOperator operator) throws RQUnsupportedOperatorException
	{
		OffsetDateTime convertedValue = value != null ? OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
		return buildComparablePredicate(path, convertedValue, operator);
	}

	@Override
	public Predicate buildStringPredicate (StringPath path, String value, CoreOperator operator) throws RQUnsupportedOperatorException
	{
		try
		{
			return buildComparablePredicate(path, value, operator);
		}
		catch (RQBusinessException ex)
		{
			switch (operator)
			{
				case LIKE_IGNORE_CASE:
					return path.containsIgnoreCase(value);
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}
	}

	@Override
	@SuppressWarnings ({"Duplicates"})
	public Predicate buildNumberPredicate (NumberPath<Long> path, String value, CoreOperator operator) throws RQUnsupportedOperatorException
	{
		if (value != null)
		{
			Long convertedValue = Long.parseLong(value);

			switch (operator)
			{
				case EQ:
					return path.eq(convertedValue);
				case NE:
					return path.ne(convertedValue);
				case GT:
					return path.gt(convertedValue);
				case GOE:
					return path.goe(convertedValue);
				case LT:
					return path.lt(convertedValue);
				case LOE:
					return path.loe(convertedValue);
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}
		else
		{
			switch (operator)
			{
				case EQ:
					return path.isNull();
				case NE:
					return path.isNotNull();
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}
	}

	@Override
	@SuppressWarnings ("unchecked")
	public Predicate buildSimpleExpressionPredicate (SimpleExpression path, String value, CoreOperator operator) throws RQUnsupportedOperatorException
	{
		if (value != null)
		{
			switch (operator)
			{
				case EQ:
					return path.eq(value);
				case NE:
					return path.ne(value);
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}
		else
		{
			switch (operator)
			{
				case EQ:
					return path.isNull();
				case NE:
					return path.isNotNull();
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}
	}

	@SuppressWarnings ({"Duplicates"})
	private <T extends Comparable, E extends ComparableExpression<T>> Predicate buildComparablePredicate (E path, T value, CoreOperator operator) throws RQUnsupportedOperatorException
	{
		if (value != null)
		{
			switch (operator)
			{
				case EQ:
					return path.eq(value);
				case NE:
					return path.ne(value);
				case GT:
					return path.gt(value);
				case GOE:
					return path.goe(value);
				case LT:
					return path.lt(value);
				case LOE:
					return path.loe(value);
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}
		else
		{
			switch (operator)
			{
				case EQ:
					return path.isNull();
				case NE:
					return path.isNotNull();
				default:
					throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
			}
		}
	}

	private <F extends CoreFilterable> Class<?> getTypeFromMapping (final F property, final CoreFilterableType type, final EntityConfiguration<F> entityMapping) throws RQUndefinedEntityMappingException
	{
		if (entityMapping.getExpression(property) instanceof EntityPropertyTypeExpression)
		{
			return ((EntityPropertyTypeExpression) entityMapping.getExpression(property)).getPairs().get(type);
		}

		throw new RQUndefinedEntityMappingException("The entity property identity type not found for property: " + property);
	}

	private <F extends CoreFilterable> Class<? extends Enum<?>> getEnumTypeFromMapping (final F property, final EntityConfiguration<F> entityMapping) throws RQUndefinedEntityMappingException
	{
		if (entityMapping.getExpression(property) instanceof EntityPropertyEnumExpression)
		{
			return ((EntityPropertyEnumExpression) entityMapping.getExpression(property)).getEnumType();
		}

		throw new RQUndefinedEntityMappingException("The entity property identity enum not found for property: " + property);
	}
}
