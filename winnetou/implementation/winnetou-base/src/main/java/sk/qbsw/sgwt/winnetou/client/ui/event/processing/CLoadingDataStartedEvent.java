package sk.qbsw.sgwt.winnetou.client.ui.event.processing;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event fired when screen data is loading
 * 
 * @author Dalibor Rak
 * 
 */
public class CLoadingDataStartedEvent extends AApplicationEvent
{
	public CLoadingDataStartedEvent(IApplicationEventSource source)
	{
		super(source);
	}

	public String getDescription()
	{
		return "Loading data started";
	}

}
