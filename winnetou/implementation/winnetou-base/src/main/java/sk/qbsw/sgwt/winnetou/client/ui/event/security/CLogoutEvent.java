package sk.qbsw.sgwt.winnetou.client.ui.event.security;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

public class CLogoutEvent extends AApplicationEvent
{

	public CLogoutEvent (IApplicationEventSource source)
	{
		super(source);
	}

	public String getDescription ()
	{
		return "Logout from the application";
	}

}
