package sk.qbsw.sgwt.winnetou.client.exception;

import com.smartgwt.client.util.BooleanCallback;

import sk.qbsw.sgwt.winnetou.client.ui.component.CMessageShowing;
import sk.qbsw.sgwt.winnetou.client.ui.localization.CErrorConstants;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;

/**
 * Class for publishing exceptions on client side.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CClientExceptionPublisher
{
	/**
	 * Passes exception to the handling mechanism
	 * 
	 * @param ex
	 */
	public static void pass (String message, Throwable ex)
	{
		throw new CSystemFailureException(ex.getMessage(), ex);
	}

	/**
	 * Passes exception to the handling mechanism
	 * 
	 * @param ex
	 */
	public static void pass (Throwable ex)
	{
		throw new CSystemFailureException(ex.getMessage(), ex);
	}

	/**
	 * Publishes exceptions / shows exception warning or system error message
	 * 
	 * @param ex
	 *            exception
	 */
	public static void publish (Throwable ex)
	{
		publish(ex, ISystemLabels.Factory.getInstance().title_warning());
	}

	/**
	 * Publishes exceptions / shows exception warning or system error message
	 * 
	 * @param ex
	 *            exception
	 */
	public static void publish (Throwable ex, String title)
	{
		String errorMessage = CErrorConstants.getExceptionMessage(ex);
		CMessageShowing.showWarning(title, errorMessage, (BooleanCallback) null);
	}
}
