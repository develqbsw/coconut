package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;

@SuppressWarnings ("serial")
public class UserDisabledException extends CSecurityException
{

	public UserDisabledException (String message)
	{
		super(message);
	}

}
