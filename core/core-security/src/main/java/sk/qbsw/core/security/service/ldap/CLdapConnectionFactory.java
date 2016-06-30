package sk.qbsw.core.security.service.ldap;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapConnectionPool;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.directory.ldap.client.api.DefaultPoolableLdapConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;

/**
 * The factory for LDAP connection.
 *
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.13.0
 */
@Component ("ldapConnectionFactory")
class CLdapConnectionFactory extends AService implements ILdapConnectionFactory
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CLdapConnectionFactory.class);

	/** The configuration data. */
	@Autowired
	private ILdapAuthenticationConfigurator configurationData;

	/** The primary server connection pool. */
	private LdapConnectionPool primaryServerConnectionPool = null;

	/** The secondary server connection pool. */
	private LdapConnectionPool secondaryServerConnectionPool = null;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnectionFactory#init()
	 */
	@Override
	public void init ()
	{
		if (primaryServerConnectionPool == null)
		{
			primaryServerConnectionPool = createPool(configurationData.getServerName(), configurationData.getServerPort(), configurationData.getUserDn(), configurationData.getUserPassword(), configurationData.getUseSslFlag());
			LOGGER.debug("Primary ldap connection pool created.");
		}

		if (secondaryServerConnectionPool == null && configurationData.getSecondaryServerName() != null)
		{
			secondaryServerConnectionPool = createPool(configurationData.getSecondaryServerName(), configurationData.getSecondaryServerPort(), configurationData.getUserDn(), configurationData.getUserPassword(), configurationData.getUseSslFlag());
			LOGGER.debug("Secondary ldap connection pool created.");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnectionFactory#uninit()
	 */
	@Override
	public void uninit ()
	{
		LOGGER.debug("The ldap connection factory uninit called");

		if (primaryServerConnectionPool != null)
		{
			try
			{
				primaryServerConnectionPool.close();
			}
			catch (Exception ex)
			{
				LOGGER.debug("The ldap primary connection uninit failed", ex);
				//Do nothing
			}
		}

		if (secondaryServerConnectionPool != null)
		{
			try
			{
				secondaryServerConnectionPool.close();
			}
			catch (Exception ex)
			{
				LOGGER.debug("The ldap secondary connection uninit failed", ex);
				//Do nothing
			}
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnectionFactory#getConnection()
	 */
	@Override
	public CLdapConnection getConnection () throws LdapException
	{
		LdapConnection connection = null;

		//trying to get connection from primary server
		try
		{
			connection = primaryServerConnectionPool.getConnection();
			LOGGER.debug("Get connection - the number of active connections in primary pool is {}", primaryServerConnectionPool.getNumActive());
		}
		catch (Exception ex)
		{
			LOGGER.warn("Primary Ldap server is down", ex);
			connection = null;
		}

		if (connection != null && connection.isConnected() && connection.isAuthenticated())
		{
			LOGGER.debug("Primary ldap connection borrowed.");

			CLdapConnection connectionModel = new CLdapConnection();
			connectionModel.setConnection(connection);
			connectionModel.setType(ELdapConnectionType.PRIMARY);

			return connectionModel;
		}
		else
		{
			LOGGER.debug("Secondary ldap connection borrowed.");

			//release connection from primary pool and borrow from secondary pool
			primaryServerConnectionPool.releaseConnection(connection);
			connection = secondaryServerConnectionPool.getConnection();
			LOGGER.debug("Get connection - the number of active connections in secondary pool is {}", secondaryServerConnectionPool.getNumActive());

			CLdapConnection connectionModel = new CLdapConnection();
			connectionModel.setConnection(connection);
			connectionModel.setType(ELdapConnectionType.SECONDARY);

			return connectionModel;
		}
	} 

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnectionFactory#releaseConnection(sk.qbsw.core.security.service.ldap.CLdapConnectionModel)
	 */
	@Override
	public void releaseConnection (CLdapConnection connection) throws LdapException
	{
		if (connection != null && connection.getType().equals(ELdapConnectionType.PRIMARY))
		{
			primaryServerConnectionPool.releaseConnection(connection.getConnection());
			connection.setConnection(null);
			connection.setType(null);

			LOGGER.debug("Primary ldap connection released.");
		}
		else if (connection != null && connection.getType().equals(ELdapConnectionType.SECONDARY))
		{
			secondaryServerConnectionPool.releaseConnection(connection.getConnection());
			connection.setConnection(null);
			connection.setType(null);

			LOGGER.debug("Secondary ldap connection released.");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnectionFactory#getOneTimeConnection()
	 */
	@Override
	public CLdapConnection getOneTimeConnection () throws LdapException
	{
		LdapConnection connection = null;
		LdapConnection primaryServerPoolConnection = null;

		try
		{
			primaryServerPoolConnection = primaryServerConnectionPool.getConnection();
		}
		catch (Exception ex)
		{
			LOGGER.warn("Primary Ldap server is down", ex);
			primaryServerPoolConnection = null;
		}

		try
		{
			//checks if the connection to primary server is active or not
			if (primaryServerPoolConnection != null && primaryServerPoolConnection.isConnected())
			{
				connection = new LdapNetworkConnection(configurationData.getServerName(), configurationData.getServerPort(), configurationData.getUseSslFlag());

				CConnectionCounter.increase();
				LOGGER.debug("One time connection to primary server created. Connections count is {}", CConnectionCounter.value());
			}
			else
			{
				connection = new LdapNetworkConnection(configurationData.getSecondaryServerName(), configurationData.getSecondaryServerPort(), configurationData.getUseSslFlag());

				CConnectionCounter.increase();
				LOGGER.debug("One time connection to secondary server created. Connections count is {}", CConnectionCounter.value());
			}
		}
		finally
		{
			primaryServerConnectionPool.releaseConnection(primaryServerPoolConnection);
		}

		//create connection model
		CLdapConnection connectionModel = new CLdapConnection();
		connectionModel.setConnection(connection);
		connectionModel.setType(ELdapConnectionType.ONE_TIME);

		return connectionModel;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ldap.ILdapConnectionFactory#releaseOneTimeConnection(sk.qbsw.core.security.service.ldap.CLdapConnectionModel)
	 */
	@Override
	public void releaseOneTimeConnection (CLdapConnection connection) throws LdapException
	{
		try
		{
			if (connection != null && connection.getConnection() != null)
			{
				connection.getConnection().unBind();
				connection.getConnection().close();
				connection.setConnection(null);
				connection.setType(null);
			}

			CConnectionCounter.decrease();
			LOGGER.debug("One time connection closed. Connections count is {}", CConnectionCounter.value());
		}
		catch (Exception e)
		{
			LOGGER.debug("One time connection closing failed.", e);
		}
	}

	/**
	 * Creates a new CLdapConnection object.
	 *
	 * @param serverName the server name
	 * @param serverPort the server port
	 * @param ldapUserDn the ldap user dn
	 * @param ldapUserPassword the ldap user password
	 * @param useSslFlag the use ssl flag
	 * @return the ldap connection pool
	 */
	private LdapConnectionPool createPool (String serverName, int serverPort, String ldapUserDn, String ldapUserPassword, boolean useSslFlag)
	{
		LdapConnectionConfig config = new LdapConnectionConfig();
		config.setLdapHost(serverName);
		config.setLdapPort(serverPort);
		config.setName(ldapUserDn);
		config.setCredentials(ldapUserPassword);
		config.setUseSsl(useSslFlag);

		DefaultPoolableLdapConnectionFactory factory = new DefaultPoolableLdapConnectionFactory(config);
		LdapConnectionPool pool = new LdapConnectionPool(factory);

		configConnectionPool(pool);

		return pool;
	}

	/**
	 * Config connection pool.
	 *
	 * @param connectionPool the connection pool
	 */
	private void configConnectionPool (LdapConnectionPool connectionPool)
	{
		//this is important - do not change!!!
		//the connection must be test before borrow - the logic depends on it
		connectionPool.setTestOnBorrow(true);

		if (configurationData.getPoolMaxIdle() != null)
		{
			connectionPool.setMaxIdle(configurationData.getPoolMaxIdle());
		}

		if (configurationData.getPoolMinIdle() != null)
		{
			connectionPool.setMinIdle(configurationData.getPoolMinIdle());
		}

		if (configurationData.getPoolMaxActive() != null)
		{
			connectionPool.setMaxActive(configurationData.getPoolMaxActive());
		}

		if (configurationData.getMaxWait() != null)
		{
			connectionPool.setMaxWait(configurationData.getMaxWait());
		}
	}
}

/**
 * Connection counter 
 * @author Dalibor Rak
 *
 */
class CConnectionCounter
{
	/** Local counter of connections*/
	private static volatile int oneTimeConnectionsCount = 0;

	/** Hide connection counter */
	private CConnectionCounter ()
	{
		// hide construction
	}

	/**
	 * 
	 */
	public static void increase ()
	{
		oneTimeConnectionsCount++;
	}

	/**
	 * 
	 */
	public static void decrease ()
	{
		oneTimeConnectionsCount--;
	}

	/**
	 * Reads value
	 * @return current count of connections
	 */
	public static int value ()
	{
		return oneTimeConnectionsCount;
	}
}
