package sk.qbsw.et.browser.api.mapping;

import com.querydsl.core.types.dsl.SimpleExpression;

import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.et.browser.client.model.IFilterable;

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
public class CBrwEntityMappingBuilder<F extends IFilterable>
{
	/** The entity mapping. */
	private final CBrwEntityMapping<F> entityMapping = new CBrwEntityMapping<>();

	/**
	 * Adds the property mapping.
	 *
	 * @param property the property
	 * @param expression the expression
	 * @param propertyName the property name
	 * @return the c brw entity mapping builder
	 */
	public CBrwEntityMappingBuilder<F> addPropertyMapping (F property, SimpleExpression<?> expression, String propertyName)
	{
		entityMapping.putPair(property, new CBrwEntityPropertyIdentityPair(expression, propertyName));
		return this;
	}

	/**
	 * Adds the join.
	 *
	 * @param join the join
	 * @return the c brw entity mapping builder
	 */
	public CBrwEntityMappingBuilder<F> addJoin (CJoinDescriptor<?> join)
	{
		entityMapping.addJoin(join);
		return this;
	}

	/**
	 * Builds the entity mapping.
	 *
	 * @return the c brw entity mapping
	 */
	public CBrwEntityMapping<F> buildEntityMapping ()
	{
		return entityMapping;
	}
}
