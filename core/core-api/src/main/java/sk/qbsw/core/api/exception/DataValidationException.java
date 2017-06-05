package sk.qbsw.core.api.exception;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;

/**
 * The validation of the data failed.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.0.0
 * @since 1.0.0
 */
public class DataValidationException extends CBusinessException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6537534603040010959L;

	/**
	 * Instantiates a new data validation exception.
	 */
	public DataValidationException ()
	{
		super(ECoreErrorResponse.INVALID_DATA);
	}

	/**
	 * Instantiates a new data validation exception.
	 *
	 * @param message the message
	 */
	public DataValidationException (String message)
	{
		super(message, ECoreErrorResponse.INVALID_DATA);
	}
}
