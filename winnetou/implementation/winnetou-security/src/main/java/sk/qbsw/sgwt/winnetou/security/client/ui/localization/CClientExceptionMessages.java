package sk.qbsw.sgwt.winnetou.security.client.ui.localization;

import sk.qbsw.sgwt.winnetou.client.data.CClientDataHolder;

/**
 * List of exception which can be passed to the client when the system
 * 
 * @author Dalibor Rak
 * @Version 0.1
 * @since 0.1
 */
public class CClientExceptionMessages
{
	public static String AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";
	public static String AUTHENTICATION_USER_INACTIVE = "AUTHENTICATION_USER_INACTIVE";
	public static String AUTHORIZATION_INSUFFICIENT_RIGHTS = "AUTHORIZATION_INSUFFICIENT_RIGHTS";

	public static void initializeMessages()
	{
		CClientDataHolder.getInstance().addData(CClientDataHolder.ERROR_CODES, AUTHENTICATION_FAILED, ISecurityMessages.Factory.getInstance().error_not_authenticated());
		CClientDataHolder.getInstance().addData(CClientDataHolder.ERROR_CODES, AUTHENTICATION_USER_INACTIVE, ISecurityMessages.Factory.getInstance().error_user_inactive());
		CClientDataHolder.getInstance().addData(CClientDataHolder.ERROR_CODES, AUTHORIZATION_INSUFFICIENT_RIGHTS, ISecurityMessages.Factory.getInstance().error_insufficient_rights());
	}
}