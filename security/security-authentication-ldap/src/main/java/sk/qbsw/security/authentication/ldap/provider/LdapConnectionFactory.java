package sk.qbsw.security.authentication.ldap.provider;

import org.apache.directory.api.ldap.model.exception.LdapException;

/**
 * The factory for LDAP connection.
 *
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.13.0
 */
public interface LdapConnectionFactory
{
	/**
	 * Initialize the factory.
	 */
	void init ();

	/**
	 * Uninit.
	 */
	void uninit ();

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws LdapException the ldap exception
	 */
	LdapConnectionWrapper getConnection () throws LdapException;

	/**
	 * Release the connection.
	 *
	 * @param connection the connection
	 * @throws LdapException the ldap exception
	 */
	void releaseConnection (LdapConnectionWrapper connection) throws LdapException;

	/**
	 * Gets the one time connection.
	 *
	 * @return the one time connection
	 * @throws LdapException the ldap exception
	 */
	LdapConnectionWrapper getOneTimeConnection () throws LdapException;

	/**
	 * Release one time connection.
	 *
	 * @param connection the connection
	 * @throws LdapException the ldap exception
	 */
	void releaseOneTimeConnection (LdapConnectionWrapper connection) throws LdapException;
}
