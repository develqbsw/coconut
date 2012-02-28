package sk.qbsw.sgwt.winnetou.client.service.time;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IServerTimeServiceAsync
{
	void getServerTime(AsyncCallback<Date> callback);
}
