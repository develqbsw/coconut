package sk.qbsw.security.ldap.service;

import java.util.Map;
import java.util.Set;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.ldap.service.CLdapProvider.EModificationOperation;

/**
 * The ldap provider interface.
 *
 * @author Tomas Lauro
 * 
 * @version 1.11.10
 * @since 1.11.10
 */
public interface ILdapProvider
{
	/**
	 * Gets the single result from LDAP server.
	 *
	 * @param baseDn the base dn
	 * @param filter the filter
	 * @param scope the scope
	 * @param attributes the attributes
	 * @return the entry
	 * @throws CBusinessException The exception occurs if there is unexpected count of results
	 * @throws LdapException The exception occurs if the connection to server can't be established
	 */
	public abstract Entry searchSingleResult (String baseDn, String filter, SearchScope scope, String... attributes) throws CBusinessException, LdapException;

	/**
	 * Gets the entries from LDAP server.
	 *
	 * @param baseDn the base dn
	 * @param filter the filter
	 * @param scope the scope
	 * @param attributes the attributes
	 * @return the sets of the entries
	 * @throws LdapException The exception occurs if the connection to server can't be established
	 * @throws CursorException The exception occurs if the results set is corrupted
	 */
	public abstract Set<Entry> searchResults (String baseDn, String filter, SearchScope scope, String... attributes) throws LdapException, CursorException;

	/**
	 * Modify entry v LDAP.
	 *
	 * @param dn the dn of entry
	 * @param attributeId the attribute id
	 * @param value the value
	 * @throws LdapException The exception occurs if the connection to server can't be established or the modification operation failed
	 */
	public abstract void modifyEntry (String dn, String attributeId, String value, EModificationOperation modificationOperation) throws LdapException;

	/**
	 * Add entry v LDAP.
	 *
	 * @param dn the dn of entry
	 * @param attributes the attributes
	 * @throws LdapException The exception occurs if the connection to server can't be established or the add operation failed
	 */
	public abstract void addEntry (String dn, Map<String, byte[][]> attributes) throws LdapException;

	/**
	 * Rename entry.
	 *
	 * @param dn the dn
	 * @param newRdn the new rdn
	 * @param deleteOldRdn the delete old rdn
	 * @throws LdapException The exception occurs if the connection to server can't be established or the rename operation failed
	 */
	public abstract void renameEntry (String dn, String newRdn, boolean deleteOldRdn) throws LdapException;

	/**
	 * Checks if entry exists in LDAP.
	 *
	 * @param dn the dn of entry
	 * @return true, if successful
	 * @throws LdapException The exception occurs if the connection to server can't be established or the exists operation failed
	 */
	public abstract boolean entryExists (String dn) throws LdapException;

	/**
	 * Authenticate the user in ldap. Throws an exception if authentication fails.
	 *
	 * @param baseDn the base dn
	 * @param loginFilter the login filter
	 * @param password the password
	 * @throws CBusinessException The exception occurs if there is not or is more then one user with defined login
	 * @throws LdapException The exception occurs if the connection to server can't be established or the authenticate operation failed
	 */
	public abstract void authenticate (String baseDn, String loginFilter, String password) throws LdapException, CBusinessException;

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public abstract boolean isConnected ();
}
