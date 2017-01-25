package sk.qbsw.et.mailchimp.client.test.configuration;

import org.springframework.stereotype.Component;

import sk.qbsw.et.mailchimp.client.configuration.MailchimpClientNetworkConfiguration;

/**
 * The client nwtwork configurator.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Component
public class MailchimpClientNetworkConfigurationImpl implements MailchimpClientNetworkConfiguration
{
	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpClientConfiguration#isProxySet()
	 */
	@Override
	public boolean isProxySet ()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpClientConfiguration#getProxyHost()
	 */
	@Override
	public String getProxyHost ()
	{
		return "192.168.152.1";
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpClientConfiguration#getProxyPort()
	 */
	@Override
	public Integer getProxyPort ()
	{
		return 3128;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpClientConfiguration#isUntrustedSslConnectionAllowed()
	 */
	@Override
	public boolean isUntrustedSslConnectionAllowed ()
	{
		return true;
	}
}
