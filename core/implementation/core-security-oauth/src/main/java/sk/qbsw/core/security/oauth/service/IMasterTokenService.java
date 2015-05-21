package sk.qbsw.core.security.oauth.service;

/**
 * The master token service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public interface IMasterTokenService
{
	/**
	 * Generate token.
	 *
	 * @return the string
	 */
	String generateToken ();
}
