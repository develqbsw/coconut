package sk.qbsw.android.ui.component.validation;

/**
 * Validation exception, this exception is used when user add bad inputs to fields
 *
 * @author Michal Lacko
 * @version 0.1.0
 */
@SuppressWarnings ("serial")
public class CValidationException extends Exception
{

	/**
	 * Instantiates a new validation exception.
	 *
	 * @param message the message
	 */
	public CValidationException (String message)
	{
		super(message);
	}
}
