package sk.qbsw.core.persistence.model.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Base entity implementation.
 *
 * @param <P> the generic type
 */
@Access (AccessType.FIELD)
@MappedSuperclass
public abstract class AEntity<P> implements IEntity<P>, Serializable
{
	/**
	 * Instantiates a new a entity.
	 */
	protected AEntity ()
	{
	}

	/**
	 * Identifies if the entity has identity (ID).
	 *
	 * @return true / false
	 */
	public boolean isKnown ()
	{
		return AEntity.isKnown(this);
	}

	/**
	 * Checks if is known.
	 *
	 * @param entity the entity
	 * @return true, if is known
	 */
	public static <T> boolean isKnown (IEntity<T> entity)
	{
		return entity != null && entity.getId() != null;
	}
}
