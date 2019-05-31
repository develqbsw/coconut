package sk.qbsw.et.rquery.rsql.binding.converter;

import com.querydsl.core.types.Predicate;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.rsql.binding.mapper.FilterableMapper;

/**
 * The nodes converter.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface NodesConverter
{
	/**
	 * Create predicate predicate.
	 *
	 * @param <C> the filterable type
	 * @param node the node
	 * @param configuration the configuration
	 * @param mapper the mapper
	 * @return the predicate
	 */
	<C extends CoreFilterable> Predicate createPredicate (Node node, EntityConfiguration<C> configuration, FilterableMapper<C> mapper);

	/**
	 * Create predicate predicate.
	 *
	 * @param <C> the filterable type
	 * @param logicalNode the logical node
	 * @param configuration the configuration
	 * @param mapper the mapper
	 * @return the predicate
	 */
	<C extends CoreFilterable> Predicate createPredicate (LogicalNode logicalNode, EntityConfiguration<C> configuration, FilterableMapper<C> mapper);

	/**
	 * Create predicate predicate.
	 *
	 * @param <C> the filterable type
	 * @param comparisonNode the comparison node
	 * @param configuration the configuration
	 * @param mapper the mapper
	 * @return the predicate
	 */
	<C extends CoreFilterable> Predicate createPredicate (ComparisonNode comparisonNode, EntityConfiguration<C> configuration, FilterableMapper<C> mapper);
}
