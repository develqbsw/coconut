package sk.qbsw.android.service.task.response;

import java.io.Serializable;

/**
 * Model which include variable to cancel current running service
 * @author Michal Lacko
 * @since 0.3.0
 * @version 0.1.0
 */
public class CServiceCancelModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5679615376244189131L;
	/**
	 * attribute which represent cancelation of service
	 */
	private Boolean isCanceled;

	public Boolean getIsCanceled ()
	{
		return isCanceled;
	}

	public void setIsCanceled (Boolean isCanceled)
	{
		this.isCanceled = isCanceled;
	} 
}
