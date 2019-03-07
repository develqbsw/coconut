package sk.qbsw.security.spring.oauth.common.configuration;

import sk.qbsw.security.spring.common.configuration.AuthHeaderConfiguration;

/**
 * The oauth header configuration.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface OAuthHeaderConfiguration extends AuthHeaderConfiguration
{
	/**
	 * Gets request security header device id name.
	 *
	 * @return the request security header device id name
	 */
	String getRequestSecurityHeaderDeviceIdName ();
}
