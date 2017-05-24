package sk.qbsw.security.ldap.service;

/**
 * The connection type.
 *
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.13.0
 */
enum ELdapConnectionType
{
	/** The primary connection. */
	PRIMARY,

	/** The secondary connection. */
	SECONDARY,

	/** The one time connection. */
	ONE_TIME;
}
