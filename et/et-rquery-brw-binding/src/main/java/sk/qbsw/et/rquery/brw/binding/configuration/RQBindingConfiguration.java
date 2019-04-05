package sk.qbsw.et.rquery.brw.binding.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.et.rquery.brw.binding.converter.FilterCriteriaConverter;
import sk.qbsw.et.rquery.brw.binding.converter.FilterCriteriaConverterImpl;
import sk.qbsw.et.rquery.brw.binding.converter.SortingPagingCriteriaConverter;
import sk.qbsw.et.rquery.brw.binding.converter.SortingPagingCriteriaConverterImpl;
import sk.qbsw.et.rquery.brw.binding.mapper.OperatorMapper;
import sk.qbsw.et.rquery.brw.binding.mapper.OperatorMapperImpl;
import sk.qbsw.et.rquery.core.configuration.RQCoreConfiguration;
import sk.qbsw.et.rquery.core.predicate.ComparisonPredicateBuilder;

/**
 * The request query binding configuration.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class RQBindingConfiguration extends RQCoreConfiguration
{
	@Bean
	public OperatorMapper operatorMapper ()
	{
		return new OperatorMapperImpl();
	}

	@Bean
	public FilterCriteriaConverter filterCriteriaConverter (ComparisonPredicateBuilder comparisonPredicateBuilder, OperatorMapper operatorMapper)
	{
		return new FilterCriteriaConverterImpl(comparisonPredicateBuilder, operatorMapper);
	}

	@Bean
	public SortingPagingCriteriaConverter sortingPagingCriteriaConverter ()
	{
		return new SortingPagingCriteriaConverterImpl();
	}
}
