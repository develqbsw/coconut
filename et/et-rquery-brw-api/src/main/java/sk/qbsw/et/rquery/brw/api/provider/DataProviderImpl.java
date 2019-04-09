package sk.qbsw.et.rquery.brw.api.provider;

import sk.qbsw.et.rquery.brw.binding.DataBinder;
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
public class DataProviderImpl<F extends Filterable, K extends Serializable, E extends Serializable> implements DataProvider<F, K, E>
{
	private final DataBinder<F, K, E> dataBinder;

	/**
	 * Instantiates a new Data provider.
	 *
	 * @param dataBinder the data binder
	 */
	public DataProviderImpl (DataBinder<F, K, E> dataBinder)
	{
		this.dataBinder = dataBinder;
	}

	@Override
	public E findOne (K id)
	{
		return dataBinder.findOne(id);
	}

	@Override
	public List<E> findData (DataRequestBody<F> request)
	{
		return dataBinder.findData(request, false);
	}

	@Override
	public PageableData<E> findPageableData (PageableDataRequestBody<F> request)
	{
		return dataBinder.findPageableData(request, false);
	}

	@Override
	public long countData (CountDataRequestBody<F> request)
	{
		return dataBinder.countData(request, false);
	}

	@Override
	public List<E> findDistinctData (DataRequestBody<F> request)
	{
		return dataBinder.findData(request, true);
	}

	@Override
	public PageableData<E> findDistinctPageableData (PageableDataRequestBody<F> request)
	{
		return dataBinder.findPageableData(request, true);
	}

	@Override
	public long countDistinctData (CountDataRequestBody<F> request)
	{
		return dataBinder.countData(request, true);
	}
}
