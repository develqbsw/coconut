package sk.qbsw.sgwt.winnetou.client.ui.component.screen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sk.qbsw.sgwt.winnetou.client.ui.component.window.IControlable;
import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.CApplicationEventListenerCollection;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventListener;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;
import sk.qbsw.sgwt.winnetou.client.ui.event.component.CCloseScreenEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.processing.CActivityFinishedEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.processing.CActivityStartEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.processing.CLoadingDataFinishedEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.processing.CLoadingDataStartedEvent;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * Base class for horizontal layout screen.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public abstract class AScreenHorizontal extends HLayout implements IScreen, IControlable
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
	 * reference to event source
	 */
	protected IApplicationEventSource eventSource = this;

	private CApplicationEventListenerCollection applicationEventsListeners;

	private CDefaultPublishingListener publishingListener;

	private String screenId;

	private String screenTitle;

	/**
	 * Default constructor, reads services and resources
	 */
	public AScreenHorizontal ()
	{
		applicationEventsListeners = new CApplicationEventListenerCollection();
		publishingListener = new CDefaultPublishingListener();

	}

	/**
	 * method for adding new listener
	 * 
	 * @param listener
	 *            listener to be added
	 */
	@SuppressWarnings ("unchecked")
	public void addListener (IApplicationEventListener listener)
	{
		applicationEventsListeners.add(listener);
	}

	/**
	 * Adds Member to screen and adds listeners
	 */
	@Override
	public void addMember (Canvas canvas)
	{
		addToListeners(canvas);
		super.addMember(canvas);
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

	/**
	 * Clears all members of the screen
	 */
	public void clearContent ()
	{
		if (getMembers().length > 0)
		{
			this.removeMembers(this.getMembers());
		}
	}

	/**
	 * removes all listeners
	 */
	public void clearListeners ()
	{
		applicationEventsListeners.clear();
	}

	/**
	 * Destroys screen
	 */
	public void destroyScreen ()
	{
		this.destroy();
	}

	/**
	 * Fires CCloseScreenEvent event
	 */
	protected void fireCloseEvent ()
	{
		applicationEventsListeners.fireEvent(new CCloseScreenEvent(this));
	}

	/**
	 * Fires CLoadingDataFinishedEvent event
	 */
	protected void fireDataLoadingFinishedEvent ()
	{
		applicationEventsListeners.fireEvent(new CLoadingDataFinishedEvent(this));
	}

	/**
	 * Fires CActivityEndEvent event
	 */
	protected void fireActivityFinishedEvent ()
	{
		applicationEventsListeners.fireEvent(new CActivityFinishedEvent(this));
	}
	
	/**
	 * Fires CActivityEndEvent event
	 */
	protected void fireActivityStartEvent ()
	{
		applicationEventsListeners.fireEvent(new CActivityStartEvent(this));
	}
	
	/**
	 * Fires CLoadingDataFinishedEvent event
	 */
	protected void fireDataLoadingFinishedEvent (Serializable data)
	{
		CLoadingDataFinishedEvent event = new CLoadingDataFinishedEvent(this);
		event.setRelatedData(data);

		applicationEventsListeners.fireEvent(event);
	}

	/**
	 * Fires CLoadingDataStartedEvent event
	 */
	protected void fireDataLoadingStartedEvent ()
	{
		applicationEventsListeners.fireEvent(new CLoadingDataStartedEvent(this));
	}

	public void fireEvent (IApplicationEvent event)
	{
		applicationEventsListeners.fireEvent(event);
	}

	/**
	 * @See IScreen#getHeaderControlsLeft()
	 */
	public List<Canvas> getHeaderControlsLeft ()
	{
		List<Canvas> items = new ArrayList<Canvas>();

		return items;
	}

	/**
	 * @See IScreen#getHeaderControlsRight()
	 */
	public List<Canvas> getHeaderControlsRight ()
	{
		List<Canvas> items = new ArrayList<Canvas>();

		return items;
	}

	/**
	 * @See {@link IScreen#getToolbarControls()}
	 */
	public List<Canvas> getToolbarControls ()
	{
		List<Canvas> items = new ArrayList<Canvas>();

		return items;
	}

	/**
	 * Gets screen layout
	 */
	public Layout getLayout ()
	{
		return this;
	}

	public String getScreenId ()
	{
		return screenId;
	}

	/**
	 * @see IScreen#getScreenTitle()
	 */
	public String getScreenTitle ()
	{
		return screenTitle;
	}

	/**
	 * @see IScreen#hideScreen()
	 */
	public void hideScreen ()
	{
		setWidth("0%");
		setVisible(false);
		redraw();
	}

	/**
	 * @see IScreen#loadData()
	 */
	public void loadData ()
	{

	}

	/**
	 * method for removing new listener
	 * 
	 * @param listener
	 *            listener to be removed
	 */
	public void removeListener (IApplicationEventListener listener)
	{
		applicationEventsListeners.remove(listener);
	}

	public void setScreenId (String screenId)
	{
		this.screenId = screenId;
	}

	/**
	 * @see IScreen#showScreen()
	 */
	public void showScreen ()
	{
		setWidth100();
		setVisible(true);
		redraw();
	}

	/**
	 * @see IScreen#autoFocus()
	 */
	public void autoFocus ()
	{

	}
	
	
	/**
	 *	@see IControlable#disableByActivityStart 
	 */
	public void disableByActivityStart ()
	{
		Canvas[] members = getMembers();

		for (Canvas canvas : members)
		{
			if (canvas instanceof IControlable)
			{
				IControlable controlable = (IControlable) canvas;
				controlable.disableByActivityStart();
			}
		}
	}

	/**
	 *	@see IControlable#enableByActivityEnd 
	 */
	public void enableByActivityEnd ()
	{
		Canvas[] members = getMembers();

		for (Canvas canvas : members)
		{
			if (canvas instanceof IControlable)
			{
				IControlable controlable = (IControlable) canvas;
				controlable.enableByActivityEnd();
			}
		}
	}
}
