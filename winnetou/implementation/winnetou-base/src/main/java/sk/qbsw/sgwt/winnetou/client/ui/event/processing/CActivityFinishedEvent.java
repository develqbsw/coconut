package sk.qbsw.sgwt.winnetou.client.ui.event.processing;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event fired when activity (action for example on-click) is end.
 * 
 * @author Pavol Kucka
 * 
 */
public class CActivityFinishedEvent extends AApplicationEvent
{
	public CActivityFinishedEvent(IApplicationEventSource source)
	{
		super(source);
	}

	public String getDescription()
	{
		return "Activity end window";
	}
}
