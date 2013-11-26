package sk.qbsw.android.integration.response;

import java.io.Serializable;


/** 
 * response which is getted from webservice
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 * 
 * @param <T> content send from webservice
 */
public class CServiceResponseModel<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7020266551680783148L;

	/**
	 * error code (if error not occurred then this value is null)
	 */	
	private String errorCode;
	
	/**
	 * data which are send to device
	 */
	private T content;

	public T getContent ()
	{
		return content;
	}

	public void setContent (T dataToSend)
	{
		this.content = dataToSend;
	}

	public String getErrorCode ()
	{
		return errorCode;
	}

	public void setErrorCode (String errorCode)
	{
		this.errorCode = errorCode;
	}
}
