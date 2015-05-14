package sk.qbsw.android.service.task.response;


/**
 * List of available request statuses
 * 
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 */
public enum ETaskResponseResult
{
	OK ()
	{
	},
	//errors
	SERVER_TIMEOUT ()
	{
	},
	THREAD_INTERRUPTED ()
	{
	},
	DATABASE_ERROR ()
	{
	},
	OTHER_ERROR ()
	{
	},
	NETWORK_NOT_AVAILABLE ()
	{
	},
	SERVICE_CANCELED ()
	{
	},
	BIG_DATA_NETWORK_NOT_AVAILABLE ()
	{
	};
}
