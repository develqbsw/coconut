package sk.qbsw.android.service.task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sk.qbsw.android.CQBSWAndroidLoggerTag;
import sk.qbsw.android.exception.CExceptionHandler;
import sk.qbsw.android.service.parent.model.CServiceRequestModel;
import sk.qbsw.android.service.task.CTaskService;
import sk.qbsw.android.service.task.response.CServiceResponse;
import sk.qbsw.android.service.task.response.CTaskResponseModel;
import sk.qbsw.android.service.task.response.ETaskResponseResult;
import sk.qbsw.android.utils.CNetwork;
import android.content.Context;
import android.util.Log;

/**
 * Service to send requests with CApiClient to API webservices
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.2.0
 *
 * @param <T> - type of content of request model which is send to server - data which are parsed by json and send to server
 * @param <D> - type of request data which are send to server
 * @param <P> - type of response of content which is get from server
 * @param <M> - type of response model of content which is get from server
 */
public abstract class CWebService<T extends Serializable> extends CTaskService<T, Object>
{
	/**
	 * if is this parameter set to true then send request only when is 3g network available or wifi available
	 */
	private Boolean sendBigData;

	public CWebService (Context context)
	{
		super(context);
		this.sendBigData = Boolean.FALSE;
	}

	/**
	 * Execute request to webServiceAPI
	 * Open new thread to call webClient to send request
	 * @param requestModel model which is send to request
	 */
	protected abstract Object callWebApi (CServiceRequestModel<T> requestModel) throws Exception;

	@SuppressWarnings ("unchecked")
	//because serviceResponseService create default Service response Object
	/**
	 * this method call request to send notification to server - onFinish method is called when request is finished 
	 */
	@Override
	protected CServiceResponse<Object> call (CServiceRequestModel<T> requestModel)
	{

		CServiceResponse<Object> serviceResponse;
		//if is netwrk available then execute task on server else execute task on database
		if (CNetwork.isNetworkAvailable(getContext()))
		{
			if (!this.sendBigData || this.sendBigData && (CNetwork.is3GAvailable(getContext()) || CNetwork.isWifiAvailable(getContext())))
			{
				CTaskResponseModel<Object> taskResponseModel = new CTaskResponseModel<Object>();
				taskResponseModel.setResult(ETaskResponseResult.OK.name());

				try
				{
					//web API response nothing
					Object responseModel = callWebApi(requestModel);
					taskResponseModel.setContent(responseModel);
				}
				catch (Throwable e)
				{
					Calendar calendar = Calendar.getInstance();

					String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(calendar.getTime());

					Log.e(CQBSWAndroidLoggerTag.TAG, " URL Exception: timeStamp:" + timeStamp + " time:" + calendar.getTimeInMillis());


					//Exception when serverTimeout
					CExceptionHandler.logException(this.getClass(), e);

					taskResponseModel.setResult(ETaskResponseResult.SERVER_TIMEOUT.name());
				}

				serviceResponse = postExecuteWebRequest(taskResponseModel);
				//to give chance to modify web request child of this class
				serviceResponse = postExecuteWebRequest(serviceResponse);
			}
			else
			{
				serviceResponse = CWebService.this.getServiceResponseService().bigDataNetworkNotAvailableError();
			}
		}
		else
		{
			serviceResponse = CWebService.this.getServiceResponseService().networkNotAvailableError();
		}

		return serviceResponse;

	}

	@SuppressWarnings ("unchecked")
	//because serviceResponseService create default Service response Object
	private CServiceResponse<Object> postExecuteWebRequest (CTaskResponseModel<Object> responseModel)
	{
		CServiceResponse<Object> serviceResponse = null;
		//if is response ok and isn't timeout from server

		serviceResponse = CWebService.this.getServiceResponseService().create();
		//check if is error

		Boolean error = (!ETaskResponseResult.OK.name().equals(responseModel.getResult()));

		//set response from service
		serviceResponse.setError(error);
		serviceResponse.setTaskResult(responseModel.getResult());

		if (responseModel.getContent() != null)
		{
			Object responseModelContent = responseModel.getContent();
			serviceResponse.setContent(responseModelContent);
		}

		return serviceResponse;

	}

	/**
	 * <b>Method for overriding and modify service response after request is call</b>
	 * this method in default do not modify response only simply return what get as parameter
	 * @param serviceResponse response which is result from service after request is call
	 * @return modified response from service 
	 */
	protected CServiceResponse<Object> postExecuteWebRequest (CServiceResponse<Object> serviceResponse)
	{
		return serviceResponse;
	}

	public Boolean getSendBigData ()
	{
		return sendBigData;
	}

	public void setSendBigData (Boolean sendBigData)
	{
		this.sendBigData = sendBigData;
	}
}
