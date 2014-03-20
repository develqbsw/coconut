package sk.qbsw.core.security.service.ldap;

import java.io.IOException;
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
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;

/**
 * The ldap provider implementation.
 *
 * @author Tomas Lauro
 * @version 1.7.2
 * @since 1.6.0
 */
@Component ("ldapProvider")
public class CLdapProvider
{
	/** The main ldap connection. */
	private LdapConnection connection;

	/** The temporary ldap connection to use in the authentication process. */
	private LdapConnection temporaryConnection;

	/** The data. */
	@Autowired
	private ILdapAuthenticationConfigurator data;

	/**
	 * Initialize the connections to LDAP server. Must be called before every connect to server.
	 */
	private synchronized void init ()
	{
		//initialize the main connection
		if (connection == null)
		{
			connection = createConnection(data.getServerName(), data.getServerPort());

			if (connection.isConnected() == false)
			{
				openConnection(connection);
				bindOnServer(connection, data.getUserDn(), data.getUserPassword());
			}
		}

		//initialize the temporary connection
		if (temporaryConnection == null)
		{
			temporaryConnection = createConnection(data.getServerName(), data.getServerPort());

			if (temporaryConnection.isConnected() == false)
			{
				openConnection(temporaryConnection);
			}
		}
	}

	/**
	 * Uninit the connections and set it to null.
	 */
	@PreDestroy
	private synchronized void uninit ()
	{
		if (connection != null && connection.isConnected())
		{
			unbindFromServer(connection);
			closeConnection(connection);
		}

		if (temporaryConnection != null && temporaryConnection.isConnected())
		{
			closeConnection(connection);
		}

		connection = null;
		temporaryConnection = null;
	}

	/**
	 * Creates the connection to LDAP server.
	 *
	 * @param ldapServerName the ldap server name
	 * @param ldapServerPort the ldap server port
	 * @return the ldap connection
	 */
	private LdapConnection createConnection (String ldapServerName, int ldapServerPort)
	{
		return new LdapNetworkConnection(ldapServerName, ldapServerPort);
	}

	/**
	 * Open connection to ldap server.
	 *
	 * @param ldapConnection the ldap connection
	 */
	private void openConnection (LdapConnection ldapConnection)
	{
		try
		{
			ldapConnection.connect();
		}
		catch (LdapException ex)
		{
			throw new CSystemException("Cannot open connection to ldap server", ex);
		}
	}

	/**
	 * Close connection.
	 *
	 * @param ldapConnection the ldap connection
	 */
	private void closeConnection (LdapConnection ldapConnection)
	{
		if (ldapConnection != null && ldapConnection.isConnected())
		{
			try
			{
				ldapConnection.close();
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
	 * @param ldapConnection the ldap connection
	 * @param ldapUserDn the DN of an user account
	 * @param ldapUserPassword the user account password
	 */
	private void bindOnServer (LdapConnection ldapConnection, String ldapUserDn, String ldapUserPassword)
	{
		if (ldapConnection != null)
		{
			try
			{
				ldapConnection.bind(ldapUserDn, ldapUserPassword);
			}
			catch (LdapException ex)
			{
				throw new CSystemException("Cannot bind on a ldap server with user: " + ldapUserDn, ex);
			}
		}
		else
		{
			throw new CSystemException("Cannot bind on a ldap server. Connection doesn't exist");
		}
	}

	/**
	 * Unbind from server.
	 *
	 * @param ldapConnection the ldap connection
	 */
	private void unbindFromServer (LdapConnection ldapConnection)
	{
		if (ldapConnection != null)
		{
			try
			{
				ldapConnection.unBind();
			}
			catch (LdapException e)
			{
				//not interesting
			}
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
	 * @throws CBusinessException the c business exception
	 */
	public Entry searchSingleResult (String baseDn, String filter, SearchScope scope, String... attributes) throws CBusinessException
	{
		//init the connection
		init();

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

	/**
	 * Gets the entries from LDAP server.
	 *
	 * @param baseDn the base dn
	 * @param filter the filter
	 * @param scope the scope
	 * @param attributes the attributes
	 * @return the sets of the entries
	 */
	public Set<Entry> searchResults (String baseDn, String filter, SearchScope scope, String... attributes)
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
		catch (LdapInvalidAttributeValueException ex)
		{
			throw new CSystemException("An LdapInvalidAttributeValueException exception raised: " + ex.toString());
		}
		catch (LdapException ex)
		{
			throw new CSystemException("An LdapException exception raised: " + ex.toString());
		}
		catch (CursorException ex)
		{
			throw new CSystemException("An CursorException exception raised: " + ex.toString());
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
	 * @throws CSecurityException the security exception
	 */
	public void modifyEntry (String dn, String attributeId, String value, EModificationOperation modificationOperation) throws CSecurityException
	{
		//init the connection
		init();

		try
		{
			//create attribute
			Attribute attribute = new DefaultAttribute(attributeId, value);

			//create modification
			Modification modification = new DefaultModification();
			modification.setAttribute(attribute);
			modification.setOperation(modificationOperation.getOperation());

			//modify
			connection.modify(dn, modification);
		}
		catch (LdapException ex)
		{
			throw new CSecurityException("The attribute " + attributeId + " of dn " + dn + " in ldap cannot be changed: " + ex.toString());
		}
	}

	/**
	 * Add entry v LDAP.
	 *
	 * @param dn the dn of entry
	 * @param attributes the attributes
	 * @throws CSecurityException the security exception
	 */
	public void addEntry (String dn, Map<String, String[]> attributes) throws CSecurityException
	{
		//init the connection
		init();

		try
		{
			//create entry
			Entry entry = new DefaultEntry(dn);

			//add values to entry
			for (Map.Entry<String, String[]> attribute : attributes.entrySet())
			{
				entry.add(attribute.getKey(), attribute.getValue());
			}

			//add
			connection.add(entry);
		}
		catch (LdapException ex)
		{
			throw new CSecurityException("The entry with dn " + dn + " in ldap cannot be created: " + ex.toString());
		}
	}

	/**
	 * Rename entry.
	 *
	 * @param dn the dn
	 * @param newRdn the new rdn
	 * @param deleteOldRdn the delete old rdn
	 * @throws CSecurityException the security exception
	 */
	public void renameEntry (String dn, String newRdn, boolean deleteOldRdn) throws CSecurityException
	{
		//init the connection
		init();

		try
		{
			//rename
			connection.rename(dn, newRdn, deleteOldRdn);
		}
		catch (LdapException ex)
		{
			throw new CSecurityException("Cannot change rnd with dn: " + dn);
		}
	}

	/**
	 * Checks if entry exists in LDAP.
	 *
	 * @param dn the dn of entry
	 * @return true, if successful
	 * @throws CSecurityException the security exception
	 */
	public boolean entryExists (String dn) throws CSecurityException
	{
		//init the connection
		init();

		try
		{
			return connection.exists(dn);
		}
		catch (LdapException ex)
		{
			throw new CSecurityException("Cannot validate the entry with dn: " + dn);
		}
	}

	/**
	 * Authenticate the user in ldap. Throws an exception if authentication fails.
	 *
	 * @param baseDn the base dn
	 * @param loginFilter the login filter
	 * @param password the password
	 * @throws CSecurityException the c security exception
	 */
	public void authenticate (String baseDn, String loginFilter, String password) throws CSecurityException
	{
		//init the connection
		init();

		EntryCursor cursor = null;

		try
		{
			Entry ldapUserEntry = searchSingleResult(baseDn, loginFilter, SearchScope.SUBTREE, "*");

			try
			{
				//bind as user (with his DN) to verify password
				bindOnServer(temporaryConnection, ldapUserEntry.getDn().toString(), password);
			}
			catch (CSystemException ex)
			{
				throw new CSecurityException("The authentication failed - invalid password or user.");
			}
		}
		catch (CBusinessException ex)
		{
			//there is not a user or not unique
			throw new CSecurityException(ex.getMessage());
		}
		finally
		{
			unbindFromServer(temporaryConnection);

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

			if (connection != null && connection.isConnected() == true)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (CSystemException ex)
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
