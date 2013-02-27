package sk.qbsw.sgwt.winnetou.client.ui.event.processing;

import java.io.Serializable;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event fired when screen data is loaded
 * 
 * @author Dalibor Rak
 * 
 */
public class CLoadingDataFinishedEvent extends AApplicationEvent
{
	/**
	 * Data relative to loaded event
	 */
	private Serializable relatedData;

	public CLoadingDataFinishedEvent(IApplicationEventSource source)
	{
		super(source);
	}

	public String getDescription()
	{
		return "Loading data stopped";
	}

	public void setRelatedData(Serializable relatedData)
	{
		this.relatedData = relatedData;
	}

	public Serializable getRelatedData()
	{
		return relatedData;
	}

	/**
	 * Converts related data to Long
	 * @return
	 */
	public Long getRelatedDataAsLong()
	{

		if (relatedData == null)
		{
			return null;
		}
		return (Long) relatedData;
	}

}
