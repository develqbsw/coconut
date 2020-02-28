package sk.qbsw.et.rquery.rsql.api.provider;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import sk.qbsw.et.rquery.client.model.PageableData;
import sk.qbsw.et.rquery.rsql.binding.DataBinder;
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
public class DataProviderImpl<K extends Serializable, E extends Serializable> implements DataProvider<K, E>
{
	private final DataBinder<K, E> dataBinder;

	/**
	 * Instantiates a new Data provider.
	 *
	 * @param dataBinder the data binder
	 */
	public DataProviderImpl (DataBinder<K, E> dataBinder)
	{
		this.dataBinder = dataBinder;
	}

	@Override
	public E findOne (K id)
	{
		return dataBinder.findOne(id);
	}

	@Override
	public List<E> findData (String query, Sort sort)
	{
		return dataBinder.findData(query, sort, false);
	}

	@Override
	public PageableData<E> findPageableData (String query, Pageable pageable)
	{
		return dataBinder.findPageableData(query, pageable, false);
	}

	@Override
	public PageableData<E> findPageableData (String query, OffsetPageable pageable)
	{
		return dataBinder.findPageableData(query, pageable, false);
	}

	@Override
	public long countData (String query)
	{
		return dataBinder.countData(query, false);
	}

	@Override
	public List<E> findDistinctData (String query, Sort sort)
	{
		return dataBinder.findData(query, sort, true);
	}

	@Override
	public PageableData<E> findDistinctPageableData (String query, Pageable pageable)
	{
		return dataBinder.findPageableData(query, pageable, true);
	}

	@Override
	public PageableData<E> findDistinctPageableData (String query, OffsetPageable pageable)
	{
		return dataBinder.findPageableData(query, pageable, true);
	}

	@Override
	public long countDistinctData (String query)
	{
		return dataBinder.countData(query, true);
	}
}
