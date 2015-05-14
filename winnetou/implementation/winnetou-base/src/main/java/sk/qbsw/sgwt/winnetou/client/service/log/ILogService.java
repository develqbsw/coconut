package sk.qbsw.sgwt.winnetou.client.service.log;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service for logging from the client side
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
@RemoteServiceRelativePath("rpc/winnetou/log")
public interface ILogService extends RemoteService
{
	public class Locator
	{
		private static ILogServiceAsync instance;

		public static ILogServiceAsync getInstance()
		{
			if (instance == null)
			{
				instance = GWT.create(ILogService.class);
			}
			return instance;
		}
	}

	/**
	 * Logs debug message
	 * 
	 * @param message
	 */
	public void debug(String message);

	/**
	 * Logs error message
	 * 
	 * @param message
	 */
	public void error(String message);

	/**
	 * Logs info message
	 * 
	 * @param message
	 */
	public void info(String message);
}
