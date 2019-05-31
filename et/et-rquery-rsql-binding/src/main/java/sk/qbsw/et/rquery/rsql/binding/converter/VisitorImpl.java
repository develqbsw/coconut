package sk.qbsw.et.rquery.rsql.binding.converter;

import com.querydsl.core.types.Predicate;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.rsql.binding.mapper.FilterableMapper;

/**
 * The visitor implementation.
 *
 * @param <C> the type parameter
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class VisitorImpl<C extends CoreFilterable> implements RSQLVisitor<Predicate, Void>
{
	private final NodesConverter converter;

	private final EntityConfiguration<C> configuration;

	private final FilterableMapper<C> mapper;

	/**
	 * Instantiates a new Visitor.
	 *
	 * @param converter the converter
	 * @param configuration the configuration
	 * @param mapper the mapper
	 */
	public VisitorImpl (NodesConverter converter, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		this.converter = converter;
		this.configuration = configuration;
		this.mapper = mapper;
	}

	@Override
	public Predicate visit (AndNode node, Void param)
	{
		return converter.createPredicate(node, configuration, mapper);
	}

	@Override
	public Predicate visit (OrNode node, Void param)
	{
		return converter.createPredicate(node, configuration, mapper);
	}

	@Override
	public Predicate visit (ComparisonNode node, Void params)
	{
		return converter.createPredicate(node, configuration, mapper);
	}
}
