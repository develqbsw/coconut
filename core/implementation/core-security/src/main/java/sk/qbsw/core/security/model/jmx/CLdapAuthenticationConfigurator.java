package sk.qbsw.core.security.model.jmx;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@Service ("ldapAuthenticationConfigurator")
@ManagedResource (objectName = "sk.qbsw.core.security:name=ldapAuthenticationConfigurator", description = "LDAP authentication details")
public class CLdapAuthenticationConfigurator implements ILdapAuthenticationConfigurator
{
	/** The ldap server name. */
	//	@Value ("${ldap.server_name}")
	private String serverName;
	/** The ldap server port. */
	//	@Value ("${ldap.server_port}")
	private int serverPort;
	/** The ldap dn of an user to authenticate with ldap server. */
	//	@Value ("${ldap.user_dn}")
	private String userDn;
	/** The ldap user password. */
	//	@Value ("${ldap.user_password}")
	private String userPassword;
	/** The ldap user organization. */
	//	@Value ("${ldap.user_organization_id}")
	private Long userOrganizationId;
	/** The ldap user search base dn. */
	//	@Value ("${ldap.user_search_base_dn}")
	private String userSearchBaseDn;

	private String groupSearchBaseDn;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getServerName()
	 */
	@Override
	public String getServerName ()
	{
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	@Override
	public void setServerName (String serverName)
	{
		this.serverName = serverName;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getServerPort()
	 */
	@Override
	public int getServerPort ()
	{
		return serverPort;
	}



	/**
	 * @param serverPort the serverPort to set
	 */
	@Override
	public void setServerPort (int serverPort)
	{
		this.serverPort = serverPort;
	}



	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getUserDn()
	 */
	@Override
	public String getUserDn ()
	{
		return userDn;
	}



	/**
	 * @param userDn the userDn to set
	 */
	@Override
	public void setUserDn (String userDn)
	{
		this.userDn = userDn;
	}



	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getUserPassword()
	 */
	@Override
	public String getUserPassword ()
	{
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	@Override
	public void setUserPassword (String userPassword)
	{
		this.userPassword = userPassword;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getUserOrganizationId()
	 */
	@Override
	public Long getUserOrganizationId ()
	{
		return userOrganizationId;
	}

	/**
	 * @param userOrganizationId the userOrganizationId to set
	 */
	@Override
	public void setUserOrganizationId (Long userOrganizationId)
	{
		this.userOrganizationId = userOrganizationId;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getUserSearchBaseDn()
	 */
	@Override
	public String getUserSearchBaseDn ()
	{
		return userSearchBaseDn;
	}



	/**
	 * @param userSearchBaseDn the userSearchBaseDn to set
	 */
	@Override
	public void setUserSearchBaseDn (String userSearchBaseDn)
	{
		this.userSearchBaseDn = userSearchBaseDn;
	}

	public CLdapAuthenticationConfigurator ()
	{
	}

	/**
	 * @return the groupSearchBaseDn
	 */
	@Override
	public String getGroupSearchBaseDn ()
	{
		return groupSearchBaseDn;
	}

	/**
	 * @param groupSearchBaseDn the groupSearchBaseDn to set
	 */
	@Override
	public void setGroupSearchBaseDn (String groupSearchBaseDn)
	{
		this.groupSearchBaseDn = groupSearchBaseDn;
	}
	
	
}
