package sk.qbsw.et.rquery.brw.binding.converter;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import sk.qbsw.et.rquery.brw.binding.mapper.FilterableMapper;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.criteria.Paging;
import sk.qbsw.et.rquery.brw.client.model.criteria.SortingCriteria;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;

/**
 * The default sorting and paging criteria converter.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface SortingPagingCriteriaConverter
{
	/**
	 * Convert to pageable pageable.
	 *
	 * @param <F> the property type
	 * @param <C> the core property type
	 * @param sortingCriteria the sorting criteria
	 * @param paging the paging
	 * @param mapping the mapping
	 * @param filterableMapper the filterable mapper
	 * @return the pageable
	 */
	<F extends Filterable, C extends CoreFilterable> Pageable convertToPageable (SortingCriteria<F> sortingCriteria, Paging paging, EntityConfiguration<C> mapping, FilterableMapper<F, C> filterableMapper);

	/**
	 * Convert to sort sort.
	 *
	 * @param <F> the property type
	 * @param <C> the core property type
	 * @param sortingCriteria the sorting criteria
	 * @param mapping the mapping
	 * @param filterableMapper the filterable mapper
	 * @return the sort
	 */
	<F extends Filterable, C extends CoreFilterable> Sort convertToSort (SortingCriteria<F> sortingCriteria, EntityConfiguration<C> mapping, FilterableMapper<F, C> filterableMapper);
}
