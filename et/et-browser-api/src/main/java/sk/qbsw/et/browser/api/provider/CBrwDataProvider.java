package sk.qbsw.et.browser.api.provider;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.api.mapping.CBrwEntityMapping;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.request.IBrwRequest;
import sk.qbsw.et.browser.client.request.IFilterRequest;
import sk.qbsw.et.browser.core.dto.IBrwDto;
import sk.qbsw.et.browser.core.exception.CBrwBusinessException;
import sk.qbsw.et.browser.core.exception.CBrwUndefinedBrowserMappingException;
import sk.qbsw.et.browser.core.model.CJoinDescriptor;

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
	 * @see sk.qbsw.et.browser.core.provider.IBrwDataProvider#getData(sk.qbsw.et.browser.client.request.IBrwRequest)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> IBrwDto<PK, T> getData (IBrwRequest<F> request) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(request.getBrowserCode());
		List<CJoinDescriptor<?>> joins = getJoinsByBrowserCode(request.getBrowserCode());

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		Pageable pageable = dataConverter.convertSortingCriteriaAndPagingToPageable(request.getSortingCriteria(), request.getPaging(), entityMapping);

		return (IBrwDto<PK, T>) serviceFactory.getBrwService(request.getBrowserCode()).findAll(predicate, pageable, joins.toArray(new CJoinDescriptor[joins.size()]));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#count(sk.qbsw.et.browser.client.request.IBrwRequest)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public <F extends IFilterable> long count (IBrwRequest<F> request) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(request.getBrowserCode());

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		return serviceFactory.getBrwService(request.getBrowserCode()).count(predicate);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.provider.IBrwDataProvider#getData(sk.qbsw.et.browser.client.request.IFilterRequest)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public <F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> List<T> getData (IFilterRequest<F> request) throws CBrwBusinessException
	{
		CBrwEntityMapping<F> entityMapping = (CBrwEntityMapping<F>) getEntityMappingByBrowserCode(request.getBrowserCode());
		List<CJoinDescriptor<?>> joins = getJoinsByBrowserCode(request.getBrowserCode());

		Predicate predicate = dataConverter.convertFilterCriteriaToPredicate(request.getFilterCriteria(), entityMapping);
		Sort sort = dataConverter.convertSortingCriteriaToSort(request.getSortingCriteria(), entityMapping);

		return (List<T>) serviceFactory.getBrwFilterService(request.getBrowserCode()).findAll(predicate, sort, joins.toArray(new CJoinDescriptor[joins.size()]));
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
	public void setMapping (Map<String, CBrwEntityMapping<? extends IFilterable>> mapping)
	{
		this.mapping = mapping;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#setDataConverter(sk.qbsw.et.browser.api.provider.IBrwDataConverter)
	 */
	public void setDataConverter (IBrwDataConverter dataConverter)
	{
		this.dataConverter = dataConverter;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.api.provider.IBrwDataProvider#setServiceFactory(sk.qbsw.et.browser.api.provider.IBrwServiceFactory)
	 */
	public void setServiceFactory (IBrwServiceFactory serviceFactory)
	{
		this.serviceFactory = serviceFactory;
	}
}
