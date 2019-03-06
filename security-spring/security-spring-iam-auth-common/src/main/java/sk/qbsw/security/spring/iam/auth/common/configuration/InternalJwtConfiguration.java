package sk.qbsw.security.spring.iam.auth.common.configuration;

import java.io.Serializable;

/**
 * The internal jwt configuration.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface InternalJwtConfiguration extends Serializable
{
	/**
	 * Gets issuer.
	 *
	 * @return the issuer
	 */
	String getIssuer ();

	/**
	 * Gets audience.
	 *
	 * @return the audience
	 */
	String getAudience ();

	/**
	 * Gets subject.
	 *
	 * @return the subject
	 */
	String getSubject ();

	/**
	 * Gets hmac sha key.
	 *
	 * @return the hmac sha key
	 */
	String getHmacShaKey ();

	/**
	 * Gets claims.
	 *
	 * @return the claims
	 */
	InternalJwtClaims getClaims ();
}
