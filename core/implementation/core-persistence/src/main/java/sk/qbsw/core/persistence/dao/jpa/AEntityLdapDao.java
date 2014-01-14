package sk.qbsw.core.persistence.dao.jpa;

import java.io.IOException;
import java.util.List;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * Class implements base methods for entity LDAP DAO.
 *
 * @param <PK> the generic type for Entity Primary key
 * @param <T> the generic type for Entity itself
 * @see sk.qbsw.core.persistence.dao.IEntityDao
 * 
 * @author Tomas Lauro
 * 
 * @since 1.6.0
 * @version 1.6.0
 */
public abstract class AEntityLdapDao<PK, T extends IEntity<PK>> implements IEntityDao<PK, T>
{
	/**
	 * Creates the connection to LDAP server.
	 *
	 * @param ldapServerName the ldap server name
	 * @param ldapServerPort the ldap server port
	 * @return the ldap connection
	 */
	protected LdapConnection createConnection (String ldapServerName, int ldapServerPort)
	{
		return new LdapNetworkConnection(ldapServerName, ldapServerPort);
	}

	/**
	 * Close connection.
	 *
	 * @param connection the ldap connection
	 */
	protected void closeConnection (LdapConnection connection)
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
	 * @param connection the connection
	 * @param ldapUserDn the DN of an user account
	 * @param ldapUserPassword the user account password
	 */
	protected void bindOnServer (LdapConnection connection, String ldapUserDn, String ldapUserPassword)
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
	 * @param connection the connection
	 */
	protected void unbindFromServer (LdapConnection connection)
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
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#save(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void save (T object)
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void remove (T object)
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findAll()
	 */
	@Override
	public List<T> findAll ()
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.lang.Object)
	 */
	@Override
	public T findById (PK id)
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#findById(java.util.List)
	 */
	@Override
	public List<T> findById (List<PK> ids)
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#flush()
	 */
	@Override
	public void flush ()
	{
		throw new CSystemException("The not implemented method.");
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#clear()
	 */
	@Override
	public void clear ()
	{
		throw new CSystemException("The not implemented method.");
	}
}
