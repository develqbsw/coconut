package sk.qbsw.core.security.model.jmx;

/**
 * The Interface ILdapAuthenticationConfigurator.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.10.5
 * @since 1.6.0
 */
public interface ILdapAuthenticationConfigurator
{
	/**
	 * Gets the server name.
	 *
	 * @return the serverName
	 */
	public abstract String getServerName ();

	/**
	 * Sets the server name.
	 *
	 * @param serverName the new server name
	 */
	public abstract void setServerName (String serverName);

	/**
	 * Gets the server port.
	 *
	 * @return the serverPort
	 */
	public abstract int getServerPort ();

	/**
	 * Sets the server port.
	 *
	 * @param serverPort the new server port
	 */
	public abstract void setServerPort (int serverPort);

	/**
	 * Gets the flag useSsl.
	 * 
	 */
	public abstract boolean getUseSslFlag ();

	/**
	 * Sets the flag useSsl.
	 *
	 * @param useSsl the flag indicates usage of the ssl
	 */
	public abstract void setUseSslFlag (boolean useSsl);

	/**
	 * Gets the user dn.
	 *
	 * @return the userDn
	 */
	public abstract String getUserDn ();

	/**
	 * Sets the user dn.
	 *
	 * @param userDn the new user dn
	 */
	public abstract void setUserDn (String userDn);

	/**
	 * Gets the user password.
	 *
	 * @return the userPassword
	 */
	public abstract String getUserPassword ();

	/**
	 * Sets the user password.
	 *
	 * @param userPassword the new user password
	 */
	public abstract void setUserPassword (String userPassword);

	/**
	 * Gets the user organization id.
	 *
	 * @return the userOrganizationId
	 */
	public abstract Long getUserOrganizationId ();

	/**
	 * Sets the user organization id.
	 *
	 * @param userOrganizationId the new user organization id
	 */
	public abstract void setUserOrganizationId (Long userOrganizationId);

	/**
	 * Gets the user search base dn array.
	 *
	 * @return the userSearchBaseDns
	 */
	public abstract String[] getUserSearchBaseDns ();

	/**
	 * Sets the user search base dn array.
	 *
	 * @param userSearchBaseDns the new user search base dn
	 */
	public abstract void setUserSearchBaseDns (String[] userSearchBaseDns);

	/**
	 * Gets the user object class.
	 *
	 * @return the user object class
	 */
	public abstract String getUserObjectClass ();

	/**
	 * Sets the user object class.
	 *
	 * @param userObjectClass the new user object class
	 */
	public abstract void setUserObjectClass (String userObjectClass);

	/**
	 * Gets the user search filter, the default value is (&(cn=%s)).
	 *
	 * @return the user search filter
	 */
	public abstract String getUserSearchFilter ();

	/**
	 * Sets the user search filter. The wildcard %s will be replaced with login.
	 *
	 * @param userSearchFilter the new user search filter
	 */
	public abstract void setUserSearchFilter (String userSearchFilter);
}
