/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.indy.base.behaviour;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Abstract class Class AAJAXDownload.
 *
 * @author Dalibor Rak
 * @version 1.3
 * @since 1.3
 */
@SuppressWarnings ("serial")
public abstract class AAJAXDownload extends AbstractAjaxBehavior
{
	/**
	 * Initiate.
	 *
	 * @param target the target
	 */
	public void initiate (AjaxRequestTarget target)
	{
		CharSequence url = getCallbackUrl();

		target.appendJavaScript("window.location.href='" + url + "'");
	}

	/**
	 * On request.
	 *
	 * @see org.apache.wicket.behavior.IBehaviorListener#onRequest()
	 */
	public void onRequest ()
	{
		getComponent().getRequestCycle().scheduleRequestHandlerAfterCurrent(new ResourceStreamRequestHandler(getResourceStream(), getFileName()));
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	protected abstract String getFileName ();

	/**
	 * Gets the resource stream.
	 *
	 * @return the resource stream
	 */
	protected abstract IResourceStream getResourceStream ();
}
