package sk.qbsw.et.rquery.core.configuration;

import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.et.rquery.core.configuration.expression.EntityPropertyExpression;
import sk.qbsw.et.rquery.core.model.CoreFilterable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The immutable entity configuration.
 *
 * @param <F> the filterable
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class EntityConfiguration<F extends CoreFilterable>
{
	private final Map<F, EntityPropertyExpression> mapping;

	private final List<CJoinDescriptor<?>> joins;

	/**
	 * Instantiates a new Entity configuration.
	 *
	 * @param mapping the mapping
	 * @param joins the joins
	 */
	public EntityConfiguration (Map<F, EntityPropertyExpression> mapping, List<CJoinDescriptor<?>> joins)
	{
		this.mapping = Collections.unmodifiableMap(mapping);
		this.joins = Collections.unmodifiableList(joins);
	}

	/**
	 * Gets expression.
	 *
	 * @param property the property
	 * @return the expression
	 */
	public EntityPropertyExpression getExpression (F property)
	{
		return mapping.get(property);
	}

	/**
	 * Contains expression boolean.
	 *
	 * @param property the property
	 * @return the boolean
	 */
	public boolean containsExpression (F property)
	{
		return mapping.containsKey(property);
	}

	/**
	 * Gets joins.
	 *
	 * @return the joins
	 */
	public List<CJoinDescriptor<?>> getJoins ()
	{
		return joins;
	}

	/**
	 * Contains join boolean.
	 *
	 * @param join the join
	 * @return the boolean
	 */
	public boolean containsJoin (CJoinDescriptor<?> join)
	{
		return joins.contains(join);
	}
}
