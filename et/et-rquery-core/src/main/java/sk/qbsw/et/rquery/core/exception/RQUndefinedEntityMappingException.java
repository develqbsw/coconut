package sk.qbsw.et.rquery.core.exception;

/**
 * The request query undefined variable mapping exception.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class RQUndefinedEntityMappingException extends RQBusinessException
{
	private static final long serialVersionUID = 7329606376468938876L;

	private static final RQError ERROR = RQError.UNDEFINED_ENTITY_MAPPING;

	/**
	 * Instantiates a new Rq undefined entity mapping exception.
	 *
	 * @param message the message
	 */
	public RQUndefinedEntityMappingException (String message)
	{
		super(message, ERROR);
	}

	/**
	 * Instantiates a new Rq undefined entity mapping exception.
	 */
	public RQUndefinedEntityMappingException ()
	{
		super(ERROR);
	}

	/**
	 * Instantiates a new Rq undefined entity mapping exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RQUndefinedEntityMappingException (String message, Throwable cause)
	{
		super(message, cause, ERROR);
	}
}
