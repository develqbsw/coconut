/**
 * 
 */
package sk.qbsw.et.browser.api.filter;

import java.io.Serializable;

/**
 * The filter criteria abstract class.
 * 
 * @author podmajersky
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.15.0
 */
public abstract class AFilterCriteria implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4464981600604617132L;

	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid ()
	{
		return true;
	}
}
