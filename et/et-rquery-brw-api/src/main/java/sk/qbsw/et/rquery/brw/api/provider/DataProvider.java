package sk.qbsw.et.rquery.brw.api.provider;

import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.request.CountDataRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.DataRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.PageableDataRequestBody;
import sk.qbsw.et.rquery.client.model.PageableData;

import java.io.Serializable;
import java.util.List;

/**
 * The default data provider implementation.
 *
 * @param <F> the filterable type
 * @param <K> the primary key type
 * @param <E> the entity type
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface DataProvider<F extends Filterable, K extends Serializable, E extends Serializable>
{
	/**
	 * Find one t.
	 *
	 * @param id the id
	 * @return the t
	 */
	E findOne (K id);

	/**
	 * Find data list.
	 *
	 * @param request the request
	 * @return the list
	 */
	List<E> findData (DataRequestBody<F> request);

	/**
	 * Find pageable data pageable data.
	 *
	 * @param request the request
	 * @return the pageable data
	 */
	PageableData<E> findPageableData (PageableDataRequestBody<F> request);

	/**
	 * Count data long.
	 *
	 * @param request the request
	 * @return the long
	 */
	long countData (CountDataRequestBody<F> request);

	/**
	 * Find distinct data list.
	 *
	 * @param request the request
	 * @return the list
	 */
	List<E> findDistinctData (DataRequestBody<F> request);

	/**
	 * Find distinct pageable data pageable data.
	 *
	 * @param request the request
	 * @return the pageable data
	 */
	PageableData<E> findDistinctPageableData (PageableDataRequestBody<F> request);

	/**
	 * Count distinct data long.
	 *
	 * @param request the request
	 * @return the long
	 */
	long countDistinctData (CountDataRequestBody<F> request);
}
