package sk.qbsw.android.service.task.response;



/**
 * this class represent result from activity on background which is connected to webservice
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 *
 * @param <T> content to send from asyncTask
 */
public class CTaskResponseModel<T>
{

	private String result;
	private T content;

	public CTaskResponseModel ()
	{
		super();
		result = ETaskResponseResult.SERVER_TIMEOUT.name();
	}

	public String getResult ()
	{
		return result;
	}

	public void setResult (String result)
	{
		this.result = result;
	}

	public T getContent ()
	{
		return content;
	}

	public void setContent (T content)
	{
		this.content = content;
	}

}
