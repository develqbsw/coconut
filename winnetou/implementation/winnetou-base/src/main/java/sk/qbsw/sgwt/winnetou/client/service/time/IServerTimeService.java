package sk.qbsw.sgwt.winnetou.client.service.time;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service providing time from applicaiton server
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
@RemoteServiceRelativePath("rpc/winnetou/time")
public interface IServerTimeService extends RemoteService
{
	public class Locator
	{
		private static IServerTimeServiceAsync instance;

		public static IServerTimeServiceAsync getInstance ()
		{
			if (instance == null)
			{
				instance = GWT.create(IServerTimeService.class);
			}
			return instance;
		}
	}
	public Date getServerTime();
}
