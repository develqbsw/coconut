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
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#getData(java.lang.String, sk.qbsw.et.browser.client.model.request.CBrwRequest)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> CBrwDto<PK, T> getData (String browserCode, CBrwRequest<F> request) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(browserCode);
		List<CJoinDescriptor<?>> joins = getJoinsByBrowserCode(browserCode);

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		Pageable pageable = dataConverter.convertSortingCriteriaAndPagingToPageable(request.getSortingCriteria(), request.getPaging(), entityMapping);

		return (CBrwDto<PK, T>) serviceFactory.getBrwService(browserCode).findAll(predicate, pageable, joins.toArray(new CJoinDescriptor[joins.size()]));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#count(java.lang.String, sk.qbsw.et.browser.client.model.request.CBrwRequest)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public <F extends IFilterable> long count (String browserCode, CBrwRequest<F> request) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(browserCode);

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		return serviceFactory.getBrwService(browserCode).count(predicate);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#getData(java.lang.String, sk.qbsw.et.browser.client.model.request.CFilterRequest)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> List<T> getData (String browserCode, CFilterRequest<F> request) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(browserCode);
		List<CJoinDescriptor<?>> joins = getJoinsByBrowserCode(browserCode);

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		Sort sort = dataConverter.convertSortingCriteriaToSort(request.getSortingCriteria(), entityMapping);

		return (List<T>) serviceFactory.getBrwFilterService(browserCode).findAll(predicate, sort, joins.toArray(new CJoinDescriptor[joins.size()]));
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
