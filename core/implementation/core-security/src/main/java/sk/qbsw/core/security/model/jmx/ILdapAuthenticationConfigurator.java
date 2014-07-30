package sk.qbsw.core.security.model.jmx;

/**
 * The Interface ILdapAuthenticationConfigurator.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.10.3
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
	 * Gets the server port.
	 *
	 * @return the serverPort
	 */
	public abstract int getServerPort ();

	/**
	 * Gets the flag useSsl.
	 * 
	 */
	public abstract boolean getUseSslFlag ();

	/**
	 * Gets the user dn.
	 *
	 * @return the userDn
	 */
	public abstract String getUserDn ();

	/**
	 * Gets the user password.
	 *
	 * @return the userPassword
	 */
	public abstract String getUserPassword ();

	/**
	 * Gets the user organization id.
	 *
	 * @return the userOrganizationId
	 */
	public abstract Long getUserOrganizationId ();

	/**
	 * Gets the user search base dn.
	 *
	 * @return the userSearchBaseDn
	 */
	public abstract String getUserSearchBaseDn ();

	/**
	 * Sets the user search base dn.
	 *
	 * @param userSearchBaseDn the new user search base dn
	 */
	public abstract void setUserSearchBaseDn (String userSearchBaseDn);

	/**
	 * Sets the user organization id.
	 *
	 * @param userOrganizationId the new user organization id
	 */
	public abstract void setUserOrganizationId (Long userOrganizationId);

	/**
	 * Sets the user password.
	 *
	 * @param userPassword the new user password
	 */
	public abstract void setUserPassword (String userPassword);

	/**
	 * Sets the user dn.
	 *
	 * @param userDn the new user dn
	 */
	public abstract void setUserDn (String userDn);

	/**
	 * Sets the server port.
	 *
	 * @param serverPort the new server port
	 */
	public abstract void setServerPort (int serverPort);

	/**
	 * Sets the flag useSsl.
	 *
	 * @param useSsl the flag indicates usage of the ssl
	 */
	public abstract void setUseSslFlag (boolean useSsl);

	/**
	 * Sets the server name.
	 *
	 * @param serverName the new server name
	 */
	public abstract void setServerName (String serverName);

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
}
