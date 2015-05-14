package sk.qbsw.sgwt.winnetou.security.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import sk.qbsw.sgwt.winnetou.client.model.security.CLoggedUserRecord;

public interface IAuthenticationServiceAsync
{

	void authenticate (String login, String password, AsyncCallback<CLoggedUserRecord> callback);

	void getLoggedUserInfo (AsyncCallback<CLoggedUserRecord> callback);

	void logout (AsyncCallback<Void> callback);

}
