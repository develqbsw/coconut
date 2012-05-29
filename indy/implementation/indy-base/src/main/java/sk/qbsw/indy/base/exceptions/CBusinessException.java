/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.indy.base.exceptions;

/**
 * The Class CBusinessException.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings ("serial")
public class CBusinessException extends Exception
{

	/**
	 * Instantiates a new c business exception.
	 *
	 * @param message the message
	 */
	public CBusinessException (String message)
	{
		super(message);
	}
}
