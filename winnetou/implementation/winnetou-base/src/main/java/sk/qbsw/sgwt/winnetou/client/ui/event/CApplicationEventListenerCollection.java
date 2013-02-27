package sk.qbsw.sgwt.winnetou.client.ui.event;

import java.util.ArrayList;

/**
 * Class holds list of ApplicationEventListeners
 * 
 * @author Dalibor Rak
 * @version 0.1
 */
@SuppressWarnings ("serial")
public class CApplicationEventListenerCollection extends ArrayList<IApplicationEventListener<IApplicationEvent>>
{
	/**
	 * Fires the given event to all the contained listeners.
	 * 
	 * @param event
	 *            The event to be fired.
	 */
	public void fireEvent (IApplicationEvent event)
	{
		for (IApplicationEventListener<IApplicationEvent> listener : this)
		{
			// event check must be as the last one
			if (listener != null && listener.isApplicable(event))
			{
				listener.handle(event);
			}
		}
	}
}
