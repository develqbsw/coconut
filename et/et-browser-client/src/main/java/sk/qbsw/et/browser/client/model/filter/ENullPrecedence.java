package sk.qbsw.et.browser.client.model.filter;

/**
 * The null precedence enum.
 * 
 * @author Tomas Lauro
 *
 * @version 1.16.0
 * @since 1.16.0
 */
public enum ENullPrecedence
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
	LAST;
}
