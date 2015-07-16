package sk.qbsw.core.security.oauth.model.jmx;

import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.service.AService;

/**
 * The oauth configurator.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
@Service ("oauthConfigurator")
@ManagedResource (objectName = "sk.qbsw.core.security.oauth:name=oauthConfigurator", description = "oauth configuration")
public class COauthConfigurator extends AService implements IOauthConfigurator
{
	/** The ip ignored - the ip is not ignored by default. */
	private boolean ipIgnored = false;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.model.jmx.IOauthConfigurator#isIpIgnored()
	 */
	@Override
	public boolean isIpIgnored ()
	{
		return ipIgnored;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.model.jmx.IOauthConfigurator#setIpIgnored(boolean)
	 */
	@Override
	public void setIpIgnored (boolean ipIgnored)
	{
		this.ipIgnored = ipIgnored;
	}
}
