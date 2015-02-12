package sk.qbsw.sgwt.winnetou.security.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import sk.qbsw.sgwt.winnetou.client.exception.CBusinessException;
import sk.qbsw.sgwt.winnetou.client.model.security.CLoggedUserRecord;

@RemoteServiceRelativePath ("rpc/authenticate")
public interface IAuthenticationService extends RemoteService
{

	/**
	 * Locator for service
	 * 
	 * @author Dalibor Rak
	 * @version 1.0
	 * @since 1.0
	 *
	 */
	public class Locator
	{
		private static IAuthenticationServiceAsync instance;

		public static IAuthenticationServiceAsync getInstance ()
		{
			if (instance == null)
			{
				instance = GWT.create(IAuthenticationService.class);
			}
			return instance;
		}
	}

	public CLoggedUserRecord authenticate (String login, String password) throws CBusinessException;
	
	/**
	 * Gets information regarding logged user
	 * 
	 * @return CLoggedUserRecord
	 */
	public CLoggedUserRecord getLoggedUserInfo();
	
	/**
	 * Logs out user from the application
	 */
	public void logout();
}
