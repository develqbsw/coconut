package sk.qbsw.sgwt.winnetou.client.ui.component;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Dialog;

/**
 * Class used for displaying messages and warnings.
 * 
 * @author Dalibor Rak
 * @version 1.0
 */
public class CMessageShowing
{
	/**
	 * Shows popup window with message, without title and with default button
	 * "OK"
	 * 
	 * @param title
	 * @param message
	 * @param cancelButtonName
	 */
	public static void showInfo(String message)
	{
		showInfo("", message);
	}

	/**
	 * Shows popup window with title, message and ok button
	 * 
	 * @param title
	 * @param message
	 * @param cancelButtonName
	 *            - not used
	 */
	public static void showInfo(String title, String message)
	{
		SC.say(title, message);
	}

	/**
	 * Shows popup window with title, message and ok button
	 * 
	 * @param title
	 * @param message
	 * @param cancelButtonName
	 *            - not used
	 */
	public static void showInfo(String title, String message, BooleanCallback callback)
	{
		SC.say(title, message, callback);
	}

	/**
	 * Shows question
	 * 
	 * @param title
	 *            title of the popup
	 * @param message
	 *            text message
	 * @param callback
	 *            callack for identifying response
	 */
	public static void showQuestion(String title, String message, BooleanCallback callback)
	{
		// adds empty callback
		if (callback == null)
		{
			callback = new BooleanCallback()
			{

				public void execute(Boolean value)
				{
				}

			};
		}

		SC.ask(title, message, callback);
	}

	/**
	 * /** Shows popup window with title, message and ok button
	 * 
	 * @param title
	 * @param message
	 * @param callback
	 *            - can be null
	 */
	public static void showWarning(String title, String message, BooleanCallback callback)
	{
		// adds empty callback
		if (callback == null)
		{
			callback = new BooleanCallback()
			{

				public void execute(Boolean value)
				{
				}
			};
		}

		Dialog d = new Dialog();
		d.bringToFront();
		SC.warn(title, message, callback, d);
	}

	/**
	 * /** Shows popup window with title, message and ok button
	 * 
	 * @param title
	 * @param message
	 * @param callback
	 *            - can be null
	 */
	public static void showWarning(String title, String message, Canvas parent)
	{
		BooleanCallback callback = new BooleanCallback()
		{

			public void execute(Boolean value)
			{
			}
		};

		Dialog d = new Dialog();
		d.setParentElement(parent);
		SC.warn(title, message, callback, d);
	}

}
