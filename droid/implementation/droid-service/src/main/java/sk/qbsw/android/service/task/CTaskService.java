package sk.qbsw.android.service.task;

import java.io.Serializable;

import sk.qbsw.android.service.listener.IOnServiceProgressListener;
import sk.qbsw.android.service.parent.CService;
import sk.qbsw.android.service.parent.model.CServiceRequestModel;
import sk.qbsw.android.service.task.response.CServiceResponse;
import sk.qbsw.android.service.task.response.CServiceResponseService;
import sk.qbsw.android.service.task.response.IServiceResponseService;
import android.content.Context;

/**
 * Service to send requests with CApiClient to API webservices
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.2.0
 *
 * @param <T> - type of content of request model which is send to server - data which are parsed by json and send to server
 * @param <P> - type of response of content which is get from server
 */
public abstract class CTaskService<T extends Serializable, P> extends CService
{

	/**
	 * service to createServiceResponse
	 */
	private IServiceResponseService serviceResponseService;

	private T requestData;

	private IOnServiceProgressListener<P> onServiceProgressListenner;

	public CTaskService (Context context)
	{
		super(context);
		this.serviceResponseService = new CServiceResponseService();
	}

	/**
	 * Execute request to webServiceAPI
	 * Open new thread to call webClient to send request
	 * @param requestModel model which is send to request
	 */
	protected abstract CServiceResponse<P> call (CServiceRequestModel<T> requestModel);

	/**
	 * this method call request to send notification to server - onFinish method is called when request is finished 
	 */
	private CServiceResponse<P> executeRequest (T requestData)
	{
		this.requestData = requestData;

		CServiceRequestModel<T> requestModel = getFilledRequestModel(this.requestData);

		//web API response nothing
		return call(requestModel);
	}

	/**
	 * execute service
	 * @return model which represent response from webService or null if is request model null
	 */
	public CServiceResponse<P> execute ()
	{
		CServiceResponse<P> serviceResponse = null;

		//if isnt set model which is end to server then return null
		if (this.requestData != null)
		{
			serviceResponse = this.executeRequest(this.requestData);
		}

		return serviceResponse;
	};

	public T getRequestData ()
	{
		return requestData;
	}

	public void setRequestData (T requestData)
	{
		this.requestData = requestData;
	}

	public IOnServiceProgressListener<P> getOnServiceProgressListenner ()
	{
		return onServiceProgressListenner;
	}

	public void setOnServiceProgressListenner (IOnServiceProgressListener<P> onServiceProgressListenner)
	{
		this.onServiceProgressListenner = onServiceProgressListenner;
	}

	/**
	 * method which can be called to send response to caller
	 * @param response service response which is send to caller
	 */
	protected void responseServiceProgress (CServiceResponse<P> response)
	{
		if (this.onServiceProgressListenner != null)
		{
			this.onServiceProgressListenner.onProgress(response);
		}
	};

	public IServiceResponseService getServiceResponseService ()
	{
		return serviceResponseService;
	}

	/**
	 * get requestModel which is proxy for send data to server
	 * 
	 * <b>this method can be overloaded to send another proxy model to server<b>
	 * do't add content to model because this service replace then with data which cone from call method
	 *  
	 * @return request model which is proxy
	 */
	private CServiceRequestModel<T> getRequestModel ()
	{
		CServiceRequestModel<T> requestModel = new CServiceRequestModel<T>();
		return requestModel;
	}

	/**
	 * add content to request
	 * @return
	 */
	private CServiceRequestModel<T> getFilledRequestModel (T content)
	{
		CServiceRequestModel<T> requestModel = getRequestModel();
		//create request model which is send into server
		requestModel.setContent(content);

		return requestModel;

	}

}
