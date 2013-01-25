package sk.qbsw.core.security.service.signature;

/**
 * Password digester interface 
 * @author Dalibor Rak
 * @version 1.3.0
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
}
