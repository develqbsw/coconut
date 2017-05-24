package sk.qbsw.security.oauth.model.jmx;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.service.AService;

/**
 * The oauth configurator.
 * 
 * @author Tomas Lauro
 * @author Marek Martinkovic
 * 
 * @version 1.14.3
 * @since 1.13.1
 */
@Service ("oauthConfigurator")
@ManagedResource (objectName = "sk.qbsw.security.oauth:name=oauthConfigurator", description = "oauth configuration")
public class COauthConfigurator extends AService implements IOauthConfigurator
{
	/** The ip ignored for master token - the ip is not ignored by default. */
	private boolean ipIgnored = false;

	/** The ip ignored for authentication token - the ip is not ignored by default. */
	private boolean authIpIgnored = false;

	/* (non-Javadoc)
	 * @see sk.qbsw.security.oauth.model.jmx.IOauthConfigurator#isAuthIpIgnored()
	 */
	@Override
	public boolean isAuthIpIgnored ()
	{
		return authIpIgnored;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.oauth.model.jmx.IOauthConfigurator#setAuthIpIgnored(boolean)
	 */
	@Override
	public void setAuthIpIgnored (boolean authIpIgnored)
	{
		this.authIpIgnored = authIpIgnored;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.oauth.model.jmx.IOauthConfigurator#isIpIgnored()
	 */
	@Override
	public boolean isIpIgnored ()
	{
		return ipIgnored;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.oauth.model.jmx.IOauthConfigurator#setIpIgnored(boolean)
	 */
	@Override
	public void setIpIgnored (boolean ipIgnored)
	{
		this.ipIgnored = ipIgnored;
	}
}
