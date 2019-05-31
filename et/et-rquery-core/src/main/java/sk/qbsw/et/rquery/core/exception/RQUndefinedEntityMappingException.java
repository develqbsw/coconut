package sk.qbsw.et.rquery.core.exception;

/**
 * The request query undefined variable mapping exception.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class RQUndefinedEntityMappingException extends RQSystemException
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
}
