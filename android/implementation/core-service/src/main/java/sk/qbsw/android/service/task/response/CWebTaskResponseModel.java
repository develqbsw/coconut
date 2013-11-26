package sk.qbsw.android.service.task.response;



/**
 * this class represent result from activity on background which is connected to webservice
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 *
 * @param <T> content to send from asyncTask
 */
public class CWebTaskResponseModel<T>
{

	private int length;
	private int count;
	private ETaskResponseResult result;
	private T content;

	public CWebTaskResponseModel ()
	{
		super();
		length = 0;
		count = 0;
		result = ETaskResponseResult.SERVER_TIMEOUT;
	}

	public int getLength ()
	{
		return length;
	}

	public void setLength (int length)
	{
		this.length = length;
	}

	public int getCount ()
	{
		return count;
	}

	public void setCount (int count)
	{
		this.count = count;
	}

	public ETaskResponseResult getResult ()
	{
		return result;
	}

	public void setResult (ETaskResponseResult result)
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
