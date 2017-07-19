package sk.qbsw.security.integration.authentication.mapping;

import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.api.authentication.client.model.response.ReauthenticationResponseBody;
import sk.qbsw.security.core.model.domain.User;

/**
 * The security mapper.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
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
	AuthenticationResponseBody mapToAuthenticationResponse (String masterToken, String authenticationToken, User user);

	/**
	 * Map to reauthentication response.
	 *
	 * @param authenticationToken the authentication token
	 * @return the reauthentication response
	 */
	ReauthenticationResponseBody mapToReauthenticationResponse (String authenticationToken);

	/**
	 * Map user to cs account data cs account data.
	 *
	 * @param user the user
	 * @return the cs account data
	 */
	CSAccountData mapUserToCSAccountData (User user);
}
