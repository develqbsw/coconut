package sk.qbsw.core.security.service.ldap;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;

/**
 * The ldap connection interface.
 *
 * @author Tomas Lauro
 * 
 * @version 1.12.1
 * @since 1.11.10
 */
interface ILdapConnection
{
	/**
	 * Initialize LDAP connection - must be called before using connection.
	 *
	 * @param ldapServerName the ldap server name
	 * @param ldapServerPort the ldap server port
	 * @return the ldap connection
	 */
	public abstract void init (String ldapServerName, int ldapServerPort, boolean useSsl);

	/**
	 * Close connection.
	 *
	 * @param ldapConnection the ldap connection
	 */
	public abstract void closeConnection ();

	/**
	 * Bind on a ldap server.
	 *
	 * @param ldapUserDn the DN of an user account
	 * @param ldapUserPassword the user account password
	 * @throws LdapException cannot bind to server or the connection no created
	 */
	public abstract void bindOnServer (String ldapUserDn, String ldapUserPassword) throws LdapException;

	/**
	 * Unbind from server.
	 *
	 */
	public abstract void unbindFromServer ();

	/**
	 * Checks if the connection is initialized - the initialized does not mean that the connection is connected!!!
	 *
	 * @return true, if is initialized
	 */
	public abstract boolean isInitialized ();

	/**
	 * Checks if is connected - see {@link org.apache.directory.ldap.client.api.LdapConnection#isConnected()}.
	 *
	 * @return true, if is connected
	 */
	public abstract boolean isConnected ();

	/**
	 * Checks if is authenticated - see {@link org.apache.directory.ldap.client.api.LdapConnection#isAuthenticated()}.
	 *
	 * @return true, if is authenticated
	 */
	public abstract boolean isAuthenticated ();

	/**
	 * Search in LDAP - see {@link org.apache.directory.ldap.client.api.LdapConnection#search(String, String, SearchScope, String...)}.
	 *
	 * @param baseDn the base dn
	 * @param filter the filter
	 * @param scope the scope
	 * @param attributes the attributes
	 * @return the entry cursor
	 * @throws LdapException the ldap exception
	 */
	public abstract EntryCursor search (String baseDn, String filter, SearchScope scope, String... attributes) throws LdapException;

	/**
	 * Modify the ldap record - see {@link org.apache.directory.ldap.client.api.LdapConnection#modify(String, Modification...)}.
	 *
	 * @param dn the dn
	 * @param modifications the modifications
	 * @throws LdapException the ldap exception
	 */
	public abstract void modify (String dn, Modification... modifications) throws LdapException;

	/**
	 * Adds the record - see {@link org.apache.directory.ldap.client.api.LdapConnection#add(Entry)}.
	 *
	 * @param entry the entry
	 * @throws LdapException the ldap exception
	 */
	public abstract void add (Entry entry) throws LdapException;

	/**
	 * Rename the record - see {@link org.apache.directory.ldap.client.api.LdapConnection#rename(String, String, boolean)}.
	 *
	 * @param entryDn the entry dn
	 * @param newRdn the new rdn
	 * @param deleteOldRdn the delete old rdn
	 * @throws LdapException the ldap exception
	 */
	public abstract void rename (String entryDn, String newRdn, boolean deleteOldRdn) throws LdapException;

	/**
	 * Checks if the record exists - see {@link org.apache.directory.ldap.client.api.LdapConnection#exists(String)}.
	 *
	 * @param dn the dn
	 * @return true, if successful
	 * @throws LdapException the ldap exception
	 */
	public abstract boolean exists (String dn) throws LdapException;
}
