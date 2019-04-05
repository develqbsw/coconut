package sk.qbsw.et.rquery.brw.binding.converter;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.dsl.SimpleExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import sk.qbsw.et.rquery.brw.binding.model.OffsetPageable;
import sk.qbsw.et.rquery.brw.binding.mapper.FilterableMapper;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.NullPrecedence;
import sk.qbsw.et.rquery.brw.client.model.SortDirection;
import sk.qbsw.et.rquery.brw.client.model.criteria.Paging;
import sk.qbsw.et.rquery.brw.client.model.criteria.SortingCriteria;
import sk.qbsw.et.rquery.brw.client.model.criteria.SortingCriterion;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.exception.RQBusinessException;
import sk.qbsw.et.rquery.core.exception.RQUndefinedEntityMappingException;
import sk.qbsw.et.rquery.core.model.CoreFilterable;

import java.util.ArrayList;
import java.util.List;

/**
 * The default sorting and paging criteria converter implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class SortingPagingCriteriaConverterImpl implements SortingPagingCriteriaConverter
{
	@Override
	public <F extends Filterable, C extends CoreFilterable> Pageable convertToPageable (SortingCriteria<F> sortingCriteria, Paging paging, EntityConfiguration<C> mapping, FilterableMapper<F, C> filterableMapper) throws RQBusinessException
	{
		if (sortingCriteria.getCriteria().isEmpty())
		{
			return new OffsetPageable(paging.getOffset(), paging.getLimit());
		}
		else
		{
			return new OffsetPageable(paging.getOffset(), paging.getLimit(), convertToSort(sortingCriteria, mapping, filterableMapper));
		}
	}

	@Override
	public <F extends Filterable, C extends CoreFilterable> Sort convertToSort (SortingCriteria<F> sortingCriteria, final EntityConfiguration<C> mapping, FilterableMapper<F, C> filterableMapper) throws RQBusinessException
	{
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

		for (SortingCriterion<F> sortingCriterion : sortingCriteria.getCriteria())
		{
			orderSpecifiers.add(convertToOrderSpecifier(sortingCriterion, mapping, filterableMapper));
		}

		return new QSort(orderSpecifiers);
	}

	private <F extends Filterable, C extends CoreFilterable> OrderSpecifier<?> convertToOrderSpecifier (final SortingCriterion<F> sortingCriterion, final EntityConfiguration<C> entityMapping, FilterableMapper<F, C> filterableMapper) throws RQBusinessException
	{
		final Order order = convertDirectionToOrder(sortingCriterion.getDirection());
		final NullHandling nullHandling = convertNullPrecedenceToNullHandling(sortingCriterion.getNullPrecedence());

		return new OrderSpecifier(order, getExpressionFromMapping(sortingCriterion.getProperty(), entityMapping, filterableMapper), nullHandling);
	}

	private Order convertDirectionToOrder (SortDirection clientDirection)
	{
		switch (clientDirection)
		{
			case DESC:
				return Order.DESC;
			default:
				return Order.ASC;
		}
	}

	private NullHandling convertNullPrecedenceToNullHandling (NullPrecedence clientNullPrecedence)
	{
		switch (clientNullPrecedence)
		{
			case FIRST:
				return NullHandling.NullsFirst;
			case LAST:
				return NullHandling.NullsLast;
			default:
				return NullHandling.Default;
		}
	}

	private <F extends Filterable, C extends CoreFilterable> SimpleExpression<?> getExpressionFromMapping (final F property, final EntityConfiguration<C> entityMapping, FilterableMapper<F, C> filterableMapper) throws RQBusinessException
	{
		SimpleExpression<?> expression = entityMapping.getExpression(filterableMapper.mapToCoreFilterable(property)).getExpression();

		if (expression == null)
		{
			throw new RQUndefinedEntityMappingException("The entity mapping not found for property: " + property);
		}

		return expression;
	}
}
