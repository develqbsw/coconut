package sk.qbsw.core.security.service.ldap;

import java.io.IOException;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.CService;

/**
 * The ldap connection.
 *
 * @author Tomas Lauro
 * @version 1.11.8
 * @since 1.10.6
 */
@Component ("ldapConnection")
@Scope (value = "prototype")
class CLdapConnection extends CService
{
	/** The ldap connection. */
	private LdapConnection connection;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapConnection.class);

	/** The count. */
	private static int COUNT = 0;

	/**
	 * Initialize LDAP connection - must be called before using connection.
	 *
	 * @param ldapServerName the ldap server name
	 * @param ldapServerPort the ldap server port
	 * @return the ldap connection
	 */
	public void init (String ldapServerName, int ldapServerPort, boolean useSsl)
	{
		connection = new LdapNetworkConnection(ldapServerName, ldapServerPort, useSsl);
		LOGGER.debug("Initialized new LDAP connection " + ++COUNT);
	}

	/**
	 * Close connection.
	 *
	 * @param ldapConnection the ldap connection
	 */
	public void closeConnection ()
	{
		LOGGER.debug("Released LDAP connection " + --COUNT);
		if (connection != null && connection.isConnected())
		{
			try
			{
				connection.close();
				connection = null;
			}
			catch (IOException ex)
			{
				//nothing. We can't close connection.
			}
		}
	}

	/**
	 * Bind on a ldap server.
	 *
	 * @param ldapUserDn the DN of an user account
	 * @param ldapUserPassword the user account password
	 * @throws LdapException cannot bind to server or the connection no created
	 */
	public void bindOnServer (String ldapUserDn, @CNotLogged @CNotAuditLogged String ldapUserPassword) throws LdapException
	{
		if (connection != null)
		{
			connection.bind(ldapUserDn, ldapUserPassword);
		}
		else
		{
			throw new LdapException("The ldap connection was not initialized");
		}
	}

	/**
	 * Unbind from server.
	 *
	 */
	public void unbindFromServer ()
	{
		if (connection != null)
		{
			try
			{
				connection.unBind();
			}
			catch (LdapException e)
			{
				//not interesting
			}
		}
	}

	/**
	 * Checks if the connection is initialized - the initialized does not mean that the connection is connected!!!
	 *
	 * @return true, if is initialized
	 */
	public boolean isInitialized ()
	{
		if (connection != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Checks if is connected - see {@link org.apache.directory.ldap.client.api.LdapConnection#isConnected()}.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected ()
	{
		if (connection != null && connection.isConnected() == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

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
	public EntryCursor search (String baseDn, String filter, SearchScope scope, String... attributes) throws LdapException
	{
		return connection.search(baseDn, filter, scope, attributes);
	}

	/**
	 * Modify the ldap record - see {@link org.apache.directory.ldap.client.api.LdapConnection#modify(String, Modification...)}.
	 *
	 * @param dn the dn
	 * @param modifications the modifications
	 * @throws LdapException the ldap exception
	 */
	public void modify (String dn, Modification... modifications) throws LdapException
	{
		connection.modify(dn, modifications);
	}

	/**
	 * Adds the record - see {@link org.apache.directory.ldap.client.api.LdapConnection#add(Entry)}.
	 *
	 * @param entry the entry
	 * @throws LdapException the ldap exception
	 */
	public void add (Entry entry) throws LdapException
	{
		connection.add(entry);
	}

	/**
	 * Rename the record - see {@link org.apache.directory.ldap.client.api.LdapConnection#rename(String, String, boolean)}.
	 *
	 * @param entryDn the entry dn
	 * @param newRdn the new rdn
	 * @param deleteOldRdn the delete old rdn
	 * @throws LdapException the ldap exception
	 */
	public void rename (String entryDn, String newRdn, boolean deleteOldRdn) throws LdapException
	{
		connection.rename(entryDn, newRdn, deleteOldRdn);
	}

	/**
	 * Checks if the record exists - see {@link org.apache.directory.ldap.client.api.LdapConnection#exists(String)}.
	 *
	 * @param dn the dn
	 * @return true, if successful
	 * @throws LdapException the ldap exception
	 */
	public boolean exists (String dn) throws LdapException
	{
		return connection.exists(dn);
	}
}
