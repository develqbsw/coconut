package sk.qbsw.core.api.model.request;

import java.io.Serializable;

import sk.qbsw.core.api.exception.DataValidationException;

/**
 * The base request.
 * 
 * @author Tomas Lauro
 * 
 * @since 1.18.0
 * @version 1.18.0
 */
public abstract class BaseRequest implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7475359831188193238L;

	/**
	 * Validate yourself.
	 *
	 * @return the boolean
	 */
	public void validate () throws DataValidationException
	{
		//nothing to validate
	}
}
