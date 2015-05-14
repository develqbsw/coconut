package sk.qbsw.android.service.task;

import java.io.Serializable;

import sk.qbsw.android.service.listener.IOnServiceFinishListener;
import sk.qbsw.android.service.task.response.CServiceResponse;
import android.content.Context;
import android.os.AsyncTask;

/**
 * this class represent request which is send to API webService in new thread
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.3.0
 * 
 * <T> type of content of serviceResponse
 *
 */
@SuppressWarnings ("rawtypes")
// because this is universal class
public class CWebThread<T extends Serializable> extends AsyncTask<CWebService, CServiceResponse<T>, CServiceResponse<T>>
{

	/**
	 * listener which is called after service end
	 */
	private IOnServiceFinishListener onServiceFinishListener;

	public CWebThread (Context applicationContext, IOnServiceFinishListener onServiceFinishListener)
	{
		super();
		this.onServiceFinishListener = onServiceFinishListener;
	}

	@Override
	protected CServiceResponse doInBackground (CWebService... params)
	{
		//this method is called always only with one param
		CWebService webService = params[0];

		CServiceResponse serviceResponse = webService.execute();

		//and always return response
		return serviceResponse;
	}

	@SuppressWarnings ("unchecked") // because this is universal class
	@Override
	protected void onPostExecute (CServiceResponse<T> result)
	{
		//if is listener not null call listener
		if (this.onServiceFinishListener != null)
		{
			this.onServiceFinishListener.onFinish(result);
		}
		super.onPostExecute(result);
	}


}
