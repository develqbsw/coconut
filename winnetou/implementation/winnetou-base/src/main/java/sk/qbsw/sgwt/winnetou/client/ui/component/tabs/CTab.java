package sk.qbsw.sgwt.winnetou.client.ui.component.tabs;

import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.CApplicationEventListenerCollection;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventListener;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * Tab accepting application events
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CTab extends Tab implements IApplicationEventSource
{
	/**
	 * Listener for publishing events
	 * 
	 * @author Dalibor Rak
	 * 
	 */
	private class CDefaultPublishingListener implements IApplicationEventListener<AApplicationEvent>
	{
		public void handle (AApplicationEvent event)
		{
			GWT.log("Panel: publishing event " + event, null);

			applicationEventsListeners.fireEvent(event);
		}

		public boolean isApplicable (IApplicationEvent event)
		{
			return true;
		}
	}

	/**
	 * Event listeners
	 */
	protected CApplicationEventListenerCollection applicationEventsListeners;

	/**
	 * publishing listener
	 */
	private CDefaultPublishingListener publishingListener;

	public CTab ()
	{
		applicationEventsListeners = new CApplicationEventListenerCollection();
		publishingListener = new CDefaultPublishingListener();
	}

	public CTab (String title)
	{
		this();
		setTitle(title);
	}

	@SuppressWarnings ("unchecked")
	public void addListener (IApplicationEventListener listener)
	{
		applicationEventsListeners.add(listener);
	}

	public void setPane (Canvas pane)
	{
		addToListeners(pane);
		super.setPane(pane);
	}

	/**
	 * Adds panel as listeners to member component
	 * 
	 * @param component
	 *            component which will fire events
	 */
	private void addToListeners (Object component)
	{
		if (component instanceof IApplicationEventSource)
		{
			IApplicationEventSource source = (IApplicationEventSource) component;
			source.addListener(publishingListener);
		}
	}

	public void clearListeners ()
	{
		applicationEventsListeners.clear();
	}

	public void removeListener (IApplicationEventListener listener)
	{
		applicationEventsListeners.remove(listener);
	}

	/**
	 * Fires event
	 * 
	 * @param event
	 */
	public void fireEvent (IApplicationEvent event)
	{
		applicationEventsListeners.fireEvent(event);
	}
}
