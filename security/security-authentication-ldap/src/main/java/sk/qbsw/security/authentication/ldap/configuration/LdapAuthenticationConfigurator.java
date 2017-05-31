package sk.qbsw.security.authentication.ldap.configuration;

/**
 * The Interface LdapAuthenticationConfigurator.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
public interface LdapAuthenticationConfigurator
{
	/**
	 * Gets the server name.
	 *
	 * @return the serverName
	 */
	String getServerName ();

	/**
	 * Sets the server name.
	 *
	 * @param serverName the new server name
	 */
	void setServerName (String serverName);

	/**
	 * Gets the server port.
	 *
	 * @return the serverPort
	 */
	int getServerPort ();

	/**
	 * Sets the server port.
	 *
	 * @param serverPort the new server port
	 */
	void setServerPort (int serverPort);

	/**
	 * Gets the secondary server name.
	 *
	 * @return the secondary server name
	 */
	String getSecondaryServerName ();

	/**
	 * Sets the secondary server name.
	 *
	 * @param secondaryServerName the new secondary server name
	 */
	void setSecondaryServerName (String secondaryServerName);

	/**
	 * Gets the secondary server port.
	 *
	 * @return the secondary server port
	 */
	int getSecondaryServerPort ();

	/**
	 * Sets the secondary server port.
	 *
	 * @param secondaryServerPort the new secondary server port
	 */
	void setSecondaryServerPort (int secondaryServerPort);

	/**
	 * Gets the flag useSsl.
	 *
	 * @return the use ssl flag
	 */
	boolean getUseSslFlag ();

	/**
	 * Sets the flag useSsl.
	 *
	 * @param useSsl the flag indicates usage of the ssl
	 */
	void setUseSslFlag (boolean useSsl);

	/**
	 * Gets the user dn.
	 *
	 * @return the userDn
	 */
	String getUserDn ();

	/**
	 * Sets the user dn.
	 *
	 * @param userDn the new user dn
	 */
	void setUserDn (String userDn);

	/**
	 * Gets the user password.
	 *
	 * @return the userPassword
	 */
	String getUserPassword ();

	/**
	 * Sets the user password.
	 *
	 * @param userPassword the new user password
	 */
	void setUserPassword (String userPassword);

	/**
	 * Gets the user organization id.
	 *
	 * @return the userOrganizationId
	 */
	Long getUserOrganizationId ();

	/**
	 * Sets the user organization id.
	 *
	 * @param userOrganizationId the new user organization id
	 */
	void setUserOrganizationId (Long userOrganizationId);

	/**
	 * Gets the user search base dn array.
	 *
	 * @return the userSearchBaseDns
	 */
	String[] getUserSearchBaseDns ();

	/**
	 * Sets the user search base dn array.
	 *
	 * @param userSearchBaseDns the new user search base dn
	 */
	void setUserSearchBaseDns (String[] userSearchBaseDns);

	/**
	 * Gets the user object class.
	 *
	 * @return the user object class
	 */
	String getUserObjectClass ();

	/**
	 * Sets the user object class.
	 *
	 * @param userObjectClass the new user object class
	 */
	void setUserObjectClass (String userObjectClass);

	/**
	 * Gets the user search filter, the default value is (&(cn=%s)).
	 *
	 * @return the user search filter
	 */
	String getUserSearchFilter ();

	/**
	 * Sets the user search filter. The wildcard %s will be replaced with login.
	 *
	 * @param userSearchFilter the new user search filter
	 */
	void setUserSearchFilter (String userSearchFilter);

	/**
	 * Gets the pool max idle.
	 *
	 * @return the pool max idle
	 */
	Integer getPoolMaxIdle ();

	/**
	 * Sets the pool max idle.
	 *
	 * @param poolMaxIdle the new pool max idle
	 */
	void setPoolMaxIdle (Integer poolMaxIdle);

	/**
	 * Gets the pool min idle.
	 *
	 * @return the pool min idle
	 */
	Integer getPoolMinIdle ();

	/**
	 * Sets the pool min idle.
	 *
	 * @param poolMinIdle the new pool min idle
	 */
	void setPoolMinIdle (Integer poolMinIdle);

	/**
	 * Gets the pool max active.
	 *
	 * @return the pool max active
	 */
	Integer getPoolMaxActive ();

	/**
	 * Sets the pool max active.
	 *
	 * @param poolMaxActive the new pool max active
	 */
	void setPoolMaxActive (Integer poolMaxActive);

	/**
	 * Gets the max wait.
	 *
	 * @return the max wait
	 */
	Long getMaxWait ();

	/**
	 * Sets the max wait.
	 *
	 * @param maxWait the new max wait
	 */
	void setMaxWait (Long maxWait);
}
