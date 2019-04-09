package sk.qbsw.et.rquery.rsql.binding.converter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreOperator;
import sk.qbsw.et.rquery.core.predicate.ComparisonPredicateBuilder;
import sk.qbsw.et.rquery.rsql.binding.mapper.FilterableMapper;
import sk.qbsw.et.rquery.rsql.binding.mapper.OperatorMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The rsql nodes converter implementation.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class NodesConverterImpl implements NodesConverter
{
	private final ComparisonPredicateBuilder comparisonPredicateBuilder;

	private final OperatorMapper operatorMapper;

	/**
	 * Instantiates a new Rsql nodes converter.
	 *
	 * @param comparisonPredicateBuilder the comparison predicate builder
	 * @param operatorMapper the operator mapper
	 */
	public NodesConverterImpl (ComparisonPredicateBuilder comparisonPredicateBuilder, OperatorMapper operatorMapper)
	{
		this.comparisonPredicateBuilder = comparisonPredicateBuilder;
		this.operatorMapper = operatorMapper;
	}

	@Override
	public <C extends CoreFilterable> Predicate createPredicate (Node node, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		if (node instanceof LogicalNode)
		{
			return createPredicate((LogicalNode) node, configuration, mapper);
		}
		if (node instanceof ComparisonNode)
		{
			return createPredicate((ComparisonNode) node, configuration, mapper);
		}
		return null;
	}

	@Override
	public <C extends CoreFilterable> Predicate createPredicate (LogicalNode logicalNode, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		List<Predicate> predicates = logicalNode.getChildren().stream().map(node -> createPredicate(node, configuration, mapper)).filter(Objects::nonNull).collect(Collectors.toList());

		BooleanBuilder predicateBuilder = new BooleanBuilder();
		if (logicalNode.getOperator() == LogicalOperator.AND)
		{
			for (Predicate predicate : predicates)
			{
				predicateBuilder.and(predicate);
			}
		}
		else if (logicalNode.getOperator() == LogicalOperator.OR)
		{
			for (Predicate predicate : predicates)
			{
				predicateBuilder.or(predicate);
			}
		}

		return predicateBuilder;
	}

	@Override
	public <C extends CoreFilterable> Predicate createPredicate (ComparisonNode comparisonNode, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		C property = mapper.mapNormalizedSelectorToCoreFilterable(comparisonNode.getSelector());
		CoreOperator operator = operatorMapper.mapToCoreOperator(comparisonNode.getOperator());

		return comparisonPredicateBuilder.buildPredicate(property, comparisonNode.getArguments(), operator, configuration);
	}
}
