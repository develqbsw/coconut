/*
 * 
 */
package sk.qbsw.core.api.client.http;

import org.apache.http.entity.ContentType;

import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * HttpAPIReqpest repeater support.
 * 
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.3.0
 */
public abstract class AHttpApiRequest implements IHttpApiRequest
{
	/** The repeat count. */
	private int repeatCount = 1;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.http.IHttpApiRequest#setRepeatCount(int)
	 */
	@Override
	public void setRepeatCount (int repeat)
	{
		this.repeatCount = repeat;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.http.IHttpApiRequest#makeCall(java.lang.String, org.apache.http.entity.ContentType, java.lang.String)
	 */
	public final String makeCall (String url, ContentType contentType, String entityInJSon)
	{
		CApiHttpException lastEx = null;

		for (int counter = 0; counter < repeatCount; counter++)
		{
			try
			{
				// try call
				return makeOneCall(url, contentType, entityInJSon);
			}
			catch (CApiHttpException ex)
			{
				lastEx = ex;
			}
		}

		// throws last exception
		throw new CApiHttpException("Repeated call failed", lastEx, lastEx.getHttpErrorCode());
	}

	/**
	 * Make one call of API request.
	 *
	 * @param url the url
	 * @param contentType the content type
	 * @param entityInJSon the entity in j son
	 * @return the string
	 */
	protected abstract String makeOneCall (String url, ContentType contentType, String entityInJSon);
}
