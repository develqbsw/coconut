package sk.qbsw.security.authentication.ldap.provider;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.*;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;

import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The ldap provider implementation.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
public class LdapProviderImpl extends AService implements LdapProvider
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LdapProviderImpl.class);

	private final LdapConnectionFactory ldapConnectionFactory;

	/**
	 * Instantiates a new Ldap provider.
	 *
	 * @param ldapConnectionFactory the ldap connection factory
	 */
	@Autowired
	public LdapProviderImpl (LdapConnectionFactory ldapConnectionFactory)
	{
		this.ldapConnectionFactory = ldapConnectionFactory;
	}

	private void init ()
	{
		ldapConnectionFactory.init();
	}

	@PreDestroy
	private void uninit ()
	{
		LOGGER.debug("The LDAP provider uninit called");

		ldapConnectionFactory.uninit();
	}

	@Override
	public Entry searchSingleResult (String baseDn, String filter, SearchScope scope, String... attributes) throws CBusinessException, LdapException
	{
		// init the connection
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

	@Override
	public Set<Entry> searchResults (String baseDn, String filter, SearchScope scope, String... attributes) throws LdapException, CursorException
	{
		// init the connection
		init();

		Set<Entry> entries = new HashSet<>();
		EntryCursor cursor = null;
		LdapConnectionWrapper connection = null;

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

	@Override
	public void modifyEntry (String dn, String attributeId, String value, EModificationOperation modificationOperation) throws LdapException
	{
		// init the connection
		init();

		// create attribute
		Attribute attribute = new DefaultAttribute(attributeId, value);

		// create modification
		Modification modification = new DefaultModification();
		modification.setAttribute(attribute);
		modification.setOperation(modificationOperation.getOperation());

		LdapConnectionWrapper connection = null;
		try
		{
			// modify
			connection = ldapConnectionFactory.getConnection();
			connection.getConnection().modify(dn, modification);
		}
		finally
		{
			ldapConnectionFactory.releaseConnection(connection);
		}
	}

	@Override
	public void addEntry (String dn, Map<String, byte[][]> attributes) throws LdapException
	{
		// init the connection
		init();

		// create entry
		Entry entry = new DefaultEntry(dn);

		// add values to entry
		for (Map.Entry<String, byte[][]> attribute : attributes.entrySet())
		{
			entry.add(attribute.getKey(), attribute.getValue());
		}

		LdapConnectionWrapper connection = null;
		try
		{
			// add
			connection = ldapConnectionFactory.getConnection();
			connection.getConnection().add(entry);
		}
		finally
		{
			ldapConnectionFactory.releaseConnection(connection);
		}
	}

	@Override
	public void renameEntry (String dn, String newRdn, boolean deleteOldRdn) throws LdapException
	{
		// init the connection
		init();

		LdapConnectionWrapper connection = null;
		try
		{
			// delete
			connection = ldapConnectionFactory.getConnection();
			connection.getConnection().rename(dn, newRdn, deleteOldRdn);
		}
		finally
		{
			ldapConnectionFactory.releaseConnection(connection);
		}
	}

	@Override
	public boolean entryExists (String dn) throws LdapException
	{
		// init the connection
		init();

		LdapConnectionWrapper connection = null;
		try
		{
			// exists
			connection = ldapConnectionFactory.getConnection();
			return connection.getConnection().exists(dn);
		}
		finally
		{
			ldapConnectionFactory.releaseConnection(connection);
		}
	}

	@Override
	public synchronized void authenticate (String baseDn, String loginFilter, @CNotLogged @CNotAuditLogged String password) throws LdapException, CBusinessException
	{
		// init the connection
		init();
		LdapConnectionWrapper connection = null;

		try
		{
			Entry ldapUserEntry = searchSingleResult(baseDn, loginFilter, SearchScope.SUBTREE, "*");

			if (password != null)
			{
				// bind as user (with his DN) to verify password
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

	@Override
	public boolean isConnected ()
	{
		LdapConnectionWrapper connection = null;

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
					// do nothing
				}
			}
		}
	}

	/**
	 * The Enum EModificationOperation.
	 */
	public enum EModificationOperation
	{
		/**
		 * The replace attribute.
		 */
		REPLACE_ATTRIBUTE (ModificationOperation.REPLACE_ATTRIBUTE),

		/**
		 * The add attribute.
		 */
		ADD_ATTRIBUTE (ModificationOperation.ADD_ATTRIBUTE),

		/**
		 * The delete attribute.
		 */
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
