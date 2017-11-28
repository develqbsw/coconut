package sk.qbsw.security.integration.authentication.core.exception;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The Authentication API exception.
 *
 * @author Tomas Lauro
 * @version 1.0.0
 * @since 1.0.0
 */
public class AuthenticationException extends CBusinessException
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8296023494797223407L;

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 */
	public AuthenticationException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param error the error
	 */
	public AuthenticationException (IErrorResponse error)
	{
		super(error);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public AuthenticationException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public AuthenticationException (String message, IErrorResponse error)
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
	public AuthenticationException (String message, Throwable cause, IErrorResponse error)
	{
		super(message, cause, error);
	}
}
