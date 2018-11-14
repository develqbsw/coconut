package sk.qbsw.core.api.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.exception.IErrorResponse;
import sk.qbsw.core.client.model.response.ErrorResponseBody;

/**
 * The base exception handler.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class BaseExceptionHandler
{
	/**
	 * The Logger.
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Handle business exceptions response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler (CBusinessException.class)
	public ResponseEntity<ErrorResponseBody> handleBusinessExceptions (CBusinessException ex)
	{
		logger.error("The business exception caught in exception handler", ex);

		return createErrorResponseEntity(ex.getError());
	}

	/**
	 * Handle system exceptions response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler (CSystemException.class)
	public ResponseEntity<ErrorResponseBody> handleSystemExceptions (CSystemException ex)
	{
		logger.error("The system exception caught in exception handler", ex);

		return createErrorResponseEntity(ex.getError());
	}

	/**
	 * Create error response entity response entity.
	 *
	 * @param error the error
	 * @return the response entity
	 */
	protected ResponseEntity<ErrorResponseBody> createErrorResponseEntity (IErrorResponse error)
	{
		if (error != null)
		{
			return createErrorResponseEntity(error.getCode(), error.getMessageKey());
		}
		else
		{
			return createErrorResponseEntity(getDefaultError().getCode(), getDefaultError().getMessageKey());
		}
	}

	/**
	 * Create error response entity response entity.
	 *
	 * @param code    the code
	 * @param message the message
	 * @return the response entity
	 */
	protected ResponseEntity<ErrorResponseBody> createErrorResponseEntity (String code, String message)
	{
		// create response object
		ErrorResponseBody errorResponseBody = new ErrorResponseBody();

		errorResponseBody.setCode(code);
		errorResponseBody.setMessage(message);

		return ResponseEntity.status(getHttpErrorStatus()).headers(createResponseHeaders()).body(errorResponseBody);
	}

	/**
	 * Create response headers http headers.
	 *
	 * @return the http headers
	 */
	protected HttpHeaders createResponseHeaders ()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		return headers;
	}

	/**
	 * Gets http error status.
	 *
	 * @return the http error status
	 */
	protected abstract int getHttpErrorStatus ();

	/**
	 * Gets default error.
	 *
	 * @return the default error
	 */
	protected abstract IErrorResponse getDefaultError ();
}
