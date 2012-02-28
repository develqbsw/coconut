package sk.qbsw.sgwt.winnetou.security.client.ui.localization;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface ISecurityMessages extends Messages
{
	public class Factory
	{
		private static ISecurityMessages instance;

		public static ISecurityMessages getInstance ()
		{
			if (instance == null)
			{
				instance = GWT.create(ISecurityMessages.class);
			}

			return instance;
		}
	}

	@Key ("security.error.not_authenticated")
	public String error_not_authenticated ();

	@Key ("security.error.user_inactive")
	public String error_user_inactive ();

	@Key ("security.error.insufficient_rights")
	public String error_insufficient_rights ();
}
