package sk.qbsw.core.base.exception;

import java.io.Serializable;

/**
 * Interface to get error code
 * 
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 *
 */
public interface IErrorResponse extends Serializable
{
	/**
	 * method which returns error code - short code which identify error
	 * @return errorCode
	 */
	public String getCode ();

	/**
	 * get description message which describe message
	 * @return error description
	 */
	public String getMessageKey ();

	/**
	 * text representation of error
	 * @return text which represent attributes of error
	 */
	@Override
	public String toString ();
}
