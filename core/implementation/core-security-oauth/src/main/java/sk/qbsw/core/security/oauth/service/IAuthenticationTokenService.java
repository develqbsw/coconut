package sk.qbsw.core.security.oauth.service;

/**
 * The authentication token service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public interface IAuthenticationTokenService
{
	/**
	 * Generate token.
	 *
	 * @return the string
	 */
	String generateToken ();

	/**
	 * Checks if is generation request valid.
	 *
	 * @param userId the user id
	 * @param masterToken the master token
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return true, if is generation request valid
	 */
	boolean isGenerationRequestValid (Long userId, String masterToken, String deviceId, String ip);
}
