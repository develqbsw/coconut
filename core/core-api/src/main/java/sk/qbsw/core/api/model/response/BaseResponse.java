package sk.qbsw.core.api.model.response;

import java.io.Serializable;

import sk.qbsw.core.api.exception.DataValidationException;

/**
 * The parent response class.
 *
 * @author Tomas Lauro
 * 
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class BaseResponse implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3512670633387664584L;

	/**
	 * Validate the mandatory request content data.
	 */
	public void validate () throws DataValidationException
	{
		//nothing to validate
	}
}
