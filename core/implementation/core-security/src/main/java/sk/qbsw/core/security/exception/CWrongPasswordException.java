package sk.qbsw.core.security.exception;

import sk.qbsw.core.base.exception.CSecurityException;

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
