package sk.qbsw.et.browser.api.provider.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.SimpleExpression;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.api.provider.IBrwDataProvider;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.request.IBrwRequest;
import sk.qbsw.et.browser.client.request.IFilterRequest;
import sk.qbsw.et.browser.core.dto.IBrwDto;
import sk.qbsw.et.browser.core.exception.CBrwUndefinedVariableMappingException;
import sk.qbsw.et.browser.core.model.CJoinDescriptor;
import sk.qbsw.et.browser.core.service.IBrwService;

/**
 * The browser data provider.
 *
 * @param <F> the filterable
 * @param <PK> the pk
 * @param <T> the entity
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public abstract class ABrwDataProvider<F extends IFilterable, PK extends Serializable, T extends IEntity<PK>> implements IBrwDataProvider<F, PK, T>
{
	/** The expressions mapping. */
	private final Map<F, SimpleExpression<?>> expressionsMapping = new HashMap<>();

	/** The properties mapping. */
	private final Map<F, String> propertiesMapping = new HashMap<>();

	/** The join descriptors. */
	private final List<CJoinDescriptor<?>> joinDescriptors = new ArrayList<>();

	/** The brw service. */
	@Autowired
	private IBrwService<PK, T> brwService;

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.provider.IBrwDataProvider#getData(sk.qbsw.et.browser.client.request.IBrwRequest)
	 */
	@Override
	public IBrwDto<PK, T> getData (IBrwRequest<F> request) throws CBrwUndefinedVariableMappingException
	{
		Predicate predicate = CBrwDataProviderHelper.convertFilterCriteriaToPredicate(request.getFilterCriteria(), expressionsMapping);
		Pageable pageable = CBrwDataProviderHelper.convertSortingCriteriaAndPagingToPageable(request.getSortingCriteria(), request.getPaging(), propertiesMapping);

		return brwService.findAll(predicate, pageable, joinDescriptors.toArray(new CJoinDescriptor[joinDescriptors.size()]));
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.provider.IBrwDataProvider#getData(sk.qbsw.et.browser.client.request.IFilterRequest)
	 */
	@Override
	public List<T> getData (IFilterRequest<F> request) throws CBrwUndefinedVariableMappingException
	{
		Predicate predicate = CBrwDataProviderHelper.convertFilterCriteriaToPredicate(request.getFilterCriteria(), expressionsMapping);
		Sort sort = CBrwDataProviderHelper.convertSortingCriteriaToSort(request.getSortingCriteria(), propertiesMapping);

		return brwService.findAll(predicate, sort);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.provider.IBrwDataProvider#initializeMapping()
	 */
	@Override
	@PostConstruct
	public abstract void initializeMapping ();

	/**
	 * Adds the mappings.
	 *
	 * @param builder the builder
	 */
	protected void addMappings (CBrwDataMappingBuilder<F, PK, T> builder)
	{
		this.expressionsMapping.putAll(builder.buildExpressionMapping());
		this.propertiesMapping.putAll(builder.buildPropertiesMapping());
		this.joinDescriptors.addAll(builder.buildJoinDescriptors());
	}
}
