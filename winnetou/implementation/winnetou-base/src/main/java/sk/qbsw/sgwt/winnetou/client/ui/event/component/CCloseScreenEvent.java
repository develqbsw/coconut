package sk.qbsw.sgwt.winnetou.client.ui.event.component;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event fired when screen is closed
 * 
 * @author Dalibor Rak
 * 
 */
public class CCloseScreenEvent extends AApplicationEvent
{
	public CCloseScreenEvent(IApplicationEventSource source)
	{
		super(source);
	}

	public String getDescription()
	{
		return "Closing window";
	}
}
