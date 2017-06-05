package sk.qbsw.security.api.authentication.mapping;

import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponse;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponse;
import sk.qbsw.security.core.model.domain.User;

/**
 * The security mapper.
 * 
 * @author Tomas Lauro
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SecurityMapper
{
	/**
	 * Map to authentication response.
	 *
	 * @param masterToken the master token
	 * @param authenticationToken the authentication token
	 * @param user the user
	 * @return the authentication response
	 */
	AuthenticationResponse mapToAuthenticationResponse (String masterToken, String authenticationToken, User user);

	/**
	 * Map to reauthentication response.
	 *
	 * @param authenticationToken the authentication token
	 * @return the reauthentication response
	 */
	ReauthenticationResponse mapToReauthenticationResponse (String authenticationToken);
}
