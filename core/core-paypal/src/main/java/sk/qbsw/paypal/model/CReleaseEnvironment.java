package sk.qbsw.paypal.model;

import java.io.Serializable;

/**
 * CPaypalReleaseEnvironment class.
 *
 * @author rosenberg
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings ("serial")
public class CReleaseEnvironment implements Serializable
{
	/** The date. */
	private String date;

	/** invitation URL. */
	private String invitationUrl = "http://localhost:8080/airlinesbooking-webtickets/destinations/";

	/** The owner. */
	private String owner = "QBSW";

	/** The proxy port. */
	private Integer proxyPort = new Integer(3128);

	/** The proxy url. Must be set programatically
	 *  */
	private String proxyUrl = "";

	/** email address for reply. */
	private String replyEmail = "nonreply@qbsw.sk";

	/** The use proxy. */
	private Boolean useProxy = Boolean.FALSE;

	/** The version. */
	private String version = "0.2.10";

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate ()
	{
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate (String date)
	{
		this.date = date;
	}

	/**
	 * Gets the invitation url.
	 *
	 * @return the invitation url
	 */
	public String getInvitationUrl ()
	{
		return invitationUrl;
	}

	/**
	 * Sets the invitation url.
	 *
	 * @param invitationUrl the new invitation url
	 */
	public void setInvitationUrl (String invitationUrl)
	{
		this.invitationUrl = invitationUrl;
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public String getOwner ()
	{
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner (String owner)
	{
		this.owner = owner;
	}


	/**
	 * Gets the proxy port.
	 *
	 * @return the proxy port
	 */
	public Integer getProxyPort ()
	{
		return proxyPort;
	}

	/**
	 * Sets the proxy port.
	 *
	 * @param proxyPort the new proxy port
	 */
	public void setProxyPort (Integer proxyPort)
	{
		this.proxyPort = proxyPort;
	}


	/**
	 * Gets the proxy url.
	 *
	 * @return the proxy url
	 */
	public String getProxyUrl ()
	{
		return proxyUrl;
	}

	/**
	 * Sets the proxy url.
	 *
	 * @param proxyUrl the new proxy url
	 */
	public void setProxyUrl (String proxyUrl)
	{
		this.proxyUrl = proxyUrl;
	}

	/**
	 * Gets the reply email.
	 *
	 * @return the reply email
	 */
	public String getReplyEmail ()
	{
		return replyEmail;
	}

	/**
	 * Sets the reply email.
	 *
	 * @param replyEmail the new reply email
	 */
	public void setReplyEmail (String replyEmail)
	{
		this.replyEmail = replyEmail;
	}


	/**
	 * Gets the use proxy.
	 *
	 * @return the use proxy
	 */
	public Boolean getUseProxy ()
	{
		return useProxy;
	}

	/**
	 * Sets the use proxy.
	 *
	 * @param useProxy the new use proxy
	 */
	public void setUseProxy (Boolean useProxy)
	{
		this.useProxy = useProxy;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion ()
	{
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion (String version)
	{
		this.version = version;
	}

}
