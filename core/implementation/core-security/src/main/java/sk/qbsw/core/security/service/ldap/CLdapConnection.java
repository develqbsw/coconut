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
 * @version 1.11.10
 * @since 1.10.6
 */
@Component ("ldapConnection")
@Scope (value = "prototype")
class CLdapConnection extends CService implements ILdapConnection
{
	/** The ldap connection. */
	private LdapConnection connection;

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapConnection.class);

	/** The count. */
	private static int COUNT = 0;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#init(java.lang.String, int, boolean)
	 */
	@Override
	public void init (String ldapServerName, int ldapServerPort, boolean useSsl)
	{
		connection = new LdapNetworkConnection(ldapServerName, ldapServerPort, useSsl);
		LOGGER.debug("Initialized new LDAP connection " + ++COUNT);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#closeConnection()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#bindOnServer(java.lang.String, java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#unbindFromServer()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#isInitialized()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#isConnected()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#search(java.lang.String, java.lang.String, org.apache.directory.api.ldap.model.message.SearchScope, java.lang.String)
	 */
	@Override
	public EntryCursor search (String baseDn, String filter, SearchScope scope, String... attributes) throws LdapException
	{
		return connection.search(baseDn, filter, scope, attributes);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#modify(java.lang.String, org.apache.directory.api.ldap.model.entry.Modification)
	 */
	@Override
	public void modify (String dn, Modification... modifications) throws LdapException
	{
		connection.modify(dn, modifications);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#add(org.apache.directory.api.ldap.model.entry.Entry)
	 */
	@Override
	public void add (Entry entry) throws LdapException
	{
		connection.add(entry);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#rename(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void rename (String entryDn, String newRdn, boolean deleteOldRdn) throws LdapException
	{
		connection.rename(entryDn, newRdn, deleteOldRdn);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnection#exists(java.lang.String)
	 */
	@Override
	public boolean exists (String dn) throws LdapException
	{
		return connection.exists(dn);
	}
}
