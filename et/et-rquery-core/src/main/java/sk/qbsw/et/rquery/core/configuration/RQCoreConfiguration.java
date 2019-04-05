package sk.qbsw.et.rquery.core.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.et.rquery.core.predicate.ComparisonPredicateBuilder;
import sk.qbsw.et.rquery.core.predicate.ComparisonPredicateBuilderImpl;
import sk.qbsw.et.rquery.core.predicate.SinglePredicateBuilder;
import sk.qbsw.et.rquery.core.predicate.SinglePredicateBuilderImpl;

/**
 * The request query core configuration.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class RQCoreConfiguration
{
	@Bean
	public SinglePredicateBuilder singlePredicateBuilder ()
	{
		return new SinglePredicateBuilderImpl();
	}

	@Bean
	public ComparisonPredicateBuilder comparisonPredicateBuilder (SinglePredicateBuilder singlePredicateBuilder)
	{
		return new ComparisonPredicateBuilderImpl(singlePredicateBuilder);
	}
}
