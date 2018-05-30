package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;

@SuppressWarnings ("serial")
public class AccountDisabledException extends CSecurityException
{
	public AccountDisabledException (String message)
	{
		super(message);
	}
}
