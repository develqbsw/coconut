package sk.qbsw.core.security.exception;

@SuppressWarnings ("serial")
public class CWrongPasswordException extends CSecurityException
{

	public CWrongPasswordException (String message)
	{
		super(message);
	}

}
