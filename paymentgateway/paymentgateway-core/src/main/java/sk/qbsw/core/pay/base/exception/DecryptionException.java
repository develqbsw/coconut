/**
 * 
 */
package sk.qbsw.core.pay.base.exception;

/**
 * exception idicates problem with decryption of data sent from banks. most
 * likely there is error in devryption- bad key, data, maybe used algorithm.
 * 
 * @author martinkovic
 * @version 1.16.0
 * @since 1.16.0
 *
 */
public class DecryptionException extends RuntimeException {

	/**
	 * @param e1
	 */
	public DecryptionException(Exception e1) {
		super(e1);
	}

	private static final long serialVersionUID = 1L;

}
