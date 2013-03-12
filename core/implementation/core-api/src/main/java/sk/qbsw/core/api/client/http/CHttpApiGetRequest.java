package sk.qbsw.core.api.client.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.InvalidParameterException;

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
 * @version 1.3.0
 * @since 1.2.0
 */
public class CHttpApiGetRequest extends AHttpApiRequest implements IHttpApiRequest
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
	protected String makeOneCall (String url, ContentType contentType, String entity)
	{
		InputStreamReader inputReader = null;
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();


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


			HttpGet getRequest = new HttpGet(fullURL);
			getRequest.addHeader("accept", contentType.getMimeType());

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200)
			{
				throw new CApiHttpException("Failed : HTTP error code:" + response.getStatusLine().getStatusCode(), null, response.getStatusLine().getStatusCode());
			}
			inputReader = new InputStreamReader( (response.getEntity().getContent()));
			BufferedReader br = new BufferedReader(inputReader);

			// reads output
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
		catch (ClientProtocolException e)
		{
			throw new RuntimeException("Client protocol exception", e);
		}
		catch (IOException e)
		{
			throw new RuntimeException("IO exception", e);
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
	 * Sets content parameter (parameter using which will be the entity transfered)
	 */
	public void setContentParameter (String parameterName)
	{
		this.contentParameter = parameterName;
	}
}
