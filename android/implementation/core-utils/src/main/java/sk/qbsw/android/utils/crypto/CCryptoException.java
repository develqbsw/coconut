package sk.qbsw.android.utils.crypto;

/**
 * Exception to detect error in ecryption algorithm
 * @author Tomas Lauro
 * @since 0.1.0
 * @version 0.1.0
 */
public class CCryptoException extends Exception
{
	private static final long serialVersionUID = -752537723739472722L;

	public CCryptoException ()
	{
		super();
	}

	public CCryptoException (String message)
	{
		super(message);
	}

	public CCryptoException (Throwable throwable)
	{
		super(throwable);
	}

	public CCryptoException (String message, Throwable throwable)
	{
		super(message, throwable);
	}
}
