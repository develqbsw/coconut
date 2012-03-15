package sk.qbsw.core.security.exception;

@SuppressWarnings ("serial")
public class CUserDisabledException extends CSecurityException
{

	public CUserDisabledException (String message)
	{
		super(message);
	}

}
