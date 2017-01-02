package sk.qbsw.et.browser.api.provider;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;

import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.api.mapping.CBrwEntityMapping;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.request.CBrwRequest;
import sk.qbsw.et.browser.client.model.request.CFilterRequest;
import sk.qbsw.et.browser.client.model.request.CFilterSortRequest;
import sk.qbsw.et.browser.core.dto.CBrwDto;
import sk.qbsw.et.browser.core.exception.CBrwBusinessException;
import sk.qbsw.et.browser.core.exception.CBrwUndefinedBrowserMappingException;

/**
 * The browser data provider.
 *
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwDataProvider implements IBrwDataProvider
{
	/** The mapping. */
	private Map<String, CBrwEntityMapping<? extends IFilterable>> mapping = new HashMap<>();

	/** The converter. */
	private IBrwDataConverter dataConverter;

	/** The service factory. */
	private IBrwServiceFactory serviceFactory;

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#getOne(java.lang.String, java.io.Serializable)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public <PK extends Serializable, T extends IEntity<PK>> T getOne (String browserCode, PK id) throws CBrwBusinessException
	{
		List<CJoinDescriptor<?>> joins = getJoinsByBrowserCode(browserCode);

		return (T) serviceFactory.getBrwService(browserCode).findOne(id, joins.toArray(new CJoinDescriptor[joins.size()]));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#getBrowserData(java.lang.String, sk.qbsw.et.browser.client.model.request.CBrwRequest)
	 */
	@Override
	public <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> CBrwDto<PK, T> getBrowserData (String browserCode, CBrwRequest<F> request) throws CBrwBusinessException
	{
		return getBrowserData(browserCode, request, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#countData(java.lang.String, sk.qbsw.et.browser.client.model.request.CFilterRequest)
	 */
	@Override
	public <F extends IFilterable> long countData (String browserCode, CFilterRequest<F> request) throws CBrwBusinessException
	{
		return countData(browserCode, request, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#getFilteredData(java.lang.String, sk.qbsw.et.browser.client.model.request.CFilterSortRequest)
	 */
	@Override
	public <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> List<T> getFilteredData (String browserCode, CFilterSortRequest<F> request) throws CBrwBusinessException
	{
		return getFilteredData(browserCode, request, false);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#getBrowserDistinctData(java.lang.String, sk.qbsw.et.browser.client.model.request.CBrwRequest)
	 */
	@Override
	public <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> CBrwDto<PK, T> getBrowserDistinctData (String browserCode, CBrwRequest<F> request) throws CBrwBusinessException
	{
		return getBrowserData(browserCode, request, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#countDistinctData(java.lang.String, sk.qbsw.et.browser.client.model.request.CFilterRequest)
	 */
	@Override
	public <F extends IFilterable> long countDistinctData (String browserCode, CFilterRequest<F> request) throws CBrwBusinessException
	{
		return countData(browserCode, request, true);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#getFilteredDistinctData(java.lang.String, sk.qbsw.et.browser.client.model.request.CFilterSortRequest)
	 */
	public <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> List<T> getFilteredDistinctData (String browserCode, CFilterSortRequest<F> request) throws CBrwBusinessException
	{
		return getFilteredData(browserCode, request, true);
	}

	/**
	 * Gets the browser data.
	 *
	 * @param <F> the generic type
	 * @param <PK> the generic type
	 * @param <T> the generic type
	 * @param browserCode the browser code
	 * @param request the request
	 * @param distinct the distinct
	 * @return the browser data
	 * @throws CBrwBusinessException the c brw business exception
	 */
	@SuppressWarnings ("unchecked")
	private <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> CBrwDto<PK, T> getBrowserData (String browserCode, CBrwRequest<F> request, boolean distinct) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(browserCode);
		List<CJoinDescriptor<?>> joins = getJoinsByBrowserCode(browserCode);

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		Pageable pageable = dataConverter.convertSortingCriteriaAndPagingToPageable(request.getSortingCriteria(), request.getPaging(), entityMapping);

		return (CBrwDto<PK, T>) serviceFactory.getBrwService(browserCode).findAll(distinct, predicate, pageable, joins.toArray(new CJoinDescriptor[joins.size()]));
	}

	/**
	 * Count data.
	 *
	 * @param <F> the generic type
	 * @param browserCode the browser code
	 * @param request the request
	 * @param distinct the distinct
	 * @return the long
	 * @throws CBrwBusinessException the c brw business exception
	 */
	@SuppressWarnings ("unchecked")
	private <F extends IFilterable> long countData (String browserCode, CFilterRequest<F> request, boolean distinct) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(browserCode);
		List<CJoinDescriptor<?>> joins = getJoinsByBrowserCode(browserCode);

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		return serviceFactory.getBrwService(browserCode).count(distinct, predicate, joins.toArray(new CJoinDescriptor[joins.size()]));
	}

	/**
	 * Gets the filtered data.
	 *
	 * @param <F> the generic type
	 * @param <PK> the generic type
	 * @param <T> the generic type
	 * @param browserCode the browser code
	 * @param request the request
	 * @param distinct the distinct
	 * @return the filtered data
	 * @throws CBrwBusinessException the c brw business exception
	 */
	@SuppressWarnings ("unchecked")
	private <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> List<T> getFilteredData (String browserCode, CFilterSortRequest<F> request, boolean distinct) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(browserCode);
		List<CJoinDescriptor<?>> joins = getJoinsByBrowserCode(browserCode);

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		Sort sort = dataConverter.convertSortingCriteriaToSort(request.getSortingCriteria(), entityMapping);

		return (List<T>) serviceFactory.getBrwFilterService(browserCode).findAll(distinct, predicate, sort, joins.toArray(new CJoinDescriptor[joins.size()]));
	}

	/**
	 * Gets the entity mapping by browser code.
	 *
	 * @param code the code
	 * @return the entity mapping by browser code
	 * @throws CBrwUndefinedBrowserMappingException the c brw undefined browser mapping exception
	 */
	private CBrwEntityMapping<? extends IFilterable> getEntityMappingByBrowserCode (String code) throws CBrwUndefinedBrowserMappingException
	{
		CBrwEntityMapping<? extends IFilterable> entityMapping = mapping.get(code);
		if (entityMapping != null)
		{
			return entityMapping;
		}
		else
		{
			throw new CBrwUndefinedBrowserMappingException("The mapping for browser code " + code + "not found");
		}
	}

	/**
	 * Gets the joins by browser code.
	 *
	 * @param code the code
	 * @return the joins by browser code
	 * @throws CBrwUndefinedBrowserMappingException the c brw undefined browser mapping exception
	 */
	private List<CJoinDescriptor<?>> getJoinsByBrowserCode (String code) throws CBrwUndefinedBrowserMappingException
	{
		CBrwEntityMapping<?> entityMapping = getEntityMappingByBrowserCode(code);
		return entityMapping.getJoins();
	}

	/**
	 * Sets the mapping.
	 *
	 * @param mapping the mapping
	 */
	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#setMapping(java.util.Map)
	 */
	@Override
	public void setMapping (Map<String, CBrwEntityMapping<? extends IFilterable>> mapping)
	{
		this.mapping = mapping;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#setDataConverter(sk.qbsw.et.browser.api.provider.IBrwDataConverter)
	 */
	@Override
	public void setDataConverter (IBrwDataConverter dataConverter)
	{
		this.dataConverter = dataConverter;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#setServiceFactory(sk.qbsw.et.browser.api.provider.IBrwServiceFactory)
	 */
	@Override
	public void setServiceFactory (IBrwServiceFactory serviceFactory)
	{
		this.serviceFactory = serviceFactory;
	}
}
