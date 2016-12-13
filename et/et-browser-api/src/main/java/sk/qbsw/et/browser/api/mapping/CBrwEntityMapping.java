package sk.qbsw.et.browser.api.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.querydsl.core.types.dsl.SimpleExpression;

import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.et.browser.client.model.IFilterable;

/**
 * The entity mapping.
 *
 * @param <F> the filterable
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.1
 * @since 1.16.1
 */
public class CBrwEntityMapping<F extends IFilterable>
{
	/** The mapping. */
	private final Map<F, CBrwEntityPropertyExpression> mapping = new HashMap<>();

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
	public CBrwEntityMapping (Map<F, CBrwEntityPropertyExpression> mapping, List<CJoinDescriptor<?>> joins)
	{
		this.mapping.putAll(mapping);
		this.joins.addAll(joins);
	}

	/**
	 * Put expression.
	 *
	 * @param property the property
	 * @param expressionPropertyPair the expression property pair
	 */
	public void putExpression (F property, CBrwEntityPropertyExpression expressionPropertyPair)
	{
		mapping.put(property, expressionPropertyPair);
	}

	/**
	 * Put expression.
	 *
	 * @param property the property
	 * @param expression the expression
	 */
	public void putExpression (F property, SimpleExpression<?> expression)
	{
		mapping.put(property, new CBrwEntityPropertyExpression(expression));
	}

	/**
	 * Gets the expression.
	 *
	 * @param property the property
	 * @return the expression
	 */
	public CBrwEntityPropertyExpression getExpression (F property)
	{
		return mapping.get(property);
	}

	/**
	 * Contains expression.
	 *
	 * @param property the property
	 * @return true, if successful
	 */
	public boolean containsExpression (F property)
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
