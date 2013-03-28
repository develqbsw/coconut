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
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * Post client for API call.
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.2.0
 */
public class CHttpApiPostRequest extends AHttpApiRequest implements IHttpApiRequest
{

	/**
	 * Makes HTTP POST call.
	 * 
	 * @param url the url to the api
	 * @param contentType the content type
	 * @param entityInJSon the entity in JSON
	 * @param characterEncoding encoding of the content
	 * @return response content
	 */
	public String makeOneCall (String url, ContentType contentType, String entityInJSon)
	{
		InputStreamReader inputReader = null;
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpParams params = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, getTimeout());
			HttpConnectionParams.setSoTimeout(params, getTimeout());

			HttpPost postRequest = new HttpPost(url);
			postRequest.addHeader("accept", contentType.getMimeType());
			if (entityInJSon != null)
			{
				StringEntity input = new StringEntity(entityInJSon, contentType.getCharset().name());
				input.setContentType(contentType.toString());
				postRequest.setEntity(input);
			}

			HttpResponse response = httpClient.execute(postRequest);

			// 2XX response is correct
			int responseCode = response.getStatusLine().getStatusCode();
			if (responseCode < 200 || responseCode > 299)
			{
				throw new CApiHttpException("Failed : HTTP error code:" + response.getStatusLine().getStatusCode(), null, response.getStatusLine().getStatusCode());
			}

			inputReader = new InputStreamReader( (response.getEntity().getContent()));
			BufferedReader br = new BufferedReader(inputReader);

			StringBuffer output = new StringBuffer();
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
