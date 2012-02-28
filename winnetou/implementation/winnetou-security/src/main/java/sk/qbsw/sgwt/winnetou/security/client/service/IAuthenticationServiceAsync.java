package sk.qbsw.sgwt.winnetou.security.client.service;

import sk.qbsw.sgwt.winnetou.client.model.security.CLoggedUserRecord;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IAuthenticationServiceAsync
{

	void authenticate (String login, String password, AsyncCallback<CLoggedUserRecord> callback);

	void getLoggedUserInfo (AsyncCallback<CLoggedUserRecord> callback);

	void logout (AsyncCallback<Void> callback);

}
