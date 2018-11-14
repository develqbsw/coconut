package sk.qbsw.security.core.configuration.model;

/**
 * The hash method to hash data.
 *
 * @author Marek Martinkovic
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.14.3
 */
public enum AuthenticationSchemas
{
	/**
	 * The custom authentication in legacy projects
	 */
	CUSTOM,

	/**
	 * digest authentication with schema login:realm:password
	 */
	DIGEST
}
