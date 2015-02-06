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
import org.apache.directory.api.ldap.model.exception.LdapProtocolErrorException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;

/**
 * The ldap provider implementation.
 *
 * @author Tomas Lauro
 * 
 * @version 1.12.1
 * @since 1.6.0
 */
@Component ("ldapProvider")
public class CLdapProvider extends AService implements ILdapProvider
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapProvider.class);

	/** The main ldap connection. */
	@Autowired
	private ILdapConnection connection;

	/** The temporary ldap connection to use in the authentication process. */
	@Autowired
	private ILdapConnection temporaryConnection;

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
		if (connection.isConnected() == false || connection.isAuthenticated() == false)
		{
			LOGGER.debug("The main LDAP connection is not connected");
			try
			{
				connection.bindOnServer(data.getUserDn(), data.getUserPassword());
			}
			catch (LdapProtocolErrorException ex)
			{
				LOGGER.debug("The main LDAP connection throws a LDAP protocol exception - trying to create new connection");
				//close connection and create new one
				//the new connection object has to be created because there is a bug with a SSL connection if only bind is called
				connection.closeConnection();
				connection.init(data.getServerName(), data.getServerPort(), data.getUseSslFlag());
				connection.bindOnServer(data.getUserDn(), data.getUserPassword());
			}
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
		LOGGER.debug("The LDAP provider uninit called");
		if (connection != null && connection.isInitialized() && connection.isConnected())
		{
			connection.unbindFromServer();
			connection.closeConnection();
		}

		if (temporaryConnection != null && temporaryConnection.isInitialized() && temporaryConnection.isConnected())
		{
			temporaryConnection.unbindFromServer();
			temporaryConnection.closeConnection();
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#searchSingleResult(java.lang.String, java.lang.String, org.apache.directory.api.ldap.model.message.SearchScope, java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#searchResults(java.lang.String, java.lang.String, org.apache.directory.api.ldap.model.message.SearchScope, java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#modifyEntry(java.lang.String, java.lang.String, java.lang.String, sk.qbsw.core.security.service.ldap.CLdapProvider.EModificationOperation)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#addEntry(java.lang.String, java.util.Map)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#renameEntry(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void renameEntry (String dn, String newRdn, boolean deleteOldRdn) throws LdapException
	{
		//init the connection
		init();

		//rename
		connection.rename(dn, newRdn, deleteOldRdn);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#entryExists(java.lang.String)
	 */
	@Override
	public boolean entryExists (String dn) throws LdapException
	{
		//init the connection
		init();

		return connection.exists(dn);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#authenticate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized void authenticate (String baseDn, String loginFilter, @CNotLogged @CNotAuditLogged String password) throws LdapException, CBusinessException
	{
		//init the connection
		init();

		EntryCursor cursor = null;

		try
		{
			Entry ldapUserEntry = searchSingleResult(baseDn, loginFilter, SearchScope.SUBTREE, "*");

			try
			{
				if (password != null)
				{
					//bind as user (with his DN) to verify password
					temporaryConnection.bindOnServer(ldapUserEntry.getDn().toString(), password);
				}
				else
				{
					throw new CBusinessException("The null password is not allowed");
				}
			}
			catch (LdapProtocolErrorException ex)
			{
				LOGGER.debug("The temporary LDAP connection throws a LDAP protocol exception - trying to create new connection");
				//close connection and create new one
				//the new connection object has to be created because there is a bug with a SSL connection if only bind is called
				temporaryConnection.closeConnection();
				temporaryConnection.init(data.getServerName(), data.getServerPort(), data.getUseSslFlag());
				temporaryConnection.bindOnServer(ldapUserEntry.getDn().toString(), password);
			}
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#isConnected()
	 */
	@Override
	public boolean isConnected ()
	{
		try
		{
			init();

			if (connection != null && connection.isInitialized() == true && connection.isConnected() == true && connection.isAuthenticated() == true)
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
			LOGGER.error("The main LDAP connection throws an exception", ex);
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
