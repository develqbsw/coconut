package sk.qbsw.integration.mailchimp.client.test.configuration;

import org.springframework.stereotype.Component;

import sk.qbsw.integration.mailchimp.client.configuration.MailchimpClientConfiguration;

/**
 * The client configurator.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Component
public class MailchimpClientConfigurationImpl implements MailchimpClientConfiguration
{
	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpClientConfiguration#getApiKey()
	 */
	@Override
	public String getApiKey ()
	{
		return "d46f9738db709c8cf742d3f8597052d1-us14";
	}
}
