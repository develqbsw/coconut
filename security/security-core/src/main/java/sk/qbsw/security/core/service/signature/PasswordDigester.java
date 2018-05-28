package sk.qbsw.security.core.service.signature;

/**
 * Password digester interface
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author Marek Martinkovic
 * @version 1.19.0
 * @since 1.3.0
 */
public interface PasswordDigester
{
	/**
	 * Generates password digest
	 *
	 * @param password password to digest
	 * @return generated digest
	 */
	String generateDigest (String password);

	/**
	 * Check password.
	 *
	 * @param plainPassword the plain password
	 * @param encryptedPassword the encrypted password
	 * @return true, if successful
	 */
	boolean checkPassword (String plainPassword, String encryptedPassword);

	/**
	 * Generates password digest fall back into legacy code, if custom auth scheme is in configurator
	 *
	 * @param login the login
	 * @param password password to digest
	 * @return generated digest
	 */
	String generateDigest (String login, String password);

	/**
	 * Check password. fall back into legacy code, if custom auth scheme is in configurator
	 *
	 * @param plainLogin the plain login
	 * @param plainPassword the plain password
	 * @param encryptedPassword the encrypted password
	 * @return true, if successful
	 */
	boolean checkPassword (String plainLogin, String plainPassword, String encryptedPassword);
}
