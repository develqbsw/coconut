package sk.qbsw.et.mailchimp.client.communication;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sk.qbsw.et.mailchimp.client.configuration.MailchimpClientConfiguration;

/**
 * The mailchimp root.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Component
public class UrlProviderImpl implements UrlProvider
{
	/** The Constant SCHEME. */
	private static final String SCHEME = "https";

	/** The Constant HOST_WITHOUT_DC. */
	private static final String HOST_WITHOUT_DC = "api.mailchimp.com";

	/** The Constant PORT. */
	private static final Integer PORT = 443;

	/** The Constant PATH. */
	private static final String PATH = "3.0";

	/** The dc. */
	private String dc;

	/** The host. */
	private String host;

	/** The root. */
	private String root;

	/** The configuration. */
	@Autowired
	private MailchimpClientConfiguration configuration;

	@PostConstruct
	private void init ()
	{
		this.dc = configuration.getApiKey().split("-")[1];
		this.host = dc + "." + HOST_WITHOUT_DC;
		this.root = SCHEME + "://" + host + "/" + PATH;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpUrlProvider#getScheme()
	 */
	@Override
	public String getScheme ()
	{
		return SCHEME;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpUrlProvider#getDc()
	 */
	@Override
	public String getDc ()
	{
		return dc;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpUrlProvider#getHost()
	 */
	@Override
	public String getHost ()
	{
		return host;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpUrlProvider#getPort()
	 */
	@Override
	public Integer getPort ()
	{
		return PORT;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpUrlProvider#getRoot()
	 */
	@Override
	public String getRoot ()
	{
		return root;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.lomnicagallery.internal.client.configuration.MailchimpUrlProvider#createAddress(java.lang.String)
	 */
	@Override
	public String createAddress (String path)
	{
		return root + path;
	}
}
