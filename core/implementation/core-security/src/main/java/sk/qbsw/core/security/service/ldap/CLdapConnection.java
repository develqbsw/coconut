package sk.qbsw.core.security.service.ldap;

import org.apache.directory.ldap.client.api.LdapConnection;

/**
 * The ldap connection.
 *
 * @author Tomas Lauro
 * @version 1.13.0
 * @since 1.10.6
 */
class CLdapConnection
{
	/** The connection. */
	private LdapConnection connection;

	/** The type. */
	private ELdapConnectionType type;

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public LdapConnection getConnection ()
	{
		return connection;
	}

	/**
	 * Sets the connection.
	 *
	 * @param connection the new connection
	 */
	public void setConnection (LdapConnection connection)
	{
		this.connection = connection;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public ELdapConnectionType getType ()
	{
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType (ELdapConnectionType type)
	{
		this.type = type;
	}
}
