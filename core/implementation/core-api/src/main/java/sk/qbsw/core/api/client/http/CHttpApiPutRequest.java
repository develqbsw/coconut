package sk.qbsw.core.api.client.http;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * PUT client for API call.
 * 
 * @author Dalibor Rak
 * @author Michal Lacko
 * @author Tomas Lauro
 * 
 * @version 1.12.1
 * @since 1.3.0
 */
public class CHttpApiPutRequest extends AHttpApiRequest implements IHttpApiRequest
{
	/**
	 * Makes HTTP PUT call.
	 * 
	 * @param url the url to the api
	 * @param contentType the content type
	 * @param entityInJSon the entity in JSON
	 * @param characterEncoding encoding of the content
	 * @return response content
	 */
	public String makeOneCall (String url, ContentType contentType, String entityInJSon) throws IOException
	{
		HttpClient httpClient = createHttpClient();
		applyTimeouts(httpClient);
		applyProxy(httpClient);

		HttpPut putRequest = new HttpPut(url);
		putRequest.addHeader("accept", contentType.getMimeType());

		if (getHeaders() != null)
		{
			for (Entry<String, String> headerItem : getHeaders().entrySet())
			{
				putRequest.addHeader(headerItem.getKey(), headerItem.getValue());
			}
		}

		if (entityInJSon != null)
		{
			StringEntity input = new StringEntity(entityInJSon, contentType.getCharset().name());
			input.setContentType(contentType.toString());
			putRequest.setEntity(input);
		}

		HttpResponse response = httpClient.execute(putRequest);

		String content = super.getEntityContent(response);

		// 2XX response is correct
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode < 200 || responseCode > 299)
		{
			throw new CApiHttpException("Failed : HTTP error code:" + response.getStatusLine().getStatusCode(), null, response.getStatusLine().getStatusCode(), content);
		}

		httpClient.getConnectionManager().shutdown();

		return content;
	}

	/**
	 * Sets parameter for content.
	 * The content will send as Entity in the POST request.
	 */
	public void setContentParameter (String parameterName)
	{
		// nothing to do
	}
}
