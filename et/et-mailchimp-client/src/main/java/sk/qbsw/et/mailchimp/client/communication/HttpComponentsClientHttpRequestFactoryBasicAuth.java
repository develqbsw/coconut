package sk.qbsw.et.mailchimp.client.communication;

import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.client.AuthCache;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * The basic authentication factory.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public class HttpComponentsClientHttpRequestFactoryBasicAuth extends HttpComponentsClientHttpRequestFactory
{
	/** The host. */
	HttpHost host;

	/**
	 * Instantiates a new http components client http request factory basic auth.
	 *
	 * @param client the client
	 * @param host the host
	 */
	public HttpComponentsClientHttpRequestFactoryBasicAuth (HttpClient client, HttpHost host)
	{
		super(client);
		this.host = host;
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.client.HttpComponentsClientHttpRequestFactory#createHttpContext(org.springframework.http.HttpMethod, java.net.URI)
	 */
	protected HttpContext createHttpContext (HttpMethod httpMethod, URI uri)
	{
		return createHttpContext();
	}

	/**
	 * Creates the http context.
	 *
	 * @return the http context
	 */
	private HttpContext createHttpContext ()
	{
		// Create AuthCache instance and generate BASIC scheme object and add it to the local auth cache
		AuthCache authCache = new BasicAuthCache();
		authCache.put(host, new BasicScheme());

		// Add AuthCache to the execution context
		BasicHttpContext localcontext = new BasicHttpContext();
		localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
		return localcontext;
	}
}
