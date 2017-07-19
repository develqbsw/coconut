package sk.qbsw.security.integration.authentication.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.client.model.response.ErrorResponseBody;
import sk.qbsw.security.api.authentication.client.AuthenticationCodes;

/**
 * The exception handler.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public abstract class SecurityExceptionHandler
{
	/**
	 * The Logger.
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * Handle business exceptions response entity.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler (CBusinessException.class)
	public ResponseEntity<ErrorResponseBody> handleBusinessExceptions (CBusinessException ex)
	{
		LOGGER.error("The business exception caught in exception handler", ex);

		return createErrorResponseEntity(ex.getErrorCode(), ex.getError().getMessageKey());
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
		LOGGER.error("The system exception caught in exception handler", ex);

		return createErrorResponseEntity(ex.getErrorCode(), ex.getError().getMessageKey());
	}

	/**
	 * Create error response entity response entity.
	 *
	 * @param code the code
	 * @param message the message
	 * @return the response entity
	 */
	protected ResponseEntity<ErrorResponseBody> createErrorResponseEntity (String code, String message)
	{
		// create response object
		ErrorResponseBody errorResponseBody = new ErrorResponseBody();

		errorResponseBody.setCode(code);
		errorResponseBody.setMessage(message);

		return ResponseEntity.status(AuthenticationCodes.APPLICATION_HTTP_ERROR_CODE).headers(createResponseHeaders()).body(errorResponseBody);
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
}
