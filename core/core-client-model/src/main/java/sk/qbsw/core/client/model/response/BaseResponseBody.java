package sk.qbsw.core.client.model.response;

import sk.qbsw.core.client.model.exception.DataValidationException;
import java.io.Serializable;

/**
 * The parent response body class.
 *
 * @author Tomas Lauro
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class BaseResponseBody implements Serializable
{
	private static final long serialVersionUID = 3512670633387664584L;

	/**
	 * Validate the mandatory request content data.
	 */
	public void validate () throws DataValidationException
	{
		// nothing to validate
	}
}
