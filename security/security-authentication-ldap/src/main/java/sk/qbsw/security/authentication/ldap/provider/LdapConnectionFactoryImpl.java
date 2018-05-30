package sk.qbsw.security.authentication.ldap.provider;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.authentication.ldap.configuration.SecurityLdapAuthenticationConfigurator;

/**
 * The factory for LDAP connection.
 *
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.13.0
 */
public class LdapConnectionFactoryImpl extends AService implements LdapConnectionFactory
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LdapConnectionFactoryImpl.class);

	private final SecurityLdapAuthenticationConfigurator configurationData;

	private LdapConnectionPool primaryServerConnectionPool = null;

	private LdapConnectionPool secondaryServerConnectionPool = null;

	public LdapConnectionFactoryImpl (SecurityLdapAuthenticationConfigurator configurationData)
	{
		this.configurationData = configurationData;
	}

	@Override
	public void init ()
	{
		if (primaryServerConnectionPool == null)
		{
			primaryServerConnectionPool = createPool(configurationData.getServerName(), configurationData.getServerPort(), configurationData.getAccountDn(), configurationData.getAccountPassword(), configurationData.getUseSslFlag());
			LOGGER.debug("Primary ldap connection pool created.");
		}

		if (secondaryServerConnectionPool == null && configurationData.getSecondaryServerName() != null)
		{
			secondaryServerConnectionPool = createPool(configurationData.getSecondaryServerName(), configurationData.getSecondaryServerPort(), configurationData.getAccountDn(), configurationData.getAccountPassword(), configurationData.getUseSslFlag());
			LOGGER.debug("Secondary ldap connection pool created.");
		}
	}

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
				// Do nothing
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
				// Do nothing
			}
		}
	}

	@Override
	public LdapConnectionWrapper getConnection () throws LdapException
	{
		LdapConnection connection;

		// trying to read connection from primary server
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

			LdapConnectionWrapper connectionModel = new LdapConnectionWrapper();
			connectionModel.setConnection(connection);
			connectionModel.setType(LdapConnectionTypes.PRIMARY);

			return connectionModel;
		}
		else
		{
			LOGGER.debug("Secondary ldap connection borrowed.");

			// release connection from primary pool and borrow from secondary pool
			primaryServerConnectionPool.releaseConnection(connection);
			connection = secondaryServerConnectionPool.getConnection();
			LOGGER.debug("Get connection - the number of active connections in secondary pool is {}", secondaryServerConnectionPool.getNumActive());

			LdapConnectionWrapper connectionModel = new LdapConnectionWrapper();
			connectionModel.setConnection(connection);
			connectionModel.setType(LdapConnectionTypes.SECONDARY);

			return connectionModel;
		}
	}

	@Override
	public void releaseConnection (LdapConnectionWrapper connection) throws LdapException
	{
		if (connection != null && connection.getType().equals(LdapConnectionTypes.PRIMARY))
		{
			primaryServerConnectionPool.releaseConnection(connection.getConnection());
			connection.setConnection(null);
			connection.setType(null);

			LOGGER.debug("Primary ldap connection released.");
		}
		else if (connection != null && connection.getType().equals(LdapConnectionTypes.SECONDARY))
		{
			secondaryServerConnectionPool.releaseConnection(connection.getConnection());
			connection.setConnection(null);
			connection.setType(null);

			LOGGER.debug("Secondary ldap connection released.");
		}
	}

	@Override
	public LdapConnectionWrapper getOneTimeConnection () throws LdapException
	{
		LdapConnection connection;
		LdapConnection primaryServerPoolConnection;

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
			// checks if the connection to primary server is active or not
			if (primaryServerPoolConnection != null && primaryServerPoolConnection.isConnected())
			{
				connection = getLdapConnection(configurationData.getServerName(), configurationData.getServerPort());

				ConnectionCounter.increase();
				LOGGER.debug("One time connection to primary server created. Connections count is {}", ConnectionCounter.value());
			}
			else
			{
				connection = getLdapConnection(configurationData.getSecondaryServerName(), configurationData.getSecondaryServerPort());

				ConnectionCounter.increase();
				LOGGER.debug("One time connection to secondary server created. Connections count is {}", ConnectionCounter.value());
			}
		}
		finally
		{
			primaryServerConnectionPool.releaseConnection(primaryServerPoolConnection);
		}

		// create connection model
		LdapConnectionWrapper connectionModel = new LdapConnectionWrapper();
		connectionModel.setConnection(connection);
		connectionModel.setType(LdapConnectionTypes.ONE_TIME);

		return connectionModel;
	}

	private LdapConnection getLdapConnection (String serverName, int serverPort)
	{
		return new LdapNetworkConnection(serverName, serverPort, configurationData.getUseSslFlag());
	}

	@Override
	public void releaseOneTimeConnection (LdapConnectionWrapper connection)
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

			ConnectionCounter.decrease();
			LOGGER.debug("One time connection closed. Connections count is {}", ConnectionCounter.value());
		}
		catch (Exception e)
		{
			LOGGER.debug("One time connection closing failed.", e);
		}
	}

	private LdapConnectionPool createPool (String serverName, int serverPort, String ldapAccountDn, String ldapAccountPassword, boolean useSslFlag)
	{
		LdapConnectionConfig config = new LdapConnectionConfig();
		config.setLdapHost(serverName);
		config.setLdapPort(serverPort);
		config.setName(ldapAccountDn);
		config.setCredentials(ldapAccountPassword);
		config.setUseSsl(useSslFlag);

		DefaultPoolableLdapConnectionFactory factory = new DefaultPoolableLdapConnectionFactory(config);
		LdapConnectionPool pool = new LdapConnectionPool(factory);

		configConnectionPool(pool);

		return pool;
	}

	private void configConnectionPool (LdapConnectionPool connectionPool)
	{
		// this is important - do not change!!!
		// the connection must be test before borrow - the logic depends on it
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

class ConnectionCounter
{
	private static volatile int oneTimeConnectionsCount = 0;

	private ConnectionCounter ()
	{
		// hide construction
	}

	public static void increase ()
	{
		oneTimeConnectionsCount++;
	}

	public static void decrease ()
	{
		oneTimeConnectionsCount--;
	}

	public static int value ()
	{
		return oneTimeConnectionsCount;
	}
}

