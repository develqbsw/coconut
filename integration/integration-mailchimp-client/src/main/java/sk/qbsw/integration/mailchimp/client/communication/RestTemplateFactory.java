package sk.qbsw.integration.mailchimp.client.communication;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.integration.mailchimp.client.configuration.MailchimpClientConfiguration;
import sk.qbsw.integration.mailchimp.client.configuration.MailchimpClientNetworkConfiguration;

/**
 * The base rest template factory.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateFactory.class);

	/** The configurator. */
	@Autowired
	private MailchimpClientConfiguration configuration;

	/** The network configuration. */
	@Autowired
	private MailchimpClientNetworkConfiguration networkConfiguration;

	/** The mailchimp url provider. */
	@Autowired
	private UrlProvider mailchimpUrlProvider;

	/** The rest template. */
	private RestTemplate restTemplate;

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public RestTemplate getObject ()
	{
		return restTemplate;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<RestTemplate> getObjectType ()
	{
		return RestTemplate.class;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton ()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet ()
	{
		//checks the configuration parameters
		if (configuration.getApiKey() == null)
		{
			LOGGER.error("Mailchimp client - the api key is missing");
		}

		//create client builder
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();

		//set credentials
		clientBuilder.setDefaultCredentialsProvider(createCredentialsProvider());

		//set proxy
		if (networkConfiguration.isProxySet())
		{
			clientBuilder.setProxy(createProxyHost());
		}

		//set untrusted ssl connection
		if (networkConfiguration.isUntrustedSslConnectionAllowed())
		{
			clientBuilder.setSSLSocketFactory(createUntrustedSslConnectionFactory());
		}

		restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(clientBuilder.build(), new HttpHost(mailchimpUrlProvider.getHost(), mailchimpUrlProvider.getPort(), mailchimpUrlProvider.getScheme())));
		updateJacksonObjectMapperInMessageConverter(restTemplate);
	}

	private void updateJacksonObjectMapperInMessageConverter (RestTemplate restTemplate)
	{
		for (HttpMessageConverter<?> messageConverter : restTemplate.getMessageConverters())
		{
			if (messageConverter.getClass().equals(MappingJackson2HttpMessageConverter.class))
			{
				((MappingJackson2HttpMessageConverter) messageConverter).setObjectMapper(JacksonFactory.createJacksonObjectMapper());
			}
		}
	}

	/**
	 * Creates a new BaseRestTemplate object.
	 *
	 * @return the http host
	 */
	private HttpHost createProxyHost ()
	{
		return new HttpHost(networkConfiguration.getProxyHost(), networkConfiguration.getProxyPort());
	}

	/**
	 * Creates a new BaseRestTemplate object.
	 *
	 * @return the SSL connection socket factory
	 */
	private SSLConnectionSocketFactory createUntrustedSslConnectionFactory ()
	{
		try
		{
			//set self signed strategy and disable hostname verification
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		}
		catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e)
		{
			LOGGER.error("Mailchimp client - cannot allow untrusted connection", e);
		}

		return null;
	}

	/**
	 * Gets the credentials provider.
	 *
	 * @return the credentials provider
	 */
	private CredentialsProvider createCredentialsProvider ()
	{
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(mailchimpUrlProvider.getHost(), mailchimpUrlProvider.getPort()), new UsernamePasswordCredentials("any", configuration.getApiKey()));

		return credentialsProvider;
	}
}
