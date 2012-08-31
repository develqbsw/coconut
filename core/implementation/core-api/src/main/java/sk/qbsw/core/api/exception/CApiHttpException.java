package sk.qbsw.core.api.exception;

/**
 * HTTP exception call.
 * 
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.2.0
 */
public class CApiHttpException extends RuntimeException
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** HTTP error code. */
	private int httpErrorCode;

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
		super(message, thr);
		this.httpErrorCode = errorCode;
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
}
