package sk.qbsw.core.browser.model;

import com.mysema.query.types.CollectionExpression;
import com.mysema.query.types.EntityPath;

import sk.qbsw.core.persistence.dao.querydsl.AEntityQueryDslDao;

/**
 * enitity used to specify brw dao methods - {@link AEntityQueryDslDao#getCompleteBRWList(java.util.List, java.util.List, Long, Long)}
 *
 * @author Peter Bozik
 * @param <T> the generic type
 */
public class CJoinedExpression<T> {

	/**
	 * The Enum JoinType.
	 */
	public enum JoinType {

		/** The inner. */
		INNER,
		/** The left. */
		LEFT, ;
	}

	/** Type of join. */
	private final CJoinedExpression.JoinType joinType;

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
	public CJoinedExpression(EntityPath<T> entity, boolean fetch) {
		this(entity, null, JoinType.LEFT, fetch);

	}

	/**
	 * Instantiates a new c joined expression.
	 *
	 * @param entity the entity
	 * @param fetch the fetch
	 */
	public CJoinedExpression(CollectionExpression<?, T> entity, boolean fetch) {
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
	public CJoinedExpression(EntityPath<T> entity, EntityPath<T> allias, JoinType joinType, boolean fetch) {
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
	public CJoinedExpression(CollectionExpression<?, T> entity, EntityPath<T> allias, JoinType joinType, boolean fetch) {
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
	public EntityPath<T> getAllias() {
		return allias;
	}

	/**
	 * Gets the join type.
	 *
	 * @return the join type
	 */
	public CJoinedExpression.JoinType getJoinType() {
		return joinType;
	}

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	public EntityPath<T> getEntity() {
		return entity;
	}

	/**
	 * Gets the collection.
	 *
	 * @return the collection
	 */
	public CollectionExpression<?, T> getCollection() {
		return collection;
	}

	/**
	 * Checks if is fetch.
	 *
	 * @return true, if is fetch
	 */
	public boolean isFetch() {
		return fetch;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allias == null) ? 0 : allias.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + (fetch ? 1231 : 1237);
		result = prime * result + ((joinType == null) ? 0 : joinType.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CJoinedExpression<?> other = (CJoinedExpression<?>) obj;
		if (allias == null) {
			if (other.allias != null)
				return false;
		}
		else if (!allias.equals(other.allias))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		}
		else if (!entity.equals(other.entity))
			return false;
		if (fetch != other.fetch)
			return false;
		if (joinType != other.joinType)
			return false;
		
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CJoinedExpression [joinType=" + joinType + ", entity=" + entity + ", allias=" + allias + ", fetch=" + fetch + "]";
	}

}
