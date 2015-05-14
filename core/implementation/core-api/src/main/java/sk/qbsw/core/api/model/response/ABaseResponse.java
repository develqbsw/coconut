package sk.qbsw.core.api.model.response;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import sk.qbsw.core.api.client.jackson.serializer.CDateJSonSerializer;

/**
 * Base response for API methods.
 * 
 * @param <T>
 *            the generic type for response
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.2.0
 */
public class ABaseResponse
{

	/**
	 * Indicates that call was finished with error.
	 */
	private boolean hasError = false;

	/**
	 * Error code. 0 is alternative to no error.
	 */
	private int errorCode = 0;

	/** Date of response. */
	@JsonSerialize (using = CDateJSonSerializer.class)
	private Date responseDate;

	/**
	 * Instantiates a new a base response.
	 */
	public ABaseResponse ()
	{

	}

	/**
	 * Checks if is checks for error.
	 * 
	 * @return true, if is checks for error
	 */
	public boolean isHasError ()
	{
		return hasError;
	}

	/**
	 * Sets the checks for error.
	 * 
	 * @param hasError
	 *            the new checks for error
	 */
	public void setHasError (boolean hasError)
	{
		this.hasError = hasError;
	}

	/**
	 * Gets the error code.
	 * 
	 * @return the error code
	 */
	public int getErrorCode ()
	{
		return errorCode;
	}

	/**
	 * Sets the error code.
	 * 
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode (int errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * Gets the response date.
	 * 
	 * @return the response date
	 */
	public Date getResponseDate ()
	{
		return responseDate;
	}

	/**
	 * Sets the response date.
	 * 
	 * @param responseDate
	 *            the new response date
	 */
	public void setResponseDate (Date responseDate)
	{
		this.responseDate = responseDate;
	}

}
