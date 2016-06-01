package sk.qbsw.core.persistence.model.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

/**
 * Base entity implementation
 *
 * @param <PK> the primary key type
 */
@Access (AccessType.FIELD)
@MappedSuperclass
public abstract class AEntity<P> implements IEntity<P>
{
	protected AEntity ()
	{

	}
	
	/**
	 * Identifies if the entity has identity (ID)
	 * @return true / false
	 */
	public boolean isKnown ()
	{
		return AEntity.isKnown(this);
	}

	public static boolean isKnown (IEntity entity)
	{
		return entity != null && entity.getId() != null;
	}

}
