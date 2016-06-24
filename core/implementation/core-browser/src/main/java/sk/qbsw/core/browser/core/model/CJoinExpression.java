package sk.qbsw.core.browser.core.model;

import com.mysema.query.types.CollectionExpression;
import com.mysema.query.types.EntityPath;

/**
 * The join model for brw dao.
 *
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.15.0
 * 
 * @param <T> the entity type
 */
public class CJoinExpression<T>
{
	/**
	 * The Enum JoinType.
	 */
	public enum JoinType
	{
		/** The inner. */
		INNER,

		/** The left. */
		LEFT;
	}

	/** Type of join. */
	private final CJoinExpression.JoinType joinType;

	/** Entity. */
	private final EntityPath<T> entity;

	/** Collection entity. */
	private final CollectionExpression<?, T> collection;

	/** alias for the entity. */
	private final EntityPath<T> allias;

	/** if the relationship is going to be fetched. */
	private final boolean fetch;

	/**
	 * Instantiates a new c joined expression.
	 *
	 * @param entity the entity
	 * @param fetch the fetch
	 */
	public CJoinExpression (EntityPath<T> entity, boolean fetch)
	{
		this(entity, null, JoinType.LEFT, fetch);
	}

	/**
	 * Instantiates a new c joined expression.
	 *
	 * @param entity the entity
	 * @param fetch the fetch
	 */
	public CJoinExpression (CollectionExpression<?, T> entity, boolean fetch)
	{
		this(entity, null, JoinType.LEFT, fetch);
	}

	/**
	 * Instantiates a new c joined expression.
	 *
	 * @param entity the entity
	 * @param allias the allias
	 * @param joinType the join type
	 * @param fetch the fetch
	 */
	public CJoinExpression (EntityPath<T> entity, EntityPath<T> allias, JoinType joinType, boolean fetch)
	{
		super();
		this.joinType = joinType;
		this.entity = entity;
		this.fetch = fetch;
		this.allias = allias;
		this.collection = null;
	}

	/**
	 * Instantiates a new c joined expression.
	 *
	 * @param entity the entity
	 * @param allias the allias
	 * @param joinType the join type
	 * @param fetch the fetch
	 */
	public CJoinExpression (CollectionExpression<?, T> entity, EntityPath<T> allias, JoinType joinType, boolean fetch)
	{
		super();
		this.joinType = joinType;
		this.collection = entity;
		this.fetch = fetch;
		this.allias = allias;
		this.entity = null;
	}


	/**
	 * Gets the allias.
	 *
	 * @return the allias
	 */
	public EntityPath<T> getAllias ()
	{
		return allias;
	}

	/**
	 * Gets the join type.
	 *
	 * @return the join type
	 */
	public CJoinExpression.JoinType getJoinType ()
	{
		return joinType;
	}


	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	public EntityPath<T> getEntity ()
	{
		return entity;
	}

	/**
	 * Gets the collection.
	 *
	 * @return the collection
	 */
	public CollectionExpression<?, T> getCollection ()
	{
		return collection;
	}



	/**
	 * Checks if is fetch.
	 *
	 * @return true, if is fetch
	 */
	public boolean isFetch ()
	{
		return fetch;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( (allias == null) ? 0 : allias.hashCode());
		result = prime * result + ( (entity == null) ? 0 : entity.hashCode());
		result = prime * result + (fetch ? 1231 : 1237);
		result = prime * result + ( (joinType == null) ? 0 : joinType.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings ("rawtypes")
	@Override
	public boolean equals (Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}

		CJoinExpression other = (CJoinExpression) obj;
		if (allias == null)
		{
			if (other.allias != null)
			{
				return false;
			}
		}
		else if (!allias.equals(other.allias))
		{
			return false;
		}

		if (entity == null)
		{
			if (other.entity != null)
			{
				return false;
			}
		}
		else if (!entity.equals(other.entity))
		{
			return false;
		}

		if (fetch != other.fetch)
		{
			return false;
		}
		if (joinType != other.joinType)
		{
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		return "CJoinedExpression [joinType=" + joinType + ", entity=" + entity + ", allias=" + allias + ", fetch=" + fetch + "]";
	}
}
