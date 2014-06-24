package sk.qbsw.android.integration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;

import sk.qbsw.core.api.client.http.AHttpApiRequest;
import sk.qbsw.core.api.client.http.IHttpApiRequest;
import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * Base HTTP client for get requests.
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.2.0
 */
public class CHttpApiGetFileRequest extends AHttpApiRequest implements IHttpApiRequest
{

	/**
	 * file where is content saved
	 */
	private File fileToSave;

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
	protected String makeOneCall (String url, ContentType contentType, String entity, Map<String, String> headers) throws IOException
	{
		//Stream used for reading the data from the internet
		InputStream inputStream = null;

		//create a buffer...
		byte[] buffer;
		int bufferLength = 0;

		this.fileToSave = File.createTempFile("qbsw_integration", ".temp");

		FileOutputStream fileOutput = new FileOutputStream(fileToSave);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		applyProxy(httpClient);
		applyTimeouts(httpClient);
		try
		{
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
				String content = super.getEntityContent(response);
				throw new CApiHttpException("Failed : HTTP error code:" + response.getStatusLine().getStatusCode(), null, response.getStatusLine().getStatusCode(), content);
			}

			buffer = new byte[1024];

			inputStream = response.getEntity().getContent();
			while ( (bufferLength = inputStream.read(buffer)) > 0)
			{
				fileOutput.write(buffer, 0, bufferLength);
			}
			//close the output stream when complete
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
				fileOutput.close();
			}
		}

		httpClient.getConnectionManager().shutdown();

		return null;
	}

	/**
	 * Sets content parameter (parameter using which will be the entity transfered)
	 */
	public void setContentParameter (String parameterName)
	{
		this.contentParameter = parameterName;
	}

	public File getFileToSave ()
	{
		return fileToSave;
	}
}
