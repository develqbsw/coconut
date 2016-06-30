package sk.qbsw.core.base.encryption;

import sk.qbsw.core.base.exception.CSecurityException;

/**
 * The Interface for Encryptor.
 *
 * @param <P> the generic type for plain text
 * @param <C> the generic type for cipher
 * 
 * @author Dalibor Rak
 * @version 1.12.0
 * @since 1.12.0
 */
public interface IEncryptor<P, C> {

	/**
	 * Decrypt.
	 *
	 * @param plain the plain text
	 * @return the cipher
	 */
	public C encrypt(P plain) throws CSecurityException;
}
