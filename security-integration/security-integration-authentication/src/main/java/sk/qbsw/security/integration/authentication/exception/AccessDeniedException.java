package sk.qbsw.security.integration.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.IErrorResponse;

/**
 * The access denied exception.
 *
 * @author Tomas Lauro
 * @version 1.0.0
 * @since 1.0.0
 */
@ResponseStatus (HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends CBusinessException
{
	private static final long serialVersionUID = 8296023494797223407L;

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 */
	public AccessDeniedException (String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param error the error
	 */
	public AccessDeniedException (IErrorResponse error)
	{
		super(error);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public AccessDeniedException (String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new c api exception.
	 *
	 * @param message the message
	 * @param error the error
	 */
	public AccessDeniedException (String message, IErrorResponse error)
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
	public AccessDeniedException (String message, Throwable cause, IErrorResponse error)
	{
		super(message, cause, error);
	}
}
