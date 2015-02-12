package sk.qbsw.core.base.exception;

/**
 * Exception used during security activities
 * 
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.0.0
 */
public class CSecurityException extends CBusinessException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new c security exception.
	 *
	 * @param message the message
	 */
	public CSecurityException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new c security exception.
	 *
	 * @param message the message
	 * @param th the th
	 */
	public CSecurityException(String message, Throwable th)
	{
		super(message, th);
	}

	/**
	 * Instantiates a new c security exception.
	 *
	 * @param message the message of security exception
	 * @param errorCode the error code of the message
	 */
	public CSecurityException(String message, String errorCode)
	{
		super(message, errorCode);
	}
}
