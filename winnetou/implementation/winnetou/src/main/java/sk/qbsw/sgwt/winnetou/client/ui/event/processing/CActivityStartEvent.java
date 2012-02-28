package sk.qbsw.sgwt.winnetou.client.ui.event.processing;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event fired when activity (action for example on-double-click) is start.
 * 
 * @author Pavol Kucka
 * 
 */
public class CActivityStartEvent extends AApplicationEvent
{
	public CActivityStartEvent(IApplicationEventSource source)
	{
		super(source);
	}

	public String getDescription()
	{
		return "Activity start window";
	}
}
