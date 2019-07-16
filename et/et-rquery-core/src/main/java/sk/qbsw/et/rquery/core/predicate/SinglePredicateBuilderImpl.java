package sk.qbsw.et.rquery.core.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import org.apache.commons.collections.CollectionUtils;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.configuration.expression.EntityPropertyEnumExpression;
import sk.qbsw.et.rquery.core.configuration.expression.EntityPropertyTypeExpression;
import sk.qbsw.et.rquery.core.exception.RQUndefinedEntityMappingException;
import sk.qbsw.et.rquery.core.exception.RQUnsupportedOperatorException;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreFilterableType;
import sk.qbsw.et.rquery.core.model.CoreOperator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The default single predicate builder.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class SinglePredicateBuilderImpl implements SinglePredicateBuilder
{
	private static final String UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE = "Unsupported operator with type predicate: ";

	@Override
	@SuppressWarnings ("unchecked")
	public <F extends CoreFilterable> Predicate buildTypePredicate (EntityPathBase path, List<String> values, CoreOperator operator, F property, EntityConfiguration<F> mapping)
	{
		if (CollectionUtils.isNotEmpty(values) && !values.contains(null))
		{
			List<Class<?>> convertedValues = values.stream().map(v -> getTypeFromMapping(property, (CoreFilterableType) Enum.valueOf((Class<? extends Enum>) getEnumTypeFromMapping(property, mapping), v), mapping)).collect(Collectors.toList());
			Class<?> convertedValue = convertedValues.get(0);

			switch (operator)
			{
				case EQ:
					return path.instanceOf(convertedValue);
				case NE:
					return path.instanceOf(convertedValue).not();
				case IN:
					return path.instanceOfAny(convertedValues.toArray(new Class[0]));
				case NOT_IN:
					return path.instanceOfAny(convertedValues.toArray(new Class[0])).not();
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
	public <F extends CoreFilterable> Predicate buildEnumPredicate (EnumPath path, List<String> values, CoreOperator operator, F property, EntityConfiguration<F> mapping)
	{
		List<Enum> convertedValues = (List<Enum>) values.stream().map(e -> Enum.valueOf((Class<? extends Enum>) getEnumTypeFromMapping(property, mapping), e)).collect(Collectors.toList());
		return buildComparablePredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildBooleanPredicate (BooleanPath path, List<String> values, CoreOperator operator)
	{
		List<Boolean> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(Boolean::parseBoolean).collect(Collectors.toList()) : new ArrayList<>();
		return buildComparablePredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildDatePredicate (DatePath<LocalDate> path, List<String> values, CoreOperator operator)
	{
		List<LocalDate> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(d -> LocalDate.parse(d, DateTimeFormatter.ISO_LOCAL_DATE)).collect(Collectors.toList()) : new ArrayList<>();
		return buildComparablePredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildTimePredicate (TimePath<LocalTime> path, List<String> values, CoreOperator operator)
	{
		List<LocalTime> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(d -> LocalTime.parse(d, DateTimeFormatter.ISO_LOCAL_TIME)).collect(Collectors.toList()) : new ArrayList<>();
		return buildComparablePredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildDateTimePredicate (DateTimePath<OffsetDateTime> path, List<String> values, CoreOperator operator)
	{
		List<OffsetDateTime> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(d -> OffsetDateTime.parse(d, DateTimeFormatter.ISO_OFFSET_DATE_TIME)).collect(Collectors.toList()) : new ArrayList<>();
		return buildComparablePredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildStringPredicate (StringPath path, List<String> values, CoreOperator operator)
	{
		try
		{
			return buildComparablePredicate(path, values, operator);
		}
		catch (RQUnsupportedOperatorException ex)
		{
			if (CollectionUtils.isNotEmpty(values) && !values.contains(null))
			{
				String searchValue = values.get(0).replace("*", "%");

				if (CollectionUtils.isNotEmpty(values) && CoreOperator.LIKE.equals(operator))
				{
					return path.like(searchValue);
				}
				if (CollectionUtils.isNotEmpty(values) && CoreOperator.NOT_LIKE.equals(operator))
				{
					return path.notLike(searchValue);
				}
				if (CollectionUtils.isNotEmpty(values) && CoreOperator.LIKE_IGNORE_CASE.equals(operator))
				{
					return path.likeIgnoreCase(searchValue);
				}
			}

			throw new RQUnsupportedOperatorException(UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + operator);
		}
	}

	@Override
	public Predicate buildShortPredicate (NumberPath<Short> path, List<String> values, CoreOperator operator)
	{
		List<Short> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(Short::parseShort).collect(Collectors.toList()) : new ArrayList<>();
		return buildNumberPredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildBytePredicate (NumberPath<Byte> path, List<String> values, CoreOperator operator)
	{
		List<Byte> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(Byte::parseByte).collect(Collectors.toList()) : new ArrayList<>();
		return buildNumberPredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildIntegerPredicate (NumberPath<Integer> path, List<String> values, CoreOperator operator)
	{
		List<Integer> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(Integer::parseInt).collect(Collectors.toList()) : new ArrayList<>();
		return buildNumberPredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildLongPredicate (NumberPath<Long> path, List<String> values, CoreOperator operator)
	{
		List<Long> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(Long::parseLong).collect(Collectors.toList()) : new ArrayList<>();
		return buildNumberPredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildFloatPredicate (NumberPath<Float> path, List<String> values, CoreOperator operator)
	{
		List<Float> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(Float::parseFloat).collect(Collectors.toList()) : new ArrayList<>();
		return buildNumberPredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildDoublePredicate (NumberPath<Double> path, List<String> values, CoreOperator operator)
	{
		List<Double> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(Double::parseDouble).collect(Collectors.toList()) : new ArrayList<>();
		return buildNumberPredicate(path, convertedValues, operator);
	}

	@Override
	public Predicate buildBigDecimalPredicate (NumberPath<BigDecimal> path, List<String> values, CoreOperator operator)
	{
		List<BigDecimal> convertedValues = CollectionUtils.isNotEmpty(values) ? values.stream().map(BigDecimal::new).collect(Collectors.toList()) : new ArrayList<>();
		return buildNumberPredicate(path, convertedValues, operator);
	}

	@SuppressWarnings ({"Duplicates"})
	private <T extends Number & Comparable<?>> Predicate buildNumberPredicate (NumberPath<T> path, List<T> values, CoreOperator operator)
	{
		if (CollectionUtils.isNotEmpty(values) && !values.contains(null))
		{
			T value = values.get(0);

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
				case IN:
					return path.in(values);
				case NOT_IN:
					return path.notIn(values);
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
	public Predicate buildSimpleExpressionPredicate (SimpleExpression path, List<String> values, CoreOperator operator)
	{
		if (CollectionUtils.isNotEmpty(values) && !values.contains(null))
		{
			String value = values.get(0);

			switch (operator)
			{
				case EQ:
					return path.eq(value);
				case NE:
					return path.ne(value);
				case IN:
					return path.in(values);
				case NOT_IN:
					return path.notIn(values);
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
	private <T extends Comparable, E extends ComparableExpression<T>> Predicate buildComparablePredicate (E path, List<T> values, CoreOperator operator)
	{
		if (CollectionUtils.isNotEmpty(values) && !values.contains(null))
		{
			T value = values.get(0);

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
				case IN:
					return path.in(values);
				case NOT_IN:
					return path.notIn(values);
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

	private <F extends CoreFilterable> Class<?> getTypeFromMapping (final F property, final CoreFilterableType type, final EntityConfiguration<F> entityMapping)
	{
		if (entityMapping.getExpression(property) instanceof EntityPropertyTypeExpression)
		{
			return ((EntityPropertyTypeExpression) entityMapping.getExpression(property)).getPairs().get(type);
		}

		throw new RQUndefinedEntityMappingException("The entity property identity type not found for property: " + property);
	}

	private <F extends CoreFilterable> Class<? extends Enum<?>> getEnumTypeFromMapping (final F property, final EntityConfiguration<F> entityMapping)
	{
		if (entityMapping.getExpression(property) instanceof EntityPropertyEnumExpression)
		{
			return ((EntityPropertyEnumExpression) entityMapping.getExpression(property)).getEnumType();
		}

		throw new RQUndefinedEntityMappingException("The entity property identity enum not found for property: " + property);
	}
}
