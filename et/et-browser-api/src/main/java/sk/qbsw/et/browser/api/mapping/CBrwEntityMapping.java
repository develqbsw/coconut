package sk.qbsw.et.browser.api.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.querydsl.core.types.dsl.SimpleExpression;

import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.core.model.CJoinDescriptor;

/**
 * The entity mapping.
 *
 * @param <F> the filterable
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwEntityMapping<F extends IFilterable>
{
	/** The mapping. */
	private final Map<F, CBrwEntityPropertyIdentityPair> mapping = new HashMap<>();

	/** The joins. */
	private final List<CJoinDescriptor<?>> joins = new ArrayList<>();

	/**
	 * Instantiates a new c brw entity mapping.
	 */
	public CBrwEntityMapping ()
	{
		//default constructor
	}

	/**
	 * Instantiates a new c brw entity mapping.
	 *
	 * @param mapping the mapping
	 * @param joins the joins
	 */
	public CBrwEntityMapping (Map<F, CBrwEntityPropertyIdentityPair> mapping, List<CJoinDescriptor<?>> joins)
	{
		this.mapping.putAll(mapping);
		this.joins.addAll(joins);
	}

	/**
	 * Put pair.
	 *
	 * @param property the property
	 * @param expressionPropertyPair the expression property pair
	 */
	public void putPair (F property, CBrwEntityPropertyIdentityPair expressionPropertyPair)
	{
		mapping.put(property, expressionPropertyPair);
	}

	/**
	 * Put pair.
	 *
	 * @param property the property
	 * @param expression the expression
	 * @param propertyName the property name
	 */
	public void putPair (F property, SimpleExpression<?> expression, String propertyName)
	{
		mapping.put(property, new CBrwEntityPropertyIdentityPair(expression, propertyName));
	}

	/**
	 * Gets the pair.
	 *
	 * @param property the property
	 * @return the pair
	 */
	public CBrwEntityPropertyIdentityPair getPair (F property)
	{
		return mapping.get(property);
	}

	/**
	 * Contains pair.
	 *
	 * @param property the property
	 * @return true, if successful
	 */
	public boolean containsPair (F property)
	{
		return mapping.containsKey(property);
	}

	/**
	 * Adds the join.
	 *
	 * @param join the join
	 */
	public void addJoin (CJoinDescriptor<?> join)
	{
		joins.add(join);
	}

	/**
	 * Contains join.
	 *
	 * @param join the join
	 * @return true, if successful
	 */
	public boolean containsJoin (CJoinDescriptor<?> join)
	{
		return joins.contains(join);
	}

	/**
	 * Gets the joins.
	 *
	 * @return the joins
	 */
	public List<CJoinDescriptor<?>> getJoins ()
	{
		return joins;
	}
}
