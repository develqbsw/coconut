package sk.qbsw.et.rquery.brw.api.provider;

import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.request.BrowserRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.CountRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.FilterRequestBody;
import sk.qbsw.et.rquery.brw.client.model.response.BrowserData;

import java.io.Serializable;
import java.util.List;

/**
 * The default data provider implementation.
 *
 * @param <F> the filterable type
 * @param <K> the primary key type
 * @param <E> the entity type
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
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
	 * Find browser data c brw dto.
	 *
	 * @param request the request
	 * @return the c brw dto
	 */
	BrowserData<E> findBrowserData (BrowserRequestBody<F> request);

	/**
	 * Count data long.
	 *
	 * @param request the request
	 * @return the long
	 */
	long countData (CountRequestBody<F> request);

	/**
	 * Find filtered data list.
	 *
	 * @param request the request
	 * @return the list
	 */
	List<E> findFilteredData (FilterRequestBody<F> request);

	/**
	 * Find distinct browser data c brw dto.
	 *
	 * @param request the request
	 * @return the c brw dto
	 */
	BrowserData<E> findDistinctBrowserData (BrowserRequestBody<F> request);

	/**
	 * Count distinct data long.
	 *
	 * @param request the request
	 * @return the long
	 */
	long countDistinctData (CountRequestBody<F> request);

	/**
	 * Find distinct filtered data list.
	 *
	 * @param request the request
	 * @return the list
	 */
	List<E> findDistinctFilteredData (FilterRequestBody<F> request);
}
