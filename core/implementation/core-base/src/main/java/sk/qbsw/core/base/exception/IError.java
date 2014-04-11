package sk.qbsw.core.base.exception;

/**
 * Interface to get error code
 * 
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 *
 */
public interface IError
{
	/**
	 * method which returns error code - short code which identify error
	 * @return errorCode
	 */
	public String getErrorCode ();

	/**
	 * get description message which describe message
	 * @return error description
	 */
	public String getMessage ();

	/**
	 * text representation of error
	 * @return text which represent attributes of error
	 */
	public String toString ();
}
