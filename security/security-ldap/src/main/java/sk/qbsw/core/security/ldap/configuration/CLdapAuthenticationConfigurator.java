package sk.qbsw.core.security.ldap.configuration;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.logging.annotation.CAuditLogged;
import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;

/**
 * The Class CLdapAuthenticationConfigurator.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
@Service ("ldapAuthenticationConfigurator")
@ManagedResource (objectName = "sk.qbsw.core.security:name=ldapAuthenticationConfigurator", description = "LDAP authentication details")
public class CLdapAuthenticationConfigurator extends AService implements ILdapAuthenticationConfigurator
{
	/** The ldap server name. */
	private String serverName;

	/** The ldap server port. */
	private int serverPort;

	/** The secondary server name. */
	private String secondaryServerName;

	/** The secondary server port. */
	private int secondaryServerPort;

	/** The flag indicates usage of ssl. */
	private boolean useSsl;

	/** The ldap dn of an user to authenticate with ldap server. */
	private String userDn;

	/** The ldap user password. */
	private String userPassword;

	/** The ldap user organization. */
	private Long userOrganizationId;

	/** The ldap user search base dns. */
	private String[] userSearchBaseDns;

	/** The user search filter. */
	private String userSearchFilter = "(&(cn=%s))";

	/** The ldap user object class. */
	private String userObjectClass;

	/** The pool max idle. */
	private Integer poolMaxIdle;

	/** The pool min idle. */
	private Integer poolMinIdle;

	/** The pool max active. */
	private Integer poolMaxActive;

	/** The max wait. */
	private Long maxWait;

	/**
	 * Instantiates a new c ldap authentication configurator.
	 */
	public CLdapAuthenticationConfigurator ()
	{
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getServerName()
	 */
	@Override
	public String getServerName ()
	{
		return serverName;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setServerName(java.lang.String)
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setServerPort(int)
	 */
	@Override
	public void setServerPort (int serverPort)
	{
		this.serverPort = serverPort;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getSecondaryServerName()
	 */
	@Override
	public String getSecondaryServerName ()
	{
		return secondaryServerName;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setSecondaryServerName(java.lang.String)
	 */
	@Override
	public void setSecondaryServerName (String secondaryServerName)
	{
		this.secondaryServerName = secondaryServerName;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getSecondaryServerPort()
	 */
	@Override
	public int getSecondaryServerPort ()
	{
		return secondaryServerPort;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setSecondaryServerPort(int)
	 */
	@Override
	public void setSecondaryServerPort (int secondaryServerPort)
	{
		this.secondaryServerPort = secondaryServerPort;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getUseSslFlag()
	 */
	@Override
	public boolean getUseSslFlag ()
	{
		return useSsl;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setUseSslFlag(boolean)
	 */
	@Override
	public void setUseSslFlag (boolean useSsl)
	{
		this.useSsl = useSsl;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.ILdapAuthenticationConfigurator#getUserDn()
	 */
	@Override
	public String getUserDn ()
	{
		return userDn;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setUserDn(java.lang.String)
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
	@CLogged (logResult = false)
	@CAuditLogged (actionName = "coconutAuthenticationConfiguratorGetPasswordAction", logResult = false)
	public String getUserPassword ()
	{
		return userPassword;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setUserPassword(java.lang.String)
	 */
	@Override
	public void setUserPassword (@CNotLogged @CNotAuditLogged String userPassword)
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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setUserOrganizationId(java.lang.Long)
	 */
	@Override
	public void setUserOrganizationId (Long userOrganizationId)
	{
		this.userOrganizationId = userOrganizationId;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getUserSearchBaseDns()
	 */
	@Override
	public String[] getUserSearchBaseDns ()
	{
		return userSearchBaseDns;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setUserSearchBaseDns(java.lang.String[])
	 */
	@Override
	public void setUserSearchBaseDns (String[] userSearchBaseDns)
	{
		this.userSearchBaseDns = userSearchBaseDns;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getUserSearchFilter()
	 */
	@Override
	public String getUserSearchFilter ()
	{
		return userSearchFilter;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setUserSearchFilter(java.lang.String)
	 */
	@Override
	public void setUserSearchFilter (String userSearchFilter)
	{
		this.userSearchFilter = userSearchFilter;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getUserObjectClass()
	 */
	@Override
	public String getUserObjectClass ()
	{
		return userObjectClass;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setUserObjectClass(java.lang.String)
	 */
	@Override
	public void setUserObjectClass (String userObjectClass)
	{
		this.userObjectClass = userObjectClass;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getPoolMaxIdle()
	 */
	@Override
	public Integer getPoolMaxIdle ()
	{
		return poolMaxIdle;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setPoolMaxIdle(int)
	 */
	@Override
	public void setPoolMaxIdle (Integer poolMaxIdle)
	{
		this.poolMaxIdle = poolMaxIdle;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getPoolMinIdle()
	 */
	@Override
	public Integer getPoolMinIdle ()
	{
		return poolMinIdle;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setPoolMinIdle(int)
	 */
	public void setPoolMinIdle (Integer poolMinIdle)
	{
		this.poolMinIdle = poolMinIdle;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getPoolMaxActive()
	 */
	@Override
	public Integer getPoolMaxActive ()
	{
		return poolMaxActive;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setPoolMaxActive(int)
	 */
	@Override
	public void setPoolMaxActive (Integer poolMaxActive)
	{
		this.poolMaxActive = poolMaxActive;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#getMaxWait()
	 */
	@Override
	public Long getMaxWait ()
	{
		return maxWait;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator#setMaxWait(long)
	 */
	@Override
	public void setMaxWait (Long maxWait)
	{
		this.maxWait = maxWait;
	}
}
