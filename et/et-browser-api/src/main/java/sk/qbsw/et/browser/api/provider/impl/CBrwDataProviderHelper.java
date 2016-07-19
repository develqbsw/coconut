package sk.qbsw.et.browser.api.provider.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;

import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CFilterCriterionTransferObject;
import sk.qbsw.et.browser.client.model.filter.CPagingTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriterionTransferObject;
import sk.qbsw.et.browser.client.model.filter.ENullPrecedence;
import sk.qbsw.et.browser.client.model.filter.EOperator;
import sk.qbsw.et.browser.client.model.filter.ESortDirection;
import sk.qbsw.et.browser.core.exception.CBrwUndefinedVariableMappingException;

/**
 * The client data mapper.
 *
 * @author Marian Oravec
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwDataProviderHelper
{
	/**
	 * Instantiates a new c brw data provider helper.
	 */
	private CBrwDataProviderHelper ()
	{
	}

	/**
	 * Convert filter criteria to predicate.
	 *
	 * @param <F> the generic type
	 * @param filterCriteria the filter criteria
	 * @param mapping the mapping
	 * @return the predicate
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	public static <F extends IFilterable> Predicate convertFilterCriteriaToPredicate (final CFilterCriteriaTransferObject<F> filterCriteria, final Map<F, SimpleExpression<?>> mapping) throws CBrwUndefinedVariableMappingException
	{
		BooleanBuilder predicateBuilder = new BooleanBuilder();

		for (CFilterCriterionTransferObject<F> filterCriterion : filterCriteria.getCriteria())
		{
			predicateBuilder.and(convertFilterCriterionToPredicate(filterCriterion, mapping));
		}

		return predicateBuilder;
	}

	/**
	 * Convert filter criterion to predicate.
	 *
	 * @param <F> the generic type
	 * @param filterCriterion the filter criterion
	 * @param mapping the mapping
	 * @return the predicate
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	@SuppressWarnings ({"unchecked", "rawtypes"})
	private static <F extends IFilterable> Predicate convertFilterCriterionToPredicate (final CFilterCriterionTransferObject<F> filterCriterion, final Map<F, SimpleExpression<?>> mapping) throws CBrwUndefinedVariableMappingException
	{
		final SimpleExpression<?> expression = getExpressionFromMapping(filterCriterion.getProperty(), mapping);
		final Serializable value = filterCriterion.getValue();
		final EOperator operator = filterCriterion.getOperator();

		if (expression instanceof BooleanOperation)
		{
			return (BooleanOperation) expression;
		}
		else if (EOperator.LIKE_IGNORE_CASE == operator && (expression instanceof StringExpression))
		{
			return ((StringExpression) expression).containsIgnoreCase((String) value);
		}
		else if ( (operator != null) && (expression instanceof ComparableExpression))
		{
			switch (operator)
			{
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
	 * Creates the where between date time predicate.
	 *
	 * @param expression the expression
	 * @param value the value
	 * @return the predicate
	 */
	@SuppressWarnings ({"unchecked", "rawtypes"})
	private static Predicate createWhereBetweenDateTimePredicate (final SimpleExpression<?> expression, final Object value)
	{
		if (value instanceof DateTime)
		{
			DateTime valueDT = (DateTime) value;
			DateTime start = valueDT.withTimeAtStartOfDay();
			DateTime end = start.plusDays(1).minusMillis(1);
			return ((ComparableExpression) expression).goe(start).and( ((ComparableExpression) expression).loe(end));
		}
		else
		{
			throw new IllegalArgumentException("Unsuported value type for BETWEEN_DATE_TIME: " + value.getClass());
		}
	}

	/**
	 * Convert sorting criteria and paging to pageable.
	 *
	 * @param <F> the generic type
	 * @param sortingCriteria the sorting criteria
	 * @param paging the paging
	 * @param propertiesMapping the properties mapping
	 * @return the pageable
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	public static <F extends IFilterable> Pageable convertSortingCriteriaAndPagingToPageable (final CSortingCriteriaTransferObject<F> sortingCriteria, final CPagingTransferObject paging, final Map<F, String> propertiesMapping) throws CBrwUndefinedVariableMappingException
	{
		return new PageRequest(paging.getPage(), paging.getSize(), convertSortingCriteriaToSort(sortingCriteria, propertiesMapping));

	}

	/**
	 * Convert sorting criteria to sort.
	 *
	 * @param <F> the generic type
	 * @param sortingCriteria the sorting criteria
	 * @param propertiesMapping the properties mapping
	 * @return the sort
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	public static <F extends IFilterable> Sort convertSortingCriteriaToSort (CSortingCriteriaTransferObject<F> sortingCriteria, final Map<F, String> propertiesMapping) throws CBrwUndefinedVariableMappingException
	{
		List<Order> orders = new ArrayList<>();

		for (CSortingCriterionTransferObject<F> sortingCriterion : sortingCriteria.getCriteria())
		{
			orders.add(convertSortingCriterionToOrder(sortingCriterion, propertiesMapping));
		}

		return new Sort(orders);
	}

	/**
	 * Convert sorting criterion to order.
	 *
	 * @param <F> the generic type
	 * @param sortingCriterion the sorting criterion
	 * @param propertiesMapping the properties mapping
	 * @return the order
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	private static <F extends IFilterable> Order convertSortingCriterionToOrder (CSortingCriterionTransferObject<F> sortingCriterion, final Map<F, String> propertiesMapping) throws CBrwUndefinedVariableMappingException
	{
		final Direction direction = convertClientDirectionToDirection(sortingCriterion.getDirection());
		final String propertyName = getVariableFromMapping(sortingCriterion.getProperty(), propertiesMapping);
		final NullHandling nullHandling = convertNullPrecedenceToNullHandling(sortingCriterion.getNullPrecedence());

		return new Order(direction, propertyName, nullHandling);
	}

	/**
	 * Convert client direction to direction.
	 *
	 * @param clientDirection the client direction
	 * @return the direction
	 */
	private static Direction convertClientDirectionToDirection (ESortDirection clientDirection)
	{
		switch (clientDirection)
		{
			case ASC:
				return Direction.ASC;
			case DESC:
				return Direction.DESC;
			default:
				return Direction.ASC;
		}
	}

	/**
	 * Convert null precedence to null handling.
	 *
	 * @param clientNullPrecedence the client null precedence
	 * @return the null handling
	 */
	private static NullHandling convertNullPrecedenceToNullHandling (ENullPrecedence clientNullPrecedence)
	{
		switch (clientNullPrecedence)
		{
			case FIRST:
				return NullHandling.NULLS_FIRST;
			case LAST:
				return NullHandling.NULLS_LAST;
			case NONE:
				return NullHandling.NATIVE;
			default:
				return NullHandling.NATIVE;
		}
	}

	/**
	 * Gets the expression from mapping.
	 *
	 * @param <F> the generic type
	 * @param variable the variable
	 * @param mapping the mapping
	 * @return the expression from mapping
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	private static <F extends IFilterable> SimpleExpression<?> getExpressionFromMapping (F variable, final Map<F, SimpleExpression<?>> mapping) throws CBrwUndefinedVariableMappingException
	{
		SimpleExpression<?> expression = mapping.get(variable);

		if (expression == null)
		{
			throw new CBrwUndefinedVariableMappingException("The variable mapping not found for variable: " + variable);
		}

		return expression;
	}

	/**
	 * Gets the variable from mapping.
	 *
	 * @param <F> the generic type
	 * @param property the property
	 * @param mapping the mapping
	 * @return the variable from mapping
	 * @throws CBrwUndefinedVariableMappingException the c brw undefined variable mapping exception
	 */
	private static <F extends IFilterable> String getVariableFromMapping (F property, final Map<F, String> mapping) throws CBrwUndefinedVariableMappingException
	{
		String propertyName = mapping.get(property);

		if (propertyName == null)
		{
			throw new CBrwUndefinedVariableMappingException("The variable mapping not found for variable: " + property);
		}

		return propertyName;
	}

}
