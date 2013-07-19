package sk.qbsw.core.api.exception;

/**
 * HTTP exception call.
 * 
 * @author Dalibor Rak
 * @version 1.4.0
 * @since 1.2.0
 */
public class CApiHttpException extends RuntimeException
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** HTTP error code. */
	private int httpErrorCode;

	/** HTTP response. */
	private String response;

	/**
	 * Instantiates a new api http exception.
	 * 
	 * @param message
	 *            the message
	 * @param thr
	 *            the thr
	 * @param errorCode
	 *            the error code
	 */
	public CApiHttpException (String message, Throwable thr, int errorCode)
	{
		this(message, thr, errorCode, "");
	}

	/**
	 * Instantiates a new c api http exception.
	 *
	 * @param message the message
	 * @param thr the thr
	 * @param errorCode the error code
	 * @param response the response
	 */
	public CApiHttpException (String message, Throwable thr, int errorCode, String response)
	{
		super(message, thr);
		this.httpErrorCode = errorCode;
		this.response = response;
	}

	/**
	 * Gets the http error.
	 * 
	 * @return the http error
	 */
	public int getHttpErrorCode ()
	{
		return httpErrorCode;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getResponse ()
	{
		return response;
	}
}
