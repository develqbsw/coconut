package sk.qbsw.android.service.task.response;



/**
 * service implements {@link IServiceResponseService} and create CServiceResponse
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public class CServiceResponseService implements IServiceResponseService
{

	@SuppressWarnings ("rawtypes")
	@Override
	public CServiceResponse create ()
	{
		CServiceResponse serviceResponse = new CServiceResponse();
		serviceResponse.setError(Boolean.FALSE);
		serviceResponse.setTaskResult(ETaskResponseResult.OK.name());
		serviceResponse.setDataChanged(Boolean.FALSE);
		return serviceResponse;
	}
	
	@SuppressWarnings ("rawtypes")
	@Override
	public CServiceResponse databaseError ()
	{
		CServiceResponse serviceResponse = new CServiceResponse();
		serviceResponse.setError(Boolean.TRUE);
		serviceResponse.setTaskResult(ETaskResponseResult.DATABASE_ERROR.name());
		serviceResponse.setDataChanged(Boolean.FALSE);
		return serviceResponse;
	}

	@SuppressWarnings ("rawtypes")
	@Override
	public CServiceResponse otherError ()
	{
		CServiceResponse serviceResponse = new CServiceResponse();
		serviceResponse.setError(Boolean.TRUE);
		serviceResponse.setTaskResult(ETaskResponseResult.OTHER_ERROR.name());
		serviceResponse.setDataChanged(Boolean.FALSE);
		return serviceResponse;
	}
	
	@SuppressWarnings ("rawtypes")
	@Override
	public CServiceResponse networkNotAvailableError ()
	{
		CServiceResponse serviceResponse = new CServiceResponse();
		serviceResponse.setError(Boolean.TRUE);
		serviceResponse.setTaskResult(ETaskResponseResult.NETWORK_NOT_AVAILABLE.name());
		serviceResponse.setDataChanged(Boolean.FALSE);
		return serviceResponse;
	}
	
	@SuppressWarnings ("rawtypes")
	@Override
	public CServiceResponse threadInterruptedError ()
	{
		CServiceResponse serviceResponse = new CServiceResponse();
		serviceResponse.setError(Boolean.TRUE);
		serviceResponse.setTaskResult(ETaskResponseResult.THREAD_INTERRUPTED.name());
		return serviceResponse;
	}

	@SuppressWarnings ("rawtypes")
	@Override
	public CServiceResponse bigDataNetworkNotAvailableError ()
	{
		CServiceResponse serviceResponse = new CServiceResponse();
		serviceResponse.setError(Boolean.TRUE);
		serviceResponse.setTaskResult(ETaskResponseResult.BIG_DATA_NETWORK_NOT_AVAILABLE.name());
		serviceResponse.setDataChanged(Boolean.FALSE);
		return serviceResponse;
	}

}
