package sk.qbsw.android.service.parent.model;

import java.io.Serializable;

/**
 * Parent model for request to service
 * @author Michal Lacko
 *
 * @param <T> content which is send with request
 */
public class CServiceRequestModel<T extends Serializable> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 371856677777065222L;

	private T content;

	public T getContent ()
	{
		return content;
	}

	public void setContent (T content)
	{
		this.content = content;
	}
	
}
