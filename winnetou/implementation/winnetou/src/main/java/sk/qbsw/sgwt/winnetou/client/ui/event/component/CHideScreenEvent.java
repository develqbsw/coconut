package sk.qbsw.sgwt.winnetou.client.ui.event.component;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event fired when screen is closed
 * 
 * @author Dalibor Rak
 * 
 */
public class CHideScreenEvent extends AApplicationEvent
{
	public CHideScreenEvent(IApplicationEventSource source)
	{
		super(source);
	}

	public String getDescription()
	{
		return "Closing window";
	}

}
