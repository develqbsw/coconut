package sk.qbsw.security.authentication.ldap.configuration;

/**
 * The Interface LdapAuthenticationConfigurator.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
public interface SecurityLdapAuthenticationConfigurator
{
	/**
	 * Gets the server name.
	 *
	 * @return the serverName
	 */
	String getServerName ();

	/**
	 * Gets the server port.
	 *
	 * @return the serverPort
	 */
	int getServerPort ();

	/**
	 * Gets the secondary server name.
	 *
	 * @return the secondary server name
	 */
	String getSecondaryServerName ();

	/**
	 * Gets the secondary server port.
	 *
	 * @return the secondary server port
	 */
	int getSecondaryServerPort ();

	/**
	 * Gets the flag useSsl.
	 *
	 * @return the use ssl flag
	 */
	boolean getUseSslFlag ();

	/**
	 * Gets the account dn.
	 *
	 * @return the accountDn
	 */
	String getAccountDn ();

	/**
	 * Gets the account password.
	 *
	 * @return the accountPassword
	 */
	String getAccountPassword ();

	/**
	 * Gets the account organization id.
	 *
	 * @return the accountOrganizationId
	 */
	Long getAccountOrganizationId ();

	/**
	 * Gets the account search base dn array.
	 *
	 * @return the accountSearchBaseDns
	 */
	String[] getAccountSearchBaseDns ();

	/**
	 * Gets the account object class.
	 *
	 * @return the account object class
	 */
	String getAccountObjectClass ();

	/**
	 * Gets the account search filter, the default value is (&(cn=%s)).
	 *
	 * @return the account search filter
	 */
	String getAccountSearchFilter ();

	/**
	 * Gets the pool max idle.
	 *
	 * @return the pool max idle
	 */
	Integer getPoolMaxIdle ();

	/**
	 * Gets the pool min idle.
	 *
	 * @return the pool min idle
	 */
	Integer getPoolMinIdle ();

	/**
	 * Gets the pool max active.
	 *
	 * @return the pool max active
	 */
	Integer getPoolMaxActive ();

	/**
	 * Gets the max wait.
	 *
	 * @return the max wait
	 */
	Long getMaxWait ();
}
