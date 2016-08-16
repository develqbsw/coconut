package sk.qbsw.core.api.model.response;

import javax.validation.Valid;

/**
 * The base response.
 *
 * @param <T> the response data type
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since   1.16.0
 */
public class ABaseResponse<T> implements IBaseResponse<T>
{
	/** The data. */
	@Valid
	private T data;

	/** The error. */
	@Valid
	private CResponseError error;

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.client.response.IBaseResponse#getData()
	 */
	@Override
	public T getData ()
	{
		return data;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.client.response.IBaseResponse#setData(java.lang.Object)
	 */
	@Override
	public void setData (T data)
	{
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.client.response.IBaseResponse#getError()
	 */
	@Override
	public CResponseError getError ()
	{
		return error;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.client.response.IBaseResponse#setError(sk.qbsw.et.browser.client.response.CResponseError)
	 */
	@Override
	public void setError (CResponseError error)
	{
		this.error = error;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.client.response.IBaseResponse#validate()
	 */
	@Override
	public boolean validate ()
	{
		return true;
	}
}
