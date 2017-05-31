package sk.qbsw.core.security.base.exception;

import sk.qbsw.core.base.exception.CSecurityException;

/**
 * Wrong password exception.
 * 
 * @deprecated - use {@link sk.qbsw.core.security.base.exception.InvalidPasswordException}
 */
@SuppressWarnings ("serial")
public class WrongPasswordException extends CSecurityException
{
	public WrongPasswordException (String message)
	{
		super(message);
	}

}
