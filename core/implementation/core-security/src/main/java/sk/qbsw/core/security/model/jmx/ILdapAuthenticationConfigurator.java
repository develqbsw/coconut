package sk.qbsw.core.security.model.jmx;

/**
 * The Interface ILdapAuthenticationConfigurator.
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
/**
 * @author rak
 *
 */
/**
 * @author rak
 *
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
	 * Sets the server name.
	 *
	 * @param serverName the new server name
	 */
	public abstract void setServerName (String serverName);

	/**
	 * Sets the group search base dn.
	 *
	 * @param groupSearchBaseDn the new group search base dn
	 */
	public abstract void setGroupSearchBaseDn (String groupSearchBaseDn);

	/**
	 * Gets the group search base dn.
	 *
	 * @return the group search base dn
	 */
	public abstract String getGroupSearchBaseDn ();

}
