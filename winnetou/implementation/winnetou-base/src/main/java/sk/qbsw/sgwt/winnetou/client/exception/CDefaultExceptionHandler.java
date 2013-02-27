package sk.qbsw.sgwt.winnetou.client.exception;

import sk.qbsw.sgwt.winnetou.client.service.log.ILogService;
import sk.qbsw.sgwt.winnetou.client.ui.component.CMessageShowing;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;

/**
 * Default exception handler for client. Shows message, and logs it to AS.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CDefaultExceptionHandler implements UncaughtExceptionHandler
{
	public CDefaultExceptionHandler()
	{

	}

	public void onUncaughtException(Throwable ex)
	{
		StackTraceElement[] trace = ex.getStackTrace();
		StringBuffer message = new StringBuffer();
		message.append(ex.getMessage() + ":");
		for (StackTraceElement stackTraceElement : trace)
		{
			String className = stackTraceElement.getClassName();
			String methodName = stackTraceElement.getMethodName();
			String fileName = stackTraceElement.getFileName();
			Integer lineNumber = stackTraceElement.getLineNumber();

			message.append(" at " + className + "." + methodName + "(" + fileName + ":" + lineNumber + ")\n");
		}

		CMessageShowing.showWarning(ISystemLabels.Factory.getInstance().title_warning(), ISystemMessages.Factory.getInstance().system_exception_occured(), (BooleanCallback)null);

		ILogService.Locator.getInstance().error(message.toString(), new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				System.out.println(caught);
			}

			public void onSuccess(Void result)
			{
				// ok, error should be logged on server side
			}
		});
	}
}
