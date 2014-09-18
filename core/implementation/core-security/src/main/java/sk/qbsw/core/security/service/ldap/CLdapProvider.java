package sk.qbsw.core.security.service.ldap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.DefaultAttribute;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;

/**
 * The ldap provider implementation.
 *
 * @author Tomas Lauro
 * @version 1.11.4
 * @since 1.6.0
 */
@Component ("ldapProvider")
public class CLdapProvider
{
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(CLdapProvider.class);

	/** The main ldap connection. */
	@Autowired
	private CLdapConnection connection;

	/** The temporary ldap connection to use in the authentication process. */
	@Autowired
	private CLdapConnection temporaryConnection;

	/** The data. */
	@Autowired
	private ILdapAuthenticationConfigurator data;

	/**
	 * Initialize the connections to LDAP server. Must be called before every connect to server.
	 *
	 * @throws LdapException the ldap exception
	 */
	private synchronized void init () throws LdapException
	{
		//initialize the main connection
		if (connection.isInitialized() == false)
		{
			connection.init(data.getServerName(), data.getServerPort(), data.getUseSslFlag());
		}
		if (connection.isConnected() == false)
		{
			logger.debug("The main LDAP connection is not connected");
			connection.bindOnServer(data.getUserDn(), data.getUserPassword());
		}

		//initialize the temporary connection
		if (temporaryConnection.isInitialized() == false)
		{
			temporaryConnection.init(data.getServerName(), data.getServerPort(), data.getUseSslFlag());
		}
	}

	/**
	 * Uninit the connections and set it to null.
	 */
	@PreDestroy
	private synchronized void uninit ()
	{
		logger.debug("The LDAP provider uninit called");
		if (connection != null && connection.isInitialized() && connection.isConnected())
		{
			connection.unbindFromServer();
			connection.closeConnection();
		}

		if (temporaryConnection != null && temporaryConnection.isInitialized() && temporaryConnection.isConnected())
		{
			temporaryConnection.closeConnection();
		}
	}

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
	public Entry searchSingleResult (String baseDn, String filter, SearchScope scope, String... attributes) throws CBusinessException, LdapException
	{
		//init the connection
		init();

		try
		{
			Set<Entry> entries = searchResults(baseDn, filter, scope, attributes);

			if (entries.size() == 0)
			{
				throw new CBusinessException("There is not a single result.");
			}
			else if (entries.size() == 1)
			{
				return entries.iterator().next();
			}
			else
			{
				throw new CBusinessException("The result is not unique.");
			}
		}
		catch (CursorException ex)
		{
			throw new CBusinessException("The result's set can't be searched.");
		}
	}

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
	public Set<Entry> searchResults (String baseDn, String filter, SearchScope scope, String... attributes) throws LdapException, CursorException
	{
		//init the connection
		init();

		Set<Entry> entries = new HashSet<Entry>();
		EntryCursor cursor = null;

		try
		{
			cursor = connection.search(baseDn, filter, scope, attributes);

			if (cursor.next() == true)
			{
				entries.add(cursor.get());
			}

			return entries;
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
		}
	}

	/**
	 * Modify entry v LDAP.
	 *
	 * @param dn the dn of entry
	 * @param attributeId the attribute id
	 * @param value the value
	 * @throws LdapException The exception occurs if the connection to server can't be established or the modification operation failed
	 */
	public void modifyEntry (String dn, String attributeId, String value, EModificationOperation modificationOperation) throws LdapException
	{
		//init the connection
		init();

		//create attribute
		Attribute attribute = new DefaultAttribute(attributeId, value);

		//create modification
		Modification modification = new DefaultModification();
		modification.setAttribute(attribute);
		modification.setOperation(modificationOperation.getOperation());

		//modify
		connection.modify(dn, modification);
	}

	/**
	 * Add entry v LDAP.
	 *
	 * @param dn the dn of entry
	 * @param attributes the attributes
	 * @throws LdapException The exception occurs if the connection to server can't be established or the add operation failed
	 */
	public void addEntry (String dn, Map<String, byte[][]> attributes) throws LdapException
	{
		//init the connection
		init();

		//create entry
		Entry entry = new DefaultEntry(dn);

		//add values to entry
		for (Map.Entry<String, byte[][]> attribute : attributes.entrySet())
		{
			entry.add(attribute.getKey(), attribute.getValue());
		}

		//add
		connection.add(entry);
	}

	/**
	 * Rename entry.
	 *
	 * @param dn the dn
	 * @param newRdn the new rdn
	 * @param deleteOldRdn the delete old rdn
	 * @throws LdapException The exception occurs if the connection to server can't be established or the rename operation failed
	 */
	public void renameEntry (String dn, String newRdn, boolean deleteOldRdn) throws LdapException
	{
		//init the connection
		init();

		//rename
		connection.rename(dn, newRdn, deleteOldRdn);
	}

	/**
	 * Checks if entry exists in LDAP.
	 *
	 * @param dn the dn of entry
	 * @return true, if successful
	 * @throws LdapException The exception occurs if the connection to server can't be established or the exists operation failed
	 */
	public boolean entryExists (String dn) throws LdapException
	{
		//init the connection
		init();

		return connection.exists(dn);
	}

	/**
	 * Authenticate the user in ldap. Throws an exception if authentication fails.
	 *
	 * @param baseDn the base dn
	 * @param loginFilter the login filter
	 * @param password the password
	 * @throws CBusinessException The exception occurs if there is not or is more then one user with defined login
	 * @throws LdapException The exception occurs if the connection to server can't be established or the authenticate operation failed
	 */
	public synchronized void authenticate (String baseDn, String loginFilter, String password) throws LdapException, CBusinessException
	{
		//init the connection
		init();

		EntryCursor cursor = null;

		try
		{
			Entry ldapUserEntry = searchSingleResult(baseDn, loginFilter, SearchScope.SUBTREE, "*");

			//bind as user (with his DN) to verify password
			temporaryConnection.bindOnServer(ldapUserEntry.getDn().toString(), password);
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
		}
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected ()
	{
		try
		{
			init();

			if (connection != null && connection.isInitialized() == true && connection.isConnected() == true)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (Throwable ex)
		{
			return false;
		}
	}

	/**
	 * The Enum EModificationOperation.
	 */
	public enum EModificationOperation
	{
		/** The replace attribute. */
		REPLACE_ATTRIBUTE (ModificationOperation.REPLACE_ATTRIBUTE),

		/** The add attribute. */
		ADD_ATTRIBUTE (ModificationOperation.ADD_ATTRIBUTE),

		/** The remove attribute. */
		REMOVE_ATTRIBUTE (ModificationOperation.REMOVE_ATTRIBUTE);

		/** The operation. */
		private ModificationOperation operation;

		/**
		 * Instantiates a new e modification operation.
		 *
		 * @param operation the operation
		 */
		private EModificationOperation (ModificationOperation operation)
		{
			this.operation = operation;
		}

		/**
		 * Gets the operation.
		 *
		 * @return the operation
		 */
		public ModificationOperation getOperation ()
		{
			return operation;
		}
	}
}
