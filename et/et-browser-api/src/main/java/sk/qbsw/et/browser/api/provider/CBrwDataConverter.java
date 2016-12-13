package sk.qbsw.et.browser.api.provider;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;

import sk.qbsw.et.browser.api.mapping.CBrwEntityMapping;
import sk.qbsw.et.browser.api.mapping.CBrwEntityPropertyEnumExpression;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CFilterCriterionTransferObject;
import sk.qbsw.et.browser.client.model.filter.CPagingTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriterionTransferObject;
import sk.qbsw.et.browser.client.model.filter.ELogicalOperator;
import sk.qbsw.et.browser.client.model.filter.ENullPrecedence;
import sk.qbsw.et.browser.client.model.filter.EOperator;
import sk.qbsw.et.browser.client.model.filter.ESortDirection;
import sk.qbsw.et.browser.core.exception.CBrwBusinessException;
import sk.qbsw.et.browser.core.exception.CBrwUndefinedEntityMappingException;
import sk.qbsw.et.browser.core.exception.EBrwError;
import sk.qbsw.et.browser.core.model.COffsetPageRequest;

/**
 * The default data converter.
 *
 * @author Marian Oravec
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwDataConverter implements IBrwDataConverter
{
	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProviderConverter#convertFilterCriteriaToPredicate(sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject, sk.qbsw.et.browser.api.mapping.CBrwEntityMapping)
	 */
	@Override
	public <F extends IFilterable> Predicate convertFilterCriteriaToPredicate (final CFilterCriteriaTransferObject<F> filterCriteria, final CBrwEntityMapping<F> mapping) throws CBrwBusinessException
	{
		BooleanBuilder predicateBuilder = new BooleanBuilder();
		BooleanBuilder orBuilder = new BooleanBuilder();

		for (CFilterCriterionTransferObject<F> filterCriterion : filterCriteria.getCriteria())
		{
			if (filterCriterion.getLogicalOperator().equals(ELogicalOperator.OR))
			{
				//just add to or builder
				orBuilder.or(convertFilterCriterionToPredicate(filterCriterion, mapping));
			}

			if (filterCriterion.getLogicalOperator().equals(ELogicalOperator.AND))
			{
				//add current orbuilder to predicate and create new one
				predicateBuilder.and(orBuilder.getValue());
				orBuilder = new BooleanBuilder().or(convertFilterCriterionToPredicate(filterCriterion, mapping));
			}
		}

		//final add or conditions
		predicateBuilder.and(orBuilder.getValue());

		return predicateBuilder;
	}

	/**
	 * Convert filter criterion to predicate.
	 *
	 * @param filterCriterion the filter criterion
	 * @param mapping the mapping
	 * @return the predicate
	 * @throws CBrwBusinessException 
	 */
	@SuppressWarnings ({"unchecked", "rawtypes"})
	private <F extends IFilterable> Predicate convertFilterCriterionToPredicate (final CFilterCriterionTransferObject<F> filterCriterion, final CBrwEntityMapping<F> mapping) throws CBrwBusinessException
	{
		final SimpleExpression<?> expression = getExpressionFromMapping(filterCriterion.getProperty(), mapping);
		final Serializable value = parseValueFromFilterCriterion(filterCriterion);
		final EOperator operator = filterCriterion.getOperator();

		if (value instanceof Class<?> && expression instanceof EntityPathBase)
		{
			return ((EntityPathBase) expression).instanceOf((Class) value);
		}
		if (expression instanceof BooleanOperation)
		{
			return (BooleanOperation) expression;
		}
		else if (EOperator.LIKE_IGNORE_CASE == operator && (expression instanceof StringExpression))
		{
			return ((StringExpression) expression).containsIgnoreCase((String) value);
		}
		else if ( (operator != null) && (expression instanceof EnumExpression))
		{
			Enum<? extends Enum<?>> valueOf = Enum.valueOf((Class<? extends Enum>) getEnumTypeFromMapping(filterCriterion.getProperty(), mapping), value.toString());
			if (EOperator.EQ.equals(operator))
			{
				return ((EnumPath) expression).eq(valueOf);
			}
			else
			{
				return ((EnumPath) expression).ne(valueOf);
			}
		}
		else if ( (operator != null) && (expression instanceof ComparableExpression))
		{
			switch (operator)
			{
				case NE:
					return ((ComparableExpression) expression).ne((Comparable) value);
				case GT:
					return ((ComparableExpression) expression).gt((Comparable) value);
				case GOE:
					return ((ComparableExpression) expression).goe((Comparable) value);
				case LT:
					return ((ComparableExpression) expression).lt((Comparable<?>) value);
				case LOE:
					return ((ComparableExpression) expression).loe((Comparable<?>) value);
				case BETWEEN_DATE_TIME:
					return createWhereBetweenDateTimePredicate(expression, value);
				default:
					if (expression instanceof BooleanExpression)
					{
						return ((ComparableExpression) expression).eq(Boolean.valueOf(value.toString()));
					}
					else
					{
						return ((ComparableExpression) expression).eq(value);
					}
			}
		}
		else if ( (operator != null) && (expression instanceof NumberPath) && (value instanceof Number))
		{
			switch (operator)
			{
				case NE:
					return ((NumberPath) expression).ne((Number) value);
				case GT:
					return ((NumberPath) expression).gt((Number) value);
				case GOE:
					return ((NumberPath) expression).goe((Number) value);
				case LT:
					return ((NumberPath) expression).lt((Number) value);
				case LOE:
					return ((NumberPath) expression).loe((Number) value);
				default:
					return ((NumberPath) expression).eq(value);
			}
		}
		else
		{
			return ((SimpleExpression) expression).eq(value);
		}
	}

	/**
	 * Parses the value from filter criterion.
	 *
	 * @param <F> the generic type
	 * @param filterCriterion the filter criterion
	 * @return the serializable
	 * @throws CBrwBusinessException the c brw business exception
	 */
	private <F extends IFilterable> Serializable parseValueFromFilterCriterion (final CFilterCriterionTransferObject<F> filterCriterion) throws CBrwBusinessException
	{
		switch (filterCriterion.getValueType())
		{
			case STRING:
				return filterCriterion.getValue();
			case NUMBER:
				return Long.parseLong(filterCriterion.getValue());
			case DATE:
				return LocalDate.parse(filterCriterion.getValue(), DateTimeFormatter.ISO_LOCAL_DATE);
			case DATE_TIME:
				return OffsetDateTime.parse(filterCriterion.getValue(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			case CLASS_NAME:
				try
				{
					return Class.forName(filterCriterion.getValue());
				}
				catch (ClassNotFoundException e)
				{
					throw new CBrwBusinessException("Classs with name " + filterCriterion.getValue() + " not found", e, EBrwError.UNDEFINED_CLASS);
				}
			default:
				return filterCriterion.getValue();
		}
	}

	/**
	 * Creates the where between date time predicate.
	 *
	 * @param expression the expression
	 * @param value the value
	 * @return the predicate
	 */
	@SuppressWarnings ({"unchecked", "rawtypes"})
	private static Predicate createWhereBetweenDateTimePredicate (final SimpleExpression<?> expression, final Object value)
	{
		if (value instanceof OffsetDateTime)
		{
			OffsetDateTime valueDT = (OffsetDateTime) value;
			LocalDateTime start = valueDT.toLocalDate().atStartOfDay();
			LocalDateTime end = start.plusDays(1).minusSeconds(1);
			return ((ComparableExpression) expression).goe(start).and( ((ComparableExpression) expression).loe(end));
		}
		else
		{
			throw new IllegalArgumentException("Unsuported value type for BETWEEN_DATE_TIME: " + value.getClass());
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProviderConverter#convertSortingCriteriaAndPagingToPageable(sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject, sk.qbsw.et.browser.client.model.filter.CPagingTransferObject, sk.qbsw.et.browser.api.mapping.CBrwEntityMapping)
	 */
	@Override
	public <F extends IFilterable> Pageable convertSortingCriteriaAndPagingToPageable (final CSortingCriteriaTransferObject<F> sortingCriteria, final CPagingTransferObject paging, final CBrwEntityMapping<F> entityMapping) throws CBrwUndefinedEntityMappingException
	{
		if (sortingCriteria.getCriteria().isEmpty())
		{
			return new COffsetPageRequest(paging.getOffset(), paging.getLimit());
		}
		else
		{
			return new COffsetPageRequest(paging.getOffset(), paging.getLimit(), convertSortingCriteriaToSort(sortingCriteria, entityMapping));
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProviderConverter#convertSortingCriteriaToSort(sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject, sk.qbsw.et.browser.api.mapping.CBrwEntityMapping)
	 */
	@Override
	public <F extends IFilterable> Sort convertSortingCriteriaToSort (CSortingCriteriaTransferObject<F> sortingCriteria, final CBrwEntityMapping<F> entityMapping) throws CBrwUndefinedEntityMappingException
	{
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

		for (CSortingCriterionTransferObject<F> sortingCriterion : sortingCriteria.getCriteria())
		{
			orderSpecifiers.add(convertSortingCriterionToOrderSpecifier(sortingCriterion, entityMapping));
		}

		return new QSort(orderSpecifiers);
	}

	/**
	 * Convert sorting criterion to order specifier.
	 *
	 * @param <F> the generic type
	 * @param sortingCriterion the sorting criterion
	 * @param entityMapping the entity mapping
	 * @return the order specifier
	 * @throws CBrwUndefinedEntityMappingException the c brw undefined entity mapping exception
	 */
	@SuppressWarnings ({"unchecked", "rawtypes"})
	private <F extends IFilterable> OrderSpecifier<?> convertSortingCriterionToOrderSpecifier (final CSortingCriterionTransferObject<F> sortingCriterion, final CBrwEntityMapping<F> entityMapping) throws CBrwUndefinedEntityMappingException
	{
		final com.querydsl.core.types.Order order = convertClientDirectionToOrder(sortingCriterion.getDirection());
		final com.querydsl.core.types.OrderSpecifier.NullHandling nullHandling = convertNullPrecedenceToNullHandling(sortingCriterion.getNullPrecedence());

		return new OrderSpecifier(order, getExpressionFromMapping(sortingCriterion.getProperty(), entityMapping), nullHandling);
	}

	/**
	 * Convert client direction to order.
	 *
	 * @param clientDirection the client direction
	 * @return the order
	 */
	private Order convertClientDirectionToOrder (ESortDirection clientDirection)
	{
		switch (clientDirection)
		{
			case ASC:
				return Order.ASC;
			case DESC:
				return Order.DESC;
			default:
				return Order.ASC;
		}
	}

	/**
	 * Convert null precedence to null handling.
	 *
	 * @param clientNullPrecedence the client null precedence
	 * @return the null handling
	 */
	private NullHandling convertNullPrecedenceToNullHandling (ENullPrecedence clientNullPrecedence)
	{
		switch (clientNullPrecedence)
		{
			case FIRST:
				return NullHandling.NullsFirst;
			case LAST:
				return NullHandling.NullsLast;
			case NONE:
				return NullHandling.Default;
			default:
				return NullHandling.Default;
		}
	}

	/**
	 * Gets the expression from mapping.
	 *
	 * @param property the property
	 * @param entityMapping the mapping
	 * @return the expression from mapping
	 * @throws CBrwUndefinedEntityMappingException the c brw undefined variable mapping exception
	 */
	private <F extends IFilterable> SimpleExpression<?> getExpressionFromMapping (final F property, final CBrwEntityMapping<F> entityMapping) throws CBrwUndefinedEntityMappingException
	{
		SimpleExpression<?> expression = entityMapping.getExpression(property).getExpression();

		if (expression == null)
		{
			throw new CBrwUndefinedEntityMappingException("The entity mapping not found for property: " + property);
		}

		return expression;
	}

	/**
	 * Gets the enum type from mapping.
	 *
	 * @param <F> the generic type
	 * @param property the property
	 * @param entityMapping the entity mapping
	 * @return the enum type from mapping
	 * @throws CBrwUndefinedEntityMappingException the c brw undefined entity mapping exception
	 */
	private <F extends IFilterable> Class<? extends Enum<?>> getEnumTypeFromMapping (final F property, final CBrwEntityMapping<F> entityMapping) throws CBrwUndefinedEntityMappingException
	{
		if (entityMapping.getExpression(property) instanceof CBrwEntityPropertyEnumExpression)
		{
			return ((CBrwEntityPropertyEnumExpression) entityMapping.getExpression(property)).getEnumType();
		}

		throw new CBrwUndefinedEntityMappingException("The entity property identity enum not found for property: " + property);
	}
}
