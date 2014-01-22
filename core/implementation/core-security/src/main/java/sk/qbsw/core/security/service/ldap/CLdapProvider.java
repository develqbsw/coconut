package sk.qbsw.core.security.service.ldap;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.exception.CSecurityException;

/**
 * The ldap provider implementation.
 *
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Component ("ldapProvider")
public class CLdapProvider
{
	/** The ldap connection. */
	private LdapConnection connection;

	/**
	 * Creates the connection to LDAP server.
	 *
	 * @param ldapServerName the ldap server name
	 * @param ldapServerPort the ldap server port
	 * @return the ldap connection
	 */
	public void createConnection (String ldapServerName, int ldapServerPort)
	{
		connection = new LdapNetworkConnection(ldapServerName, ldapServerPort);
	}

	/**
	 * Close connection.
	 *
	 */
	public void closeConnection ()
	{
		if (connection != null)
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
	 */
	public void bindOnServer (String ldapUserDn, String ldapUserPassword)
	{
		if (connection != null)
		{
			try
			{
				connection.bind(ldapUserDn, ldapUserPassword);
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
	 * Authenticate the user in ldap. Throws an exception if authentication fails.
	 *
	 * @param baseDn the base dn
	 * @param loginFilter the login filter
	 * @param password the password
	 * @throws CBusinessException the business exception
	 */
	public void authenticate (String baseDn, String loginFilter, String password) throws CSecurityException
	{
		EntryCursor cursor = null;

		try
		{
			Entry ldapUserEntry = searchSingleResult(baseDn, loginFilter, SearchScope.SUBTREE, "*");

			try
			{
				//bind as user (with his DN) to verify password
				connection.unBind();
				connection.bind(ldapUserEntry.getDn(), password);
			}
			catch (LdapException ex)
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
			if (cursor != null)
			{
				cursor.close();
			}
		}
	}
}
