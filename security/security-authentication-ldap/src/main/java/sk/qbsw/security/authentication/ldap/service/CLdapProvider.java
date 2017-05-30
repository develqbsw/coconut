package sk.qbsw.security.authentication.ldap.service;

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
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;

/**
 * The ldap provider implementation.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
@Component ("ldapProvider")
public class CLdapProvider extends AService implements ILdapProvider
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapProvider.class);

	/** The ldap connection factory. */
	@Autowired
	private ILdapConnectionFactory ldapConnectionFactory;

	/**
	 * Initialize the connections to LDAP server. Must be called before every connect to server.
	 *
	 * @throws LdapException the ldap exception
	 */
	private void init () throws LdapException
	{
		ldapConnectionFactory.init();
	}

	/**
	 * Uninit the connections and set it to null.
	 */
	@PreDestroy
	private void uninit ()
	{
		LOGGER.debug("The LDAP provider uninit called");

		ldapConnectionFactory.uninit();
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

			if (entries.isEmpty())
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
			throw new CBusinessException("The result's set can't be searched.", ex);
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

		Set<Entry> entries = new HashSet<>();
		EntryCursor cursor = null;
		CLdapConnection connection = null;

		try
		{
			connection = ldapConnectionFactory.getConnection();
			cursor = connection.getConnection().search(baseDn, filter, scope, attributes);

			if (cursor.next())
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

			ldapConnectionFactory.releaseConnection(connection);
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

		CLdapConnection connection = null;
		try
		{
			//modify
			connection = ldapConnectionFactory.getConnection();
			connection.getConnection().modify(dn, modification);
		}
		finally
		{
			ldapConnectionFactory.releaseConnection(connection);
		}
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

		CLdapConnection connection = null;
		try
		{
			//add
			connection = ldapConnectionFactory.getConnection();
			connection.getConnection().add(entry);
		}
		finally
		{
			ldapConnectionFactory.releaseConnection(connection);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#renameEntry(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void renameEntry (String dn, String newRdn, boolean deleteOldRdn) throws LdapException
	{
		//init the connection
		init();

		CLdapConnection connection = null;
		try
		{
			//remove
			connection = ldapConnectionFactory.getConnection();
			connection.getConnection().rename(dn, newRdn, deleteOldRdn);
		}
		finally
		{
			ldapConnectionFactory.releaseConnection(connection);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#entryExists(java.lang.String)
	 */
	@Override
	public boolean entryExists (String dn) throws LdapException
	{
		//init the connection
		init();

		CLdapConnection connection = null;
		try
		{
			//exists
			connection = ldapConnectionFactory.getConnection();
			return connection.getConnection().exists(dn);
		}
		finally
		{
			ldapConnectionFactory.releaseConnection(connection);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#authenticate(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized void authenticate (String baseDn, String loginFilter, @CNotLogged @CNotAuditLogged String password) throws LdapException, CBusinessException
	{
		//init the connection
		init();
		CLdapConnection connection = null;

		try
		{
			Entry ldapUserEntry = searchSingleResult(baseDn, loginFilter, SearchScope.SUBTREE, "*");

			if (password != null)
			{
				//bind as user (with his DN) to verify password
				connection = ldapConnectionFactory.getOneTimeConnection();
				connection.getConnection().bind(ldapUserEntry.getDn().toString(), password);
			}
			else
			{
				throw new CBusinessException("The null password is not allowed");
			}
		}
		finally
		{
			if (connection != null)
			{
				ldapConnectionFactory.releaseOneTimeConnection(connection);
			}
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapProvider#isConnected()
	 */
	@Override
	public boolean isConnected ()
	{
		CLdapConnection connection = null;

		try
		{
			init();
			connection = ldapConnectionFactory.getConnection();

			return connection != null && connection.getConnection() != null && connection.getConnection().isConnected() && connection.getConnection().isAuthenticated();
		}
		catch (Exception ex)
		{
			LOGGER.error("The main LDAP connection throws an exception", ex);
			return false;
		}
		finally
		{
			if (connection != null)
			{
				try
				{
					ldapConnectionFactory.releaseConnection(connection);
				}
				catch (LdapException ex)
				{
					LOGGER.debug("IsConnected method failed", ex);
					//do nothing
				}
			}
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
