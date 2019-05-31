package sk.qbsw.et.rquery.brw.binding;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sk.qbsw.core.persistence.dao.support.IFetchCapableQueryDslJpaRepository;
import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.et.rquery.brw.binding.converter.FilterCriteriaConverter;
import sk.qbsw.et.rquery.brw.binding.converter.SortingPagingCriteriaConverter;
import sk.qbsw.et.rquery.brw.binding.mapper.FilterableMapper;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.request.CountDataRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.DataRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.PageableDataRequestBody;
import sk.qbsw.et.rquery.client.model.PageableData;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;

import java.io.Serializable;
import java.util.List;

/**
 * The data binding implementation.
 *
 * @param <F> the filterable type
 * @param <C> the core filterable type
 * @param <K> the primary key type
 * @param <E> the entity type
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class DataBinderImpl<F extends Filterable, C extends CoreFilterable, K extends Serializable, E extends Serializable> implements DataBinder<F, K, E>
{
	private final IFetchCapableQueryDslJpaRepository<E, K> repository;

	private final FilterCriteriaConverter filterCriteriaConverter;
	private final SortingPagingCriteriaConverter sortingPagingCriteriaConverter;

	private final EntityConfiguration<C> configuration;
	private final FilterableMapper<F, C> filterableMapper;

	/**
	 * Instantiates a new Data provider.
	 *
	 * @param repository the repository
	 * @param filterCriteriaConverter the filter criteria converter
	 * @param sortingPagingCriteriaConverter the sorting paging criteria converter
	 * @param configuration the configuration
	 * @param filterableMapper the filterable mapper
	 */
	public DataBinderImpl (IFetchCapableQueryDslJpaRepository<E, K> repository, FilterCriteriaConverter filterCriteriaConverter, SortingPagingCriteriaConverter sortingPagingCriteriaConverter, EntityConfiguration<C> configuration, FilterableMapper<F, C> filterableMapper)
	{
		this.repository = repository;
		this.filterCriteriaConverter = filterCriteriaConverter;
		this.sortingPagingCriteriaConverter = sortingPagingCriteriaConverter;
		this.configuration = configuration;
		this.filterableMapper = filterableMapper;
	}

	@Override
	public E findOne (K id)
	{
		CJoinDescriptor[] joinDescriptors = configuration.getJoins().toArray(new CJoinDescriptor[0]);

		return repository.findOne(id, joinDescriptors);
	}

	@Override
	public List<E> findData (DataRequestBody<F> request, boolean distinct)
	{
		Predicate predicate = filterCriteriaConverter.convertToPredicate(request.getFilterCriteria(), configuration, filterableMapper);
		Sort sort = sortingPagingCriteriaConverter.convertToSort(request.getSortingCriteria(), configuration, filterableMapper);
		CJoinDescriptor[] joinDescriptors = configuration.getJoins().toArray(new CJoinDescriptor[0]);

		return repository.findAll(distinct, predicate, sort, joinDescriptors);
	}

	@Override
	public PageableData<E> findPageableData (PageableDataRequestBody<F> request, boolean distinct)
	{
		Predicate predicate = filterCriteriaConverter.convertToPredicate(request.getFilterCriteria(), configuration, filterableMapper);
		Pageable pageable = sortingPagingCriteriaConverter.convertToPageable(request.getSortingCriteria(), request.getPaging(), configuration, filterableMapper);
		CJoinDescriptor[] joinDescriptors = configuration.getJoins().toArray(new CJoinDescriptor[0]);

		Page<E> result = repository.findAll(distinct, predicate, pageable, joinDescriptors);
		return new PageableData<>(result.getContent(), result.getTotalElements());
	}

	@Override
	public long countData (CountDataRequestBody<F> request, boolean distinct)
	{
		Predicate predicate = filterCriteriaConverter.convertToPredicate(request.getFilterCriteria(), configuration, filterableMapper);
		CJoinDescriptor[] joinDescriptors = configuration.getJoins().toArray(new CJoinDescriptor[0]);

		return repository.count(distinct, predicate, joinDescriptors);
	}
}
