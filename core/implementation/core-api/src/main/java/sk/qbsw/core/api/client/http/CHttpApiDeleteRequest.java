package sk.qbsw.core.api.client.http;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;

import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * Base HTTP client for DELETE requests.
 * 
 * @author Dalibor Rak
 * @author Michal Lacko
 * @version 1.10.0
 * @since 1.3.0
 */
public class CHttpApiDeleteRequest extends AHttpApiRequest implements IHttpApiRequest
{

	/**
	 * Parameter name for content. Default value is payload
	 */
	private String contentParameter = "payload";

	/**
	 * Makes call.
	 * 
	 * @param url
	 *            the url to the API
	 * @param contentType
	 *            the content type the response will accept
	 * @param entity
	 *            the entity to send in request
	 * @return response from the HTTP call
	 * @throws CApiHttpException unsuccessful API call
	 */
	protected String makeOneCall (String url, ContentType contentType, String entity) throws IOException
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		applyProxy(httpClient);
		applyTimeouts(httpClient);

		String fullURL = url;
		if (entity != null)
		{
			if (contentParameter != null)
			{
				fullURL = url + "?" + contentParameter + "=" + URLEncoder.encode(entity, "UTF-8");
			}
			else
			{
				throw new InvalidParameterException("Content parameter not set");
			}
		}

		HttpDelete deleteRequest = new HttpDelete(fullURL);
		deleteRequest.addHeader("accept", contentType.getMimeType());

		if (getHeaders() != null)
		{
			for (Entry<String, String> headerItem : getHeaders().entrySet())
			{
				deleteRequest.addHeader(headerItem.getKey(), headerItem.getValue());
			}
		}

		HttpResponse response = httpClient.execute(deleteRequest);

		String content = super.getEntityContent(response);

		if (response.getStatusLine().getStatusCode() != 200)
		{
			throw new CApiHttpException("Failed : HTTP error code:" + response.getStatusLine().getStatusCode(), null, response.getStatusLine().getStatusCode(), content);
		}


		httpClient.getConnectionManager().shutdown();

		return content;
	}

	/**
	 * Sets content parameter (parameter using which will be the entity transfered)
	 */
	public void setContentParameter (String parameterName)
	{
		this.contentParameter = parameterName;
	}
}
