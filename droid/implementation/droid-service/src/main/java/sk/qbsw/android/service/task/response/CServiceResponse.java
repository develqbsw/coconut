package sk.qbsw.android.service.task.response;

import java.io.Serializable;


/**
 * Class represent response for service
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 *
 * @param <T> type of content in response
 */
public class CServiceResponse<T> implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * length of items in service
	 */
	private int length;

	/**
	 * count of items in service
	 */
	private int count;

	/**
	 * content - response from service
	 */
	private T content;

	/**
	 * indicies when is error in service
	 */
	private Boolean error;

	/**
	 * error message from thread
	 */
	private String taskResult;
	
	/**
	 * show type of response ussually is null
	 */
	private String type;

	/**
	 * when data from service change data in mobile
	 */
	private Boolean dataChanged;

	public CServiceResponse ()
	{
		this.length = 0;
		this.count = 0;
	}

	public T getContent ()
	{
		return content;
	}

	public void setContent (T content)
	{
		this.content = content;
	}

	public Boolean getError ()
	{
		return error;
	}

	public void setError (Boolean error)
	{
		this.error = error;
	}

	public String getTaskResult ()
	{
		return taskResult;
	}

	public void setTaskResult (String taskResult)
	{
		this.taskResult = taskResult;
	}

	public Boolean getDataChanged ()
	{
		return dataChanged;
	}

	public void setDataChanged (Boolean dataChanged)
	{
		this.dataChanged = dataChanged;
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

	public String getType ()
	{
		return type;
	}

	public void setType (String type)
	{
		this.type = type;
	}

}
