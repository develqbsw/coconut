package sk.qbsw.android.service.listener;

import sk.qbsw.android.service.task.response.CServiceResponse;

/**
 * Listener which is called when service send response and is still running
 * @author Michal Lacko
 * @since 0.4.0
 * @version 0.1.0
 * 
 * @param <T> type of service response 
 */
public interface  IOnServiceProgressListener<T>
{
	/**
	 * method is called when service progressing and do one step(listener must be explicitly called from service)
	 * @param result response from service which come when service progressing
	 */
	public void onProgress (CServiceResponse<T> response);
}
