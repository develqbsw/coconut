package sk.qbsw.core.api.model.request;

import java.io.Serializable;


/**
 * The request.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.15.0
 *
 */
public abstract class ARequest implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6459801872001863391L;

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
