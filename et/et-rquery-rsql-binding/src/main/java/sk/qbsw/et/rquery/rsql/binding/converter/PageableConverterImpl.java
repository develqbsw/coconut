package sk.qbsw.et.rquery.rsql.binding.converter;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.dsl.SimpleExpression;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.exception.RQUndefinedEntityMappingException;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.rsql.binding.mapper.FilterableMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The default pageable converter implementation.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class PageableConverterImpl implements PageableConverter
{
	@Override
	public <C extends CoreFilterable> Pageable convertToPageable (Pageable pageable, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), convertToSort(pageable.getSort(), configuration, mapper));
	}

	@Override
	public <C extends CoreFilterable> Sort convertToSort (Sort sort, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		List<OrderSpecifier<?>> orderSpecifiers = sort.map(o -> convertToOrderSpecifier(o, configuration, mapper)).stream().collect(Collectors.toList());
		return new QSort(orderSpecifiers);
	}

	@SuppressWarnings ("unchecked")
	private <C extends CoreFilterable> OrderSpecifier<?> convertToOrderSpecifier (Sort.Order order, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		Order convertedOrder = convertToOrder(order.getDirection());
		NullHandling convertedNullHandling = convertToNullHandling(order.getNullHandling());
		SimpleExpression<?> expression = getExpressionFromMapping(order.getProperty(), configuration, mapper);

		return new OrderSpecifier(convertedOrder, expression, convertedNullHandling);
	}

	private Order convertToOrder (Sort.Direction direction)
	{
		switch (direction)
		{
			case DESC:
				return Order.DESC;
			default:
				return Order.ASC;
		}
	}

	private NullHandling convertToNullHandling (Sort.NullHandling nullHandling)
	{
		switch (nullHandling)
		{
			case NULLS_FIRST:
				return NullHandling.NullsFirst;
			case NULLS_LAST:
				return NullHandling.NullsLast;
			default:
				return NullHandling.Default;
		}
	}

	private <C extends CoreFilterable> SimpleExpression<?> getExpressionFromMapping (String selector, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		SimpleExpression<?> expression = configuration.getExpression(mapper.mapNormalizedSelectorToCoreFilterable(selector)).getExpression();

		if (expression == null)
		{
			throw new RQUndefinedEntityMappingException("The entity mapping not found for selector: " + selector);
		}

		return expression;
	}
}
