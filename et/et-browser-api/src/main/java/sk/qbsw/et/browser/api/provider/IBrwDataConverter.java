package sk.qbsw.et.browser.api.provider;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;

import sk.qbsw.et.browser.api.mapping.CBrwEntityMapping;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CPagingTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject;
import sk.qbsw.et.browser.core.exception.CBrwUndefinedEntityMappingException;

/**
 * The data provider converter from client to domain objects.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IBrwDataConverter
{
	/**
	 * Convert filter criteria to predicate.
	 *
	 * @param <F> the filterable
	 * @param filterCriteria the filter criteria
	 * @param entityMapping the mapping
	 * @return the predicate
	 * @throws CBrwUndefinedEntityMappingException the c brw undefined variable mapping exception
	 */
	<F extends IFilterable> Predicate convertFilterCriteriaToPredicate (CFilterCriteriaTransferObject<F> filterCriteria, CBrwEntityMapping<F> entityMapping) throws CBrwUndefinedEntityMappingException;

	/**
	 * Convert sorting criteria and paging to pageable.
	 *
	 * @param <F> the filterable
	 * @param sortingCriteria the sorting criteria
	 * @param paging the paging
	 * @param entityMapping the entity mapping
	 * @return the pageable
	 * @throws CBrwUndefinedEntityMappingException the c brw undefined variable mapping exception
	 */
	<F extends IFilterable> Pageable convertSortingCriteriaAndPagingToPageable (CSortingCriteriaTransferObject<F> sortingCriteria, CPagingTransferObject paging, CBrwEntityMapping<F> entityMapping) throws CBrwUndefinedEntityMappingException;

	/**
	 * Convert sorting criteria to sort.
	 *
	 * @param <F> the filterable
	 * @param sortingCriteria the sorting criteria
	 * @param entityMapping the entity mapping
	 * @return the sort
	 * @throws CBrwUndefinedEntityMappingException the c brw undefined variable mapping exception
	 */
	<F extends IFilterable> Sort convertSortingCriteriaToSort (CSortingCriteriaTransferObject<F> sortingCriteria, CBrwEntityMapping<F> entityMapping) throws CBrwUndefinedEntityMappingException;
}
