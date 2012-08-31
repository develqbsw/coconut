package sk.qbsw.core.api.client.http;

import org.apache.http.entity.ContentType;

/**
 * HTTP Client for API calls
 * 
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.2.0
 * 
 */
public interface IHttpApiRequest
{

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
