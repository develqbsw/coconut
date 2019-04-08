package sk.qbsw.et.rquery.brw.api.provider;

import sk.qbsw.et.rquery.brw.binding.DataBinder;
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
	public BrowserData<E> findBrowserData (BrowserRequestBody<F> request)
	{
		return dataBinder.findBrowserData(request, false);
	}

	@Override
	public long countData (CountRequestBody<F> request)
	{
		return dataBinder.countData(request, false);
	}

	@Override
	public List<E> findFilteredData (FilterRequestBody<F> request)
	{
		return dataBinder.findFilteredData(request, false);
	}

	@Override
	public BrowserData<E> findDistinctBrowserData (BrowserRequestBody<F> request)
	{
		return dataBinder.findBrowserData(request, true);
	}

	@Override
	public long countDistinctData (CountRequestBody<F> request)
	{
		return dataBinder.countData(request, true);
	}

	@Override
	public List<E> findDistinctFilteredData (FilterRequestBody<F> request)
	{
		return dataBinder.findFilteredData(request, true);
	}
}
