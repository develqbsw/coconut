package sk.qbsw.security.authentication.ldap.provider;

/**
 * The connection type.
 *
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.13.0
 */
enum LdapConnectionType
{
	/** The primary connection. */
	PRIMARY,

	/** The secondary connection. */
	SECONDARY,

	/** The one time connection. */
	ONE_TIME;
}
