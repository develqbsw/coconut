package sk.qbsw.core.persistence.dao;

/**
 * The Interface IDao.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.0.0
 * 
 */
public interface IDao
{
	/**
	 * Gets the sequence next value.
	 *
	 * @param sequenceName the sequence name
	 * @return the sequence next value
	 */
	Long getSequenceNextValue (String sequenceName);

	/**
	 * Flushes all.
	 */
	void flush ();

	/**
	 * Clear the persistence context, causing all managed entities to become detached. Changes made to entities that have not been flushed to the database will not be persisted.
	 */
	void clear ();
}
