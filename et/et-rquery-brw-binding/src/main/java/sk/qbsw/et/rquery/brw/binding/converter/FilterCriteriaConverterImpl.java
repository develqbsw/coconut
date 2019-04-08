package sk.qbsw.et.rquery.brw.binding.converter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import sk.qbsw.et.rquery.brw.binding.mapper.FilterableMapper;
import sk.qbsw.et.rquery.brw.binding.mapper.OperatorMapper;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.LogicalOperator;
import sk.qbsw.et.rquery.brw.client.model.criteria.FilterCriteria;
import sk.qbsw.et.rquery.brw.client.model.criteria.FilterCriterion;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.predicate.ComparisonPredicateBuilder;


/**
 * The default filter criteria converter implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class FilterCriteriaConverterImpl implements FilterCriteriaConverter
{
	private final ComparisonPredicateBuilder comparisonPredicateBuilder;

	private final OperatorMapper operatorMapper;

	/**
	 * Instantiates a new Filter criteria converter.
	 *
	 * @param comparisonPredicateBuilder the comparison predicate builder
	 * @param operatorMapper the operator mapper
	 */
	public FilterCriteriaConverterImpl (ComparisonPredicateBuilder comparisonPredicateBuilder, OperatorMapper operatorMapper)
	{
		this.comparisonPredicateBuilder = comparisonPredicateBuilder;
		this.operatorMapper = operatorMapper;
	}

	@Override
	public <F extends Filterable, C extends CoreFilterable> Predicate convertToPredicate (FilterCriteria<F> filterCriteria, EntityConfiguration<C> mapping, FilterableMapper<F, C> filterableMapper)
	{
		BooleanBuilder predicateBuilder = new BooleanBuilder();
		BooleanBuilder orBuilder = new BooleanBuilder();

		for (FilterCriterion<F> filterCriterion : filterCriteria.getCriteria())
		{
			if (filterCriterion.getLogicalOperator().equals(LogicalOperator.OR))
			{
				// just add to or builder
				orBuilder.or(comparisonPredicateBuilder.buildPredicate(filterableMapper.mapToCoreFilterable(filterCriterion.getProperty()), filterCriterion.getValues(), operatorMapper.mapToCoreOperator(filterCriterion.getOperator()), mapping));
			}

			if (filterCriterion.getLogicalOperator().equals(LogicalOperator.AND))
			{
				// add current orbuilder to predicate and create new one
				predicateBuilder.and(orBuilder.getValue());
				orBuilder = new BooleanBuilder().or(comparisonPredicateBuilder.buildPredicate(filterableMapper.mapToCoreFilterable(filterCriterion.getProperty()), filterCriterion.getValues(), operatorMapper.mapToCoreOperator(filterCriterion.getOperator()), mapping));
			}
		}

		// final add or conditions
		predicateBuilder.and(orBuilder.getValue());

		return predicateBuilder;
	}
}
