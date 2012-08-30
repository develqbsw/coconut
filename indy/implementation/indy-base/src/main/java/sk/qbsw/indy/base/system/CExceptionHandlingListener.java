package sk.qbsw.indy.base.system;

import org.apache.log4j.Logger;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

public class CExceptionHandlingListener implements IRequestCycleListener
{

	@Override
	public void onUrlMapped (RequestCycle arg0, IRequestHandler arg1, Url arg2)
	{
		// Nothing to do
	}

	@Override
	public void onRequestHandlerScheduled (RequestCycle arg0, IRequestHandler arg1)
	{
		// Nothing to do
	}

	@Override
	public void onRequestHandlerResolved (RequestCycle arg0, IRequestHandler arg1)
	{
		// Nothing to do
	}

	@Override
	public void onRequestHandlerExecuted (RequestCycle arg0, IRequestHandler arg1)
	{
		// Nothing to do
	}

	@Override
	public void onExceptionRequestHandlerResolved (RequestCycle arg0, IRequestHandler arg1, Exception e)
	{
		Logger.getLogger(CExceptionHandlingListener.class).error("Handled by CExceptionHandlingListener", e);
	}

	@Override
	public IRequestHandler onException (RequestCycle arg0, Exception e)
	{
		Logger.getLogger(CExceptionHandlingListener.class).error("Handled by CExceptionHandlingListener", e);
		return null;
	}

	@Override
	public void onEndRequest (RequestCycle arg0)
	{
		// Nothing to do
	}

	@Override
	public void onDetach (RequestCycle arg0)
	{
		// Nothing to do
	}

	@Override
	public void onBeginRequest (RequestCycle arg0)
	{
		// Nothing to do
	}
}
