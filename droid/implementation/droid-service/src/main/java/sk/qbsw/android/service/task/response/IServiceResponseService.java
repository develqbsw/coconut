package sk.qbsw.android.service.task.response;


/**
 * service which create {@link CServiceResponse} response
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public interface IServiceResponseService
{
	/**
	 * Create {@link CServiceResponse} with default success parameters
	 * @return
	 */
	@SuppressWarnings ("rawtypes")
	public CServiceResponse create ();
	
	/**
	 * Create {@link CServiceResponse} with database error parameters
	 * @return
	 */
	@SuppressWarnings ("rawtypes")
	public CServiceResponse databaseError ();
	
	/**
	 * Create {@link CServiceResponse} with unspecified error parameters
	 * @return
	 */
	@SuppressWarnings ("rawtypes")
	public CServiceResponse otherError ();
	
	/**
	 * Create {@link CServiceResponse} with network not available error parameters
	 * @return
	 */
	@SuppressWarnings ("rawtypes")
	public CServiceResponse networkNotAvailableError ();
	
	/**
	 * Create {@link CServiceResponse} with thread interrupted error parameters
	 * @return
	 */
	@SuppressWarnings ("rawtypes")
	public CServiceResponse threadInterruptedError ();
	
	/**
	 * Create {@link CServiceResponse} with network not available for big data transfer error parameters
	 * @return
	 */
	@SuppressWarnings ("rawtypes")
	public CServiceResponse bigDataNetworkNotAvailableError ();
}
