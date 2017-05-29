package sk.qbsw.security.core.service.signature;

/**
 * Password digester interface 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author Marek Martinkovic
 * 
 * @version 1.14.3
 * @since 1.3.0
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

	/**
	 * Generates password digest
	 * fall back into legacy code, if custom auth scheme is in configurator
	 * @param login the login
	 * @param password password to digest
	 * @return generated digest
	 */
	public String generateDigest (String login, String password);

	/**
	 * Check password.
	 * fall back into legacy code, if custom auth scheme is in configurator
	 *
	 * @param login the login
	 * @param password the password
	 * @param digest the digest
	 * @return true, if successful
	 */
	public boolean checkPassword (String plainLogin, String plainPassword, String encryptedPassword);
}
