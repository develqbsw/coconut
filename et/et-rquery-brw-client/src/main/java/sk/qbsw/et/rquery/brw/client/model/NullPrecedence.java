package sk.qbsw.et.rquery.brw.client.model;

/**
 * The null precedence enum.
 * 
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public enum NullPrecedence
{
	/**
	 * Null precedence not specified. Relies on the RDBMS implementation.
	 */
	NONE,

	/**
	 * Null values appear at the beginning of the sorted collection.
	 */
	FIRST,

	/**
	 * Null values appear at the end of the sorted collection.
	 */
	LAST
}
