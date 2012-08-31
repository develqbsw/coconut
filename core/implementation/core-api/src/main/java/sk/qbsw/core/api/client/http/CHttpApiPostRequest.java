package sk.qbsw.core.api.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * Post client for API call.
 * 
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.2.0
 */
public class CHttpApiPostRequest implements IHttpApiRequest
{

	/**
	 * Makes HTTP POST call.
	 * 
	 * @param url
	 *            the url to the api
	 * @param contentType
	 *            the content type
	 * @param entityInJSon
	 *            the entity in JSON
	 * @return response content
	 */
	public String makeCall (String url, ContentType contentType, String entityInJSon)
	{
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(url);
			postRequest.addHeader("accept", contentType.getMimeType());

			if (entityInJSon != null)
			{
				StringEntity input = new StringEntity(entityInJSon);
				input.setContentType(contentType.getMimeType());
				postRequest.setEntity(input);
			}

			HttpResponse response = httpClient.execute(postRequest);

			// 2XX response is correct
			int responseCode = response.getStatusLine().getStatusCode();
			if (responseCode < 200 || responseCode > 299)
			{
				throw new CApiHttpException("Failed : HTTP error code:" + response.getStatusLine().getStatusCode(), null, response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader( (response.getEntity().getContent())));

			StringBuffer output = new StringBuffer();
			String line;
			while ( (line = br.readLine()) != null)
			{
				output.append(line);
			}

			httpClient.getConnectionManager().shutdown();

			return output.toString();
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException("Malformed URL", e);
		}
		catch (IOException e)
		{
			throw new RuntimeException("IOException", e);
		}
	}
}
