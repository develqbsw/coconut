package sk.qbsw.et.rquery.rsql.binding;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sk.qbsw.et.rquery.client.model.PageableData;

import java.io.Serializable;
import java.util.List;

/**
 * The data binder.
 *
 * @param <K> the primary key type
 * @param <E> the entity type
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface DataBinder<K extends Serializable, E extends Serializable>
{
	/**
	 * Find one e.
	 *
	 * @param id the id
	 * @return the e
	 */
	E findOne (K id);

	/**
	 * Find filtered data list.
	 *
	 * @param query the query
	 * @param sort the sort
	 * @param distinct the distinct
	 * @return the list
	 */
	List<E> findData (String query, Sort sort, boolean distinct);

	/**
	 * Find pageable data pageable data.
	 *
	 * @param query the query
	 * @param pageable the pageable
	 * @param distinct the distinct
	 * @return the pageable data
	 */
	PageableData<E> findPageableData (String query, Pageable pageable, boolean distinct);

	/**
	 * Count data long.
	 *
	 * @param query the query
	 * @param distinct the distinct
	 * @return the long
	 */
	long countData (String query, boolean distinct);
}
