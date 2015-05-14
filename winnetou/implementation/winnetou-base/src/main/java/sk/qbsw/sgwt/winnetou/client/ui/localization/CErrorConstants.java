package sk.qbsw.sgwt.winnetou.client.ui.localization;

import sk.qbsw.sgwt.winnetou.client.data.CClientDataHolder;
import sk.qbsw.sgwt.winnetou.client.exception.CClientExceptionPublisher;

/**
 * Class with error messages transfered to client side.
 * 
 * @author Dalibor Rak
 * @version 1.0
 * 
 */
public class CErrorConstants
{
	private static CErrorConstants instance;

	public static final String winnetouExceptionPrefix = "sk.qbsw.sgwt.winnetou:";

	/**
	 * Gets exception message
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionMessage (Throwable e)
	{
		if (e.getMessage().startsWith(CErrorConstants.winnetouExceptionPrefix))
		{
			String errorCode = e.getMessage().substring(CErrorConstants.winnetouExceptionPrefix.length());
			String message = getErrorMessage(errorCode);
			return message;
		}
		else
		{

			String message = ISystemMessages.Factory.getInstance().system_exception_occured();
			return message;
		}
	}

	/**
	 * Gets error code from exception
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionErrorCode (Throwable e)
	{
		if (e.getMessage().startsWith(CErrorConstants.winnetouExceptionPrefix))
		{
			String errorCode = e.getMessage().substring(CErrorConstants.winnetouExceptionPrefix.length());
			return errorCode;
		}
		else
		{
			return "";
		}
	}

	public static CErrorConstants getInstance ()
	{
		if (instance == null)
		{
			instance = new CErrorConstants();
		}
		return instance;
	}

	private CErrorConstants ()
	{
	}

	/**
	 * Gets message to show
	 * 
	 * @param key
	 *            key to show
	 * @return String value
	 */
	private static String getErrorMessage (String key)
	{
		String retVal = CClientDataHolder.getInstance().getData(CClientDataHolder.ERROR_CODES, key);
		if (retVal == null)
		{
			return "";
		}
		return retVal;
	}
}
