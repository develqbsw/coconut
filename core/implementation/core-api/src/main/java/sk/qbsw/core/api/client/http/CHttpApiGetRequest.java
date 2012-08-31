package sk.qbsw.core.api.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;

import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * Base HTTP client for get requests.
 * 
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.2.0
 */
public class CHttpApiGetRequest implements IHttpApiRequest
{

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
	 */
	public String makeCall (String url, ContentType contentType, String entity)
	{

		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", contentType.getMimeType());

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200)
			{
				throw new CApiHttpException("Failed : HTTP error code:" + response.getStatusLine().getStatusCode(), null, response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader( (response.getEntity().getContent())));

			// reads output
			StringBuffer output = new StringBuffer();
			String line;
			while ( (line = br.readLine()) != null)
			{
				output.append(line);
			}

			httpClient.getConnectionManager().shutdown();

			return output.toString();
		}
		catch (ClientProtocolException e)
		{
			throw new RuntimeException("Client protocol exception", e);
		}
		catch (IOException e)
		{
			throw new RuntimeException("IO exception", e);
		}
	}
}
