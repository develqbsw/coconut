package sk.qbsw.core.persistence.dao;

import java.io.Serializable;

/**
 * The Interface IDao.
 */
public interface IDao extends Serializable {

	/**
	 * Gets the sequence next value.
	 *
	 * @param sequenceName the sequence name
	 * @return the sequence next value
	 */
	public Long getSequenceNextValue(String sequenceName);

	/**
	 * Flushes all.
	 */
	public void flush();

	/**
	 * Clear the persistence context, causing all managed entities to become detached. Changes made to entities that have not been flushed to the database will not be persisted.
	 */
	public void clear();
}
