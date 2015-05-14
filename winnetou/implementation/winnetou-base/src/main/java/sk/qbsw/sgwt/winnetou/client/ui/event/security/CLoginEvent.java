package sk.qbsw.sgwt.winnetou.client.ui.event.security;

import sk.qbsw.sgwt.winnetou.client.model.security.CLoggedUserRecord;
import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

/**
 * Event for login to the application
 * @author Dalibor Rak
 * @version 1.0
 */
public class CLoginEvent extends AApplicationEvent
{
	private CLoggedUserRecord userInfo;
	
	public CLoginEvent (IApplicationEventSource handler, CLoggedUserRecord userInfo)
	{
		super(handler);
		this.userInfo = userInfo;
	}

	public String getDescription ()
	{
		return "Login successfull event.";
	}

	/**
	 * Returns user identifying object
	 * @return
	 */
	public CLoggedUserRecord getUserInfo ()
	{
		return userInfo;
	}
}

