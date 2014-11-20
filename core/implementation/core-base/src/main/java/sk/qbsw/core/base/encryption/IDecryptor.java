package sk.qbsw.core.base.encryption;

/**
 * The Interface for decryptor.
 *
 * @param <P> the generic type for plain text
 * @param <C> the generic type for cipher
 * 
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.12.0
 */
public interface IDecryptor<P, C> {

	/**
	 * Decrypt.
	 *
	 * @param cipher the cipher
	 * @return the p
	 */
	public P decrypt(C cipher);
}
