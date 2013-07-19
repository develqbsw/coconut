/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.base.exception;

/**
 * Business exception which should be handled by client UI
 *
 * @author Dalibor Rak
 * @version 1.4.0
 * @since 1.0.0
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
