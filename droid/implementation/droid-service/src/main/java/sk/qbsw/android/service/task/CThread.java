package sk.qbsw.android.service.task;

import sk.qbsw.android.service.listener.IOnServiceFinishListener;
import sk.qbsw.android.service.listener.IOnServiceProgressListener;
import sk.qbsw.android.service.task.response.CServiceResponse;
import sk.qbsw.android.service.task.response.CServiceResponseService;
import sk.qbsw.android.service.task.response.IServiceResponseService;
import android.content.Context;
import android.os.AsyncTask;

/**
 * this class represent request which is send to API webService in new thread
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.4.0
 * 
 * <T> type of content of serviceResponse
 *
 */
@SuppressWarnings ("rawtypes")
// because this is universal class
public class CThread<T> extends AsyncTask<CTaskService, CServiceResponse<T>, CServiceResponse<T>>
{

	IServiceResponseService serviceResponseService;

	/**
	 * listener which is called after service end
	 */
	private IOnServiceFinishListener onServiceFinishListener;

	/**
	 * listener which is called after service end
	 */
	private IOnServiceProgressListener onServiceProgressListenner;

	public CThread (Context applicationContext, IOnServiceFinishListener onServiceFinishListener)
	{
		super();
		this.onServiceFinishListener = onServiceFinishListener;

		//create serviceResponseService
		this.serviceResponseService = new CServiceResponseService();
	}

	public CThread (Context applicationContext, IOnServiceFinishListener onServiceFinishListener, IOnServiceProgressListener onServiceProgressListenner)
	{
		super();
		this.onServiceFinishListener = onServiceFinishListener;
		this.onServiceProgressListenner = onServiceProgressListenner;

		//create serviceResponseService
		this.serviceResponseService = new CServiceResponseService();
	}

	@SuppressWarnings ("unchecked")
	//because listener return universal value
	@Override
	protected CServiceResponse doInBackground (CTaskService... params)
	{
		CServiceResponse serviceResponse = null;
		try
		{
			//this method is called always only with one param
			CTaskService webService = params[0];

			webService.setOnServiceProgressListenner(new IOnServiceProgressListener<T>()
			{

				@Override
				public void onProgress (CServiceResponse<T> response)
				{
					publishProgress(response);

				}
			});

			serviceResponse = webService.execute();

		}
		catch (Throwable e)
		{
			//when exception is throw then return false response
			serviceResponse = this.serviceResponseService.threadInterruptedError();
		}
		
		//and always return response
		return serviceResponse;
	}

	//because work with universal method
	@SuppressWarnings ("unchecked")
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

	@SuppressWarnings ("unchecked")
	//because work with universal method
	@Override
	protected void onProgressUpdate (CServiceResponse<T>... value)
	{

		if (this.onServiceProgressListenner != null)
		{
			this.onServiceProgressListenner.onProgress(value[0]);
		}

		super.onProgressUpdate(value);
	};



}
