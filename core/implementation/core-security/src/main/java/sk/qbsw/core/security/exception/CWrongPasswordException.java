package sk.qbsw.core.security.exception;

/**
 * Wrong password exception.
 * 
 * @deprecated - use {@link sk.qbsw.core.security.exception.CInvalidPasswordException}
 */
@SuppressWarnings ("serial")
public class CWrongPasswordException extends CSecurityException
{
	public CWrongPasswordException (String message)
	{
		super(message);
	}

}
