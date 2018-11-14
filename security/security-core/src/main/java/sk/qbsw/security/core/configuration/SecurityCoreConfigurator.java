package sk.qbsw.security.core.configuration;

import sk.qbsw.security.core.configuration.model.AdditionalAuthenticationParamsTypes;
import sk.qbsw.security.core.configuration.model.AuthenticationSchemas;
import sk.qbsw.security.core.configuration.model.HashMethods;

import java.util.Map;

/**
 * The authentication configuration.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.7.2
 */
public interface SecurityCoreConfigurator
{
	/**
	 * Gets password pattern.
	 *
	 * @return the password pattern
	 */
	String getPasswordPattern ();

	/**
	 * Gets database password hash method.
	 *
	 * @return the database password hash method
	 */
	HashMethods getPasswordHashMethod ();

	/**
	 * Gets database auth schema.
	 *
	 * @return the database auth schema
	 */
	AuthenticationSchemas getAuthenticationSchema ();

	/**
	 * Gets additional auth parameters.
	 *
	 * @return the additional auth parameters
	 */
	Map<AdditionalAuthenticationParamsTypes, String> getAdditionalAuthenticationParams ();
}
