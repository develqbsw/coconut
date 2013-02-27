package sk.qbsw.sgwt.winnetou.client.ui.entrypoint;

import sk.qbsw.sgwt.winnetou.client.exception.CDefaultExceptionHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Base parent of Entry point - supports checking of object existence in html
 * page
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public abstract class AEntryPoint implements EntryPoint
{
	/**
	 * Method calle for initialization
	 * 
	 * @param divCheck
	 *            enter this parameter if you would like to check existence of
	 *            object with this ID in the page. if null, no check will be
	 *            executed.
	 */
	protected void initialize(final String divCheck)
	{
		GWT.setUncaughtExceptionHandler(new CDefaultExceptionHandler());

		if (divCheck != null && RootPanel.get(divCheck) == null)
		{
			return;
		}

		process();
	}

	/**
	 * Process the logic (The div check was successfull)
	 */
	protected abstract void process();
}
