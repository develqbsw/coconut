package sk.qbsw.et.rquery.brw.binding;

import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.request.CountDataRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.DataRequestBody;
import sk.qbsw.et.rquery.brw.client.model.request.PageableDataRequestBody;
import sk.qbsw.et.rquery.client.model.PageableData;

import java.io.Serializable;
import java.util.List;

/**
 * The data binder.
 *
 * @param <F> the filterable type
 * @param <K> the primary key type
 * @param <E> the entity type
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
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
	 * Find data list.
	 *
	 * @param request the request
	 * @param distinct the distinct
	 * @return the list
	 */
	List<E> findData (DataRequestBody<F> request, boolean distinct);

	/**
	 * Find pageable data pageable data.
	 *
	 * @param request the request
	 * @param distinct the distinct
	 * @return the pageable data
	 */
	PageableData<E> findPageableData (PageableDataRequestBody<F> request, boolean distinct);

	/**
	 * Count data long.
	 *
	 * @param request the request
	 * @param distinct the distinct
	 * @return the long
	 */
	long countData (CountDataRequestBody<F> request, boolean distinct);

}
