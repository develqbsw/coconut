package sk.qbsw.core.persistence.dao.jpa.qdsl;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.SimpleExpression;

import sk.qbsw.core.persistence.dao.IDao;
import sk.qbsw.core.persistence.dao.jpa.AJpaDao;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The query dsl dao.
 *
 * @author Marián Oravec
 * 
 * @version 1.13.0
 * @since 1.13.0
 * 
 * @param <PK> the generic type
 * @param <T> the generic type
 */
public abstract class AQDslDao<PK, T extends IEntity<PK>>extends AJpaDao implements IDao
{
	/**
	 * Creates the query.
	 *
	 * @param entityPaths the entity paths
	 * @return the JPA query
	 */
	protected JPAQuery createQuery (EntityPath<?>... entityPaths)
	{
		return new JPAQuery(this.em).from(entityPaths);
	}

	/**
	 * Creates the sub query.
	 *
	 * @param entityPaths the entity paths
	 * @return the JPA sub query
	 */
	protected JPASubQuery createSubQuery (EntityPath<?>... entityPaths)
	{
		return new JPASubQuery().from(entityPaths);
	}

	/**
	 * Creates the update clause.
	 *
	 * @param entityPath the entity path
	 * @return the JPA update clause
	 */
	protected JPAUpdateClause createUpdateClause (EntityPath<?> entityPath)
	{
		return new JPAUpdateClause(this.em, entityPath);
	}

	/**
	 * Creates the delete clause.
	 *
	 * @param entityPath the entity path
	 * @return the JPA delete clause
	 */
	protected JPADeleteClause createDeleteClause (EntityPath<?> entityPath)
	{
		return new JPADeleteClause(this.em, entityPath);
	}

	/**
	 * Eq.
	 *
	 * @param <T> the generic type
	 * @param path the path
	 * @param right the right
	 * @return the boolean expression
	 */
	protected static <T> BooleanExpression eq (final SimpleExpression<T> path, final T right)
	{
		return right == null ? path.isNull() : path.eq(right);
	}

	/**
	 * Checks if is attached.
	 *
	 * @param entity the entity
	 * @return true, if is attached
	 */
	protected boolean isAttached (Object entity)
	{
		return this.em.contains(entity);
	}

	/**
	 * Refresh.
	 *
	 * @param entities the entities
	 */
	protected void refresh (Object... entities)
	{
		for (final Object entity : entities)
		{
			this.em.refresh(entity);
		}
	}

	/**
	 * Gets the count.
	 *
	 * @param q the q
	 * @return the count
	 */
	protected long getCount (final JPAQuery q)
	{
		return q.count();
	}
}
