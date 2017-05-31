/**
 * 
 */
package sk.qbsw.core.pay.base.exception;

/**
 * 
 * @author martinkovic
 * @version 1.16.0
 * @since 1.16.0
 *
 */
public class InvalidRequestException extends RuntimeException {

	/**
	 * @param e1
	 */
	public InvalidRequestException(Exception e1) {
		super(e1);
	}

	public InvalidRequestException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
