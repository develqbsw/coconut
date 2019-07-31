package sk.qbsw.security.spring.iam.auth.common.configuration;

import java.io.Serializable;

/**
 * The internal jwt claims.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.1.0
 */
public interface InternalJwtClaims extends Serializable
{
	/**
	 * Gets uid.
	 *
	 * @return the uid
	 */
	String getUid ();

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	String getId ();

	/**
	 * Gets login.
	 *
	 * @return the login
	 */
	String getLogin ();

	/**
	 * Gets email.
	 *
	 * @return the email
	 */
	String getEmail ();

	/**
	 * Gets state.
	 *
	 * @return the state
	 */
	String getState ();

	/**
	 * Gets roles.
	 *
	 * @return the roles
	 */
	String getRoles ();

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	String getUserId ();
}
