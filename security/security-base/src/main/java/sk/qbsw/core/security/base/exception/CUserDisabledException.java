package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;

@SuppressWarnings ("serial")
public class CUserDisabledException extends CSecurityException
{

	public CUserDisabledException (String message)
	{
		super(message);
	}

}