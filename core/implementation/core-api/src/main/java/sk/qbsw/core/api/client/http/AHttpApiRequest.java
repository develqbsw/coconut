/*
 * 
 */
package sk.qbsw.core.api.client.http;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentType;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * HttpAPIReqpest repeater support.
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public abstract class AHttpApiRequest implements IHttpApiRequest
{
	/** The repeat count. */
	private int repeatCount = 1;

	/** Timetout of HTTP request. */
	private int timeout = 10000;

	/** The proxy. */
	private HttpHost proxy;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.http.IHttpApiRequest#setRepeatCount(int)
	 */
	@Override
	public void setRepeatCount (int repeat)
	{
		this.repeatCount = repeat;
	}

	/**
	 * Gets the timeout.
	 *
	 * @return the timeout
	 */
	protected int getTimeout ()
	{
		return timeout;
	}

	/**
	 * Sets the timeout in milliseconds.
	 *
	 * @param timeout the new timeout in milliseconds
	 */
	public void setTimeout (int timeout)
	{
		this.timeout = timeout;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.http.IHttpApiRequest#makeCall(java.lang.String, org.apache.http.entity.ContentType, java.lang.String)
	 */
	public final String makeCall (String url, ContentType contentType, String entityInJSon) throws ClientProtocolException, IOException
	{
		CApiHttpException lastEx = null;

		for (int counter = 0; counter < repeatCount; counter++)
		{
			try
			{
				// try call
				return makeOneCall(url, contentType, entityInJSon);
			}
			catch (CApiHttpException ex)
			{
				lastEx = ex;
			}
		}

		// throws last exception
		throw new CApiHttpException("Repeated call failed", lastEx, lastEx.getHttpErrorCode());
	}

	/**
	 * Sets the proxy.
	 *
	 * @param proxy the new proxy
	 */
	public void setProxy (HttpHost proxy)
	{
		this.proxy = proxy;
	}

	/**
	 * Gets the proxy.
	 *
	 * @return the proxy
	 */
	protected HttpHost getProxy ()
	{
		return this.proxy;
	}
	
	/**
	 * Apply timeouts settings.
	 *
	 * @param client the client
	 */
	protected void applyTimeouts(HttpClient client){
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, getTimeout());
		HttpConnectionParams.setSoTimeout(params, getTimeout());
	}
	
	/**
	 * Apply proxy settings.
	 *
	 * @param client the client
	 */
	protected void applyProxy (HttpClient client)
	{
		if (proxy != null)
		{
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
	}

	/**
	 * Make one call of API request.
	 *
	 * @param url the url
	 * @param contentType the content type
	 * @param entityInJSon the entity in j son
	 * @return the string
	 */
	protected abstract String makeOneCall (String url, ContentType contentType, String entityInJSon) throws IOException;
}
