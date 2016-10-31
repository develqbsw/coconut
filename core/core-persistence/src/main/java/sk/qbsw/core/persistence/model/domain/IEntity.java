package sk.qbsw.core.persistence.model.domain;

/**
 * Entity basic interface
 * @param <T> the generic type for the primary key
 *
 * @version 1.3.0
 * @since 1.0.0
 */
public interface IEntity<PK>
{
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	PK getId ();
}
