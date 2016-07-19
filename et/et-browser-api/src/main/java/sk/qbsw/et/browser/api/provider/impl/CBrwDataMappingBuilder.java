package sk.qbsw.et.browser.api.provider.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.SimpleExpression;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.core.model.CJoinDescriptor;

/**
 * The browser data mapper builder.
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
@Component
public class CBrwDataMappingBuilder<F extends IFilterable, PK extends Serializable, T extends IEntity<PK>>
{
	/** The expressions mapping. */
	private final Map<F, SimpleExpression<?>> expressionsMapping = new HashMap<>();

	/** The properties mapping. */
	private final Map<F, String> propertiesMapping = new HashMap<>();

	/** The join descriptors. */
	private final List<CJoinDescriptor<?>> joinDescriptors = new ArrayList<>();

	/**
	 * Adds the mapping.
	 *
	 * @param attribute the attribute
	 * @param attributePath the attribute path
	 * @param property the property
	 * @return the c brw data mapping builder
	 */
	public CBrwDataMappingBuilder<F, PK, T> addMapping (F attribute, SimpleExpression<?> attributePath, String property)
	{
		expressionsMapping.put(attribute, attributePath);
		propertiesMapping.put(attribute, property);
		return this;
	}

	/**
	 * Adds the join descriptor.
	 *
	 * @param joinDescriptor the join descriptor
	 * @return the c brw data mapping builder
	 */
	public CBrwDataMappingBuilder<F, PK, T> addJoinDescriptor (CJoinDescriptor<?> joinDescriptor)
	{
		joinDescriptors.add(joinDescriptor);
		return this;
	}

	/**
	 * Builds the expression mapping.
	 *
	 * @return the map
	 */
	public Map<F, SimpleExpression<?>> buildExpressionMapping ()
	{
		return expressionsMapping;
	}

	/**
	 * Builds the properties mapping.
	 *
	 * @return the map
	 */
	public Map<F, String> buildPropertiesMapping ()
	{
		return propertiesMapping;
	}

	/**
	 * Builds the join descriptors.
	 *
	 * @return the list
	 */
	public List<CJoinDescriptor<?>> buildJoinDescriptors ()
	{
		return joinDescriptors;
	}
}
