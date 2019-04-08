package sk.qbsw.et.rquery.brw.binding;

import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.request.BrowserRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.CountRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.FilterRequestBody;
import sk.qbsw.et.rquery.brw.client.model.response.BrowserData;

import java.io.Serializable;
import java.util.List;

/**
 * The data binder.
 *
 * @param <F> the filterable type
 * @param <K> the primary key type
 * @param <E> the entity type
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface DataBinder<F extends Filterable, K extends Serializable, E extends Serializable>
{
	/**
	 * Find one e.
	 *
	 * @param id the id
	 * @return the e
	 */
	E findOne (K id);

	/**
	 * Find browser data browser data.
	 *
	 * @param request the request
	 * @param distinct the distinct
	 * @return the browser data
	 */
	BrowserData<E> findBrowserData (BrowserRequestBody<F> request, boolean distinct);

	/**
	 * Count data long.
	 *
	 * @param request the request
	 * @param distinct the distinct
	 * @return the long
	 */
	long countData (CountRequestBody<F> request, boolean distinct);

	/**
	 * Find filtered data list.
	 *
	 * @param request the request
	 * @param distinct the distinct
	 * @return the list
	 */
	List<E> findFilteredData (FilterRequestBody<F> request, boolean distinct);
}
