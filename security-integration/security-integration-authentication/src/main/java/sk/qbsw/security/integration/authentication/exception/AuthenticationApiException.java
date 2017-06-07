package sk.qbsw.security.integration.authentication.exception;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The Authentication API exception.
 *
 * @author Tomas Lauro
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class AuthenticationApiException extends CBusinessException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8296023494797223407L;

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 */
	public AuthenticationApiException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param error the error
	 */
	public AuthenticationApiException (IErrorResponse error)
	{
		super(error);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public AuthenticationApiException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public AuthenticationApiException (String message, IErrorResponse error)
	{
		super(message, error);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 * @param error the error
	 */
	public AuthenticationApiException (String message, Throwable cause, IErrorResponse error)
	{
		super(message, cause, error);
	}
}
