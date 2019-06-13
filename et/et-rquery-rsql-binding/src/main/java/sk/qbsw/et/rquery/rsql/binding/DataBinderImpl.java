package sk.qbsw.et.rquery.rsql.binding;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sk.qbsw.core.persistence.dao.support.IFetchCapableQueryDslJpaRepository;
import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.et.rquery.client.model.PageableData;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.rsql.binding.converter.PageableConverter;
import sk.qbsw.et.rquery.rsql.binding.mapper.FilterableMapper;

import java.io.Serializable;
import java.util.List;

/**
 * The data binding implementation.
 *
 * @param <C> the core filterable type
 * @param <K> the primary key type
 * @param <E> the entity type
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class DataBinderImpl<C extends CoreFilterable, K extends Serializable, E extends Serializable> implements DataBinder<K, E>
{
	private final IFetchCapableQueryDslJpaRepository<E, K> repository;

	private final RSQLParser parser;
	private final RSQLVisitor<Predicate, Void> visitor;
	private final PageableConverter pageableConverter;

	private final EntityConfiguration<C> configuration;
	private final FilterableMapper<C> mapper;

	/**
	 * Instantiates a new Data binder.
	 *
	 * @param repository the repository
	 * @param parser the parser
	 * @param visitor the visitor
	 * @param pageableConverter the pageable converter
	 * @param configuration the configuration
	 * @param mapper the mapper
	 */
	public DataBinderImpl (IFetchCapableQueryDslJpaRepository<E, K> repository, RSQLParser parser, RSQLVisitor<Predicate, Void> visitor, PageableConverter pageableConverter, EntityConfiguration<C> configuration, FilterableMapper<C> mapper)
	{
		this.repository = repository;
		this.parser = parser;
		this.visitor = visitor;
		this.pageableConverter = pageableConverter;
		this.configuration = configuration;
		this.mapper = mapper;
	}

	@Override
	public E findOne (K id)
	{
		CJoinDescriptor[] joinDescriptors = configuration.getJoins().toArray(new CJoinDescriptor[0]);

		return repository.findOne(id, joinDescriptors);
	}

	@Override
	public List<E> findData (String query, Sort sort, boolean distinct)
	{
		Predicate predicate = createPredicate(query);
		Sort convertedSort = pageableConverter.convertToSort(sort, configuration, mapper);

		CJoinDescriptor[] joinDescriptors = configuration.getJoins().toArray(new CJoinDescriptor[0]);

		return repository.findAll(distinct, predicate, convertedSort, joinDescriptors);
	}

	@Override
	public PageableData<E> findPageableData (String query, Pageable pageable, boolean distinct)
	{
		Predicate predicate = createPredicate(query);
		Pageable convertedPageable = pageableConverter.convertToPageable(pageable, configuration, mapper);

		CJoinDescriptor[] joinDescriptors = configuration.getJoins().toArray(new CJoinDescriptor[0]);

		Page<E> result = repository.findAll(distinct, predicate, convertedPageable, joinDescriptors);
		return new PageableData<>(result.getContent(), result.getTotalElements());
	}

	@Override
	public long countData (String query, boolean distinct)
	{
		Predicate predicate = createPredicate(query);

		CJoinDescriptor[] joinDescriptors = configuration.getJoins().toArray(new CJoinDescriptor[0]);

		return repository.count(distinct, predicate, joinDescriptors);
	}

	private Predicate createPredicate (String query)
	{
		if (query != null)
		{
			Node rootNode = parser.parse(query);
			return rootNode.accept(visitor);
		}
		else
		{
			return new BooleanBuilder();
		}
	}
}
