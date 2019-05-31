package sk.qbsw.et.rquery.rsql.binding.configuration;

import org.springframework.context.annotation.Bean;

import cz.jirutka.rsql.parser.RSQLParser;
import sk.qbsw.et.rquery.core.configuration.CoreConfiguration;
import sk.qbsw.et.rquery.core.predicate.ComparisonPredicateBuilder;
import sk.qbsw.et.rquery.rsql.binding.converter.NodesConverter;
import sk.qbsw.et.rquery.rsql.binding.converter.NodesConverterImpl;
import sk.qbsw.et.rquery.rsql.binding.converter.PageableConverter;
import sk.qbsw.et.rquery.rsql.binding.converter.PageableConverterImpl;
import sk.qbsw.et.rquery.rsql.binding.mapper.OperatorMapper;
import sk.qbsw.et.rquery.rsql.binding.mapper.OperatorMapperImpl;
import sk.qbsw.et.rquery.rsql.binding.operator.ExtendedRSQLOperators;

/**
 * The request query binding configuration.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class BindingConfiguration extends CoreConfiguration
{
	@Bean
	public RSQLParser rsqlParser ()
	{
		return new RSQLParser(ExtendedRSQLOperators.defaultOperators());
	}

	@Bean
	public OperatorMapper rsqlOperatorMapper ()
	{
		return new OperatorMapperImpl();
	}

	@Bean
	public NodesConverter rsqlNodesConverter (ComparisonPredicateBuilder comparisonPredicateBuilder, OperatorMapper rsqlOperatorMapper)
	{
		return new NodesConverterImpl(comparisonPredicateBuilder, rsqlOperatorMapper);
	}

	@Bean
	public PageableConverter rsqlPageableConverter ()
	{
		return new PageableConverterImpl();
	}
}
