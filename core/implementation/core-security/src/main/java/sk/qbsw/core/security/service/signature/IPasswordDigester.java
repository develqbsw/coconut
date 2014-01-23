package sk.qbsw.core.security.service.signature;

/**
 * Password digester interface 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.3.0
 *
 */
public interface IPasswordDigester
{
	/**
	 * Generates password digest
	 * @param password password to digest
	 * @return generated digest
	 */
	public String generateDigest (String password);

	/**
	 * Check password.
	 *
	 * @param password the password
	 * @param digest the digest
	 * @return true, if successful
	 */
	public boolean checkPassword (String plainPassword, String encryptedPassword);
}
