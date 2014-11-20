package sk.qbsw.core.base.exception;

/**
 * Exception used during security activities
 * 
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.12.0
 */
public class CSecurityException extends CSystemException {

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
}
