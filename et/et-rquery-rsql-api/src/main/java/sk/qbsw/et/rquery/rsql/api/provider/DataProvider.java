package sk.qbsw.et.rquery.rsql.api.provider;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import sk.qbsw.et.rquery.client.model.PageableData;
import sk.qbsw.et.rquery.rsql.binding.model.OffsetPageable;

/**
 * The default data provider implementation.
 *
 * @param <K> the primary key type
 * @param <E> the entity type
 * @author Tomas Lauro
 * @version 2.3.1
 * @since 2.2.0
 */
public interface DataProvider<K extends Serializable, E extends Serializable>
{
	/**
	 * Find one e.
	 *
	 * @param id the id
	 * @return the e
	 */
	E findOne (K id);

	/**
	 * Find data list.
	 *
	 * @param query the query
	 * @param sort the sort
	 * @return the list
	 */
	List<E> findData (String query, Sort sort);

	/**
	 * Find pageable data pageable data.
	 *
	 * @param query the query
	 * @param pageable the pageable
	 * @return the pageable data
	 */
	PageableData<E> findPageableData (String query, Pageable pageable);

	/**
	 * Find pageable data pageable data.
	 *
	 * @param query the query
	 * @param pageable the offset pageable
	 * @return the pageable data
	 */
	PageableData<E> findPageableData (String query, OffsetPageable pageable);

	/**
	 * Count data long.
	 *
	 * @param query the query
	 * @return the long
	 */
	long countData (String query);

	/**
	 * Find distinct data list.
	 *
	 * @param query the query
	 * @param sort the sort
	 * @return the list
	 */
	List<E> findDistinctData (String query, Sort sort);

	/**
	 * Find distinct pageable data pageable data.
	 *
	 * @param query the query
	 * @param pageable the pageable
	 * @return the pageable data
	 */
	PageableData<E> findDistinctPageableData (String query, Pageable pageable);

	/**
	 * Find distinct pageable data pageable data.
	 *
	 * @param query the query
	 * @param pageable the offset pageable
	 * @return the pageable data
	 */
	PageableData<E> findDistinctPageableData (String query, OffsetPageable pageable);

	/**
	 * Count distinct data long.
	 *
	 * @param query the query
	 * @return the long
	 */
	long countDistinctData (String query);
}
