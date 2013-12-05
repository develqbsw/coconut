package sk.qbsw.android.service.listener;

import sk.qbsw.android.service.task.response.CServiceResponse;

/**
 * Listener which is called when service which run new thread end
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.3.0
 */
public interface IOnServiceFinishListener<T>
{
	/**
	 * method is called when service end
	 * @param result response from service which come when service end
	 */
	public abstract void onFinish (CServiceResponse<T> result);
}
