package sk.qbsw.core.client.factory;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sk.qbsw.core.client.configuration.UrlConfiguration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The base rest template factory.
 * 
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public abstract class BaseRestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRestTemplateFactory.class);

	private final UrlConfiguration configuration;

	private RestTemplate restTemplate;

	@Autowired
	public BaseRestTemplateFactory (UrlConfiguration configuration)
	{
		this.configuration = configuration;
	}

	@Override
	public RestTemplate getObject ()
	{
		return restTemplate;
	}

	@Override
	public Class<RestTemplate> getObjectType ()
	{
		return RestTemplate.class;
	}

	@Override
	public boolean isSingleton ()
	{
		return true;
	}

	@Override
	public void afterPropertiesSet ()
	{
		// checks the configuration parameters
		if (configuration.getHostName() == null || configuration.getPort() == null)
		{
			LOGGER.error("Client - the hostName or port are missing");
		}

		// create client builder
		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		if (configuration.getProxyHostName() != null && configuration.getProxyPort() != null)
		{
			clientBuilder.setProxy(getProxyHost());
		}
		if (configuration.isUntrustedSslConnectionAllowed())
		{
			clientBuilder.setSSLSocketFactory(getUntrustedSslConnectionFactory());
		}

		// create REST template
		restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(clientBuilder.build()));
		restTemplate.getMessageConverters().addAll(addCustomConverters());
		restTemplate.getInterceptors().addAll(addCustomInterceptors());

		// customize rest template
		customizeRestTemplate(restTemplate);
	}

	/**
	 * Add custom http message converters - always call super in overridden methods.
	 *
	 * @return custom converters
	 */
	protected List<HttpMessageConverter<?>> addCustomConverters ()
	{
		return Arrays.asList(new FormHttpMessageConverter());
	}

	/**
	 * Add custom http request interceptors - always call super in overridden methods.
	 *
	 * @return custom interceptors
	 */
	protected List<ClientHttpRequestInterceptor> addCustomInterceptors ()
	{
		return new ArrayList<>();
	}

	/**
	 * Customize rest template instance.
	 *
	 * @param restTemplate rest template
	 */
	protected void customizeRestTemplate (RestTemplate restTemplate)
	{
	}

	/**
	 * Gets the proxy host.
	 *
	 * @return the proxy host
	 */
	private HttpHost getProxyHost ()
	{
		return new HttpHost(configuration.getProxyHostName(), configuration.getProxyPort());
	}

	/**
	 * Gets the untrusted ssl connection factory.
	 *
	 * @return the untrusted ssl connection factory
	 */
	private SSLConnectionSocketFactory getUntrustedSslConnectionFactory ()
	{
		SSLConnectionSocketFactory sslConnectionSocketFactory = null;
		try
		{
			// set self signed strategy and disable hostname verification
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		}
		catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e)
		{
			LOGGER.error("Client - cannot allow untrusted connection", e);
		}

		return sslConnectionSocketFactory;
	}
}
