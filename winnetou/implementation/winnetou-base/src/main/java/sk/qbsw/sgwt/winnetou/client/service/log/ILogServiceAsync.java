package sk.qbsw.sgwt.winnetou.client.service.log;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @see ILogService
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface ILogServiceAsync
{
	public void debug(String message, AsyncCallback<Void> callback);

	public void error(String message, AsyncCallback<Void> callback);

	public void info(String message, AsyncCallback<Void> callback);
}
