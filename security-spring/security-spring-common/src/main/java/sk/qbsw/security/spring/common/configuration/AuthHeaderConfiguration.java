package sk.qbsw.security.spring.common.configuration;

import java.io.Serializable;

/**
 * The auth header configuration.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface AuthHeaderConfiguration extends Serializable
{
	/**
	 * Gets request security header token name.
	 *
	 * @return the request security header token name
	 */
	String getRequestSecurityHeaderTokenName ();
}
