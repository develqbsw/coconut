package sk.qbsw.security.authentication.model;

import java.io.Serializable;

/**
 * The authentication token interface.
 * 
 * @author Tomas Lauro
 * @version 1.13.4
 * @since 1.13.4
 */
public interface AuthenticationSecurityToken extends Serializable
{
	/**
	 * Gets the credentials.
	 *
	 * @return the credentials
	 */
	Object getCredentials ();

	/**
	 * Gets the principal.
	 *
	 * @return the principal
	 */
	Object getPrincipal ();
}
