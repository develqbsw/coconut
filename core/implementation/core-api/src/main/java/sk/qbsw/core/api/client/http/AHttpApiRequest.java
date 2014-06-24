/*
 * 
 */
package sk.qbsw.core.api.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
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
 * @author Michal Lacko
 * @version 1.10.0
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
	
	@Override
	public String makeCall (String url, ContentType contentType, String entity) throws IOException
	{
		// TODO Auto-generated method stub
		return makeCall(url, contentType, entity, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.http.IHttpApiRequest#makeCall(java.lang.String, org.apache.http.entity.ContentType, java.lang.String)
	 */
	public final String makeCall (String url, ContentType contentType, String entity, Map<String, String> headers) throws ClientProtocolException, IOException
	{
		CApiHttpException lastEx = null;

		for (int counter = 0; counter < repeatCount; counter++)
		{
			try
			{
				// try call
				return makeOneCall(url, contentType, entity, headers);
			}
			catch (CApiHttpException ex)
			{
				lastEx = ex;
			}
		}

		// throws last exception
		throw new CApiHttpException("Repeated call failed", lastEx, lastEx.getHttpErrorCode(), lastEx.getResponse());
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
	protected void applyTimeouts (HttpClient client)
	{
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
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected abstract String makeOneCall (String url, ContentType contentType, String entityInJSon, Map<String, String> headers) throws IOException;

	/**
	 * Gets the entity content.
	 *
	 * @param response the response
	 * @return the entity content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected String getEntityContent (HttpResponse response) throws IOException
	{
		StringBuffer output = new StringBuffer();
		InputStreamReader inputReader = null;
		try
		{
			inputReader = new InputStreamReader(response.getEntity().getContent());
			BufferedReader br = new BufferedReader(inputReader);

			// reads output
			try
			{
				String line;
				while ( (line = br.readLine()) != null)
				{
					output.append(line);
				}
			}
			finally
			{
				if (br != null)
				{
					br.close();
				}
			}
		}
		finally
		{
			if (inputReader != null)
			{
				try
				{
					inputReader.close();
				}
				catch (IOException e)
				{
					// nothing to do
				}
			}

		}
		return output.toString();
	}
}
