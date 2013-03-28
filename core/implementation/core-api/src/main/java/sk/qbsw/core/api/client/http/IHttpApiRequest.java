package sk.qbsw.core.api.client.http;

import org.apache.http.entity.ContentType;

/**
 * HTTP Client for API calls
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.2.0
 * 
 */
public interface IHttpApiRequest
{
	/**
	 * Sets repeat count
	 * @param repeat
	 */
	public void setRepeatCount(int repeat);

	/**
	 * Timeout of HTTP request in milisecond
	 * @param timout
	 */
	public void setTimeout(int timout);
	
	/**
	 * Parameter for the content
	 */
	public void setContentParameter (String parameterName);

	/**
	 * Makes the HTTP call. Response will be returned
	 * 
	 * @param url
	 *            URL to call
	 * @param contentType
	 *            content Type for call
	 * @param entity
	 *            Entity used as parameter
	 * @return Response as String(may be used for further parsing)
	 */
	public String makeCall (String url, ContentType contentType, String entity);
}
