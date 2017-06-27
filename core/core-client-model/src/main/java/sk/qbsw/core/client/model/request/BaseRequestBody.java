package sk.qbsw.core.client.model.request;

import sk.qbsw.core.client.model.exception.DataValidationException;
import java.io.Serializable;

/**
 * The base request body.
 * 
 * @author Tomas Lauro
 * @since 1.18.0
 * @version 1.18.0
 */
public abstract class BaseRequestBody implements Serializable
{
	private static final long serialVersionUID = 7475359831188193238L;

	/**
	 * Validate yourself.
	 *
	 * @return the boolean
	 */
	public void validate () throws DataValidationException
	{
		// nothing to validate
	}
}
