package sk.qbsw.sgwt.winnetou.client.ui.component.panel;

import sk.qbsw.sgwt.winnetou.client.ui.component.IUIComponent;
import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.CApplicationEventListenerCollection;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventListener;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Default panel for vertical members. Registers itselfs as listeners of
 * children events.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class APanelVertical extends VLayout implements IPanel, IUIComponent
{
	/**
	 * Listener for publishing events
	 * 
	 * @author Dalibor Rak
	 * 
	 */
	private class CDefaultPublishingListener implements IApplicationEventListener<AApplicationEvent>
	{
		public void handle(AApplicationEvent event)
		{
			GWT.log("Panel: publishing event " + event, null);

			applicationEventsListeners.fireEvent(event);
		}

		public boolean isApplicable(IApplicationEvent event)
		{
			return true;
		}

	}

	/**
	 * Application listeners
	 */
	private CApplicationEventListenerCollection applicationEventsListeners;

	/**
	 * Publishing listeners
	 */
	private CDefaultPublishingListener publishingListener;

	protected APanelVertical()
	{
		publishingListener = new CDefaultPublishingListener();
		applicationEventsListeners = new CApplicationEventListenerCollection();
	}

	/**
	 * Adds child to panel and adds listeners
	 */
	@Override
	public Canvas addChild(Canvas child)
	{
		addToListeners(child);
		return super.addChild(child);
	}

	/**
	 * method for adding new listener
	 * 
	 * @param listener
	 *            listener to be added
	 */
	@SuppressWarnings("unchecked")
	public void addListener(IApplicationEventListener listener)
	{
		applicationEventsListeners.add(listener);
	}

	/**
	 * Adds Member to panel and adds listeners
	 */
	@Override
	public void addMember(Canvas canvas)
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
	private void addToListeners(Object component)
	{
		if (component instanceof IApplicationEventSource)
		{
			IApplicationEventSource source = (IApplicationEventSource) component;
			source.addListener(publishingListener);
		}
	}

	/**
	 * removes all listeners
	 */
	public void clearListeners()
	{
		applicationEventsListeners.clear();
	}

	/**
	 * Fires event
	 * 
	 * @param event
	 */
	public void fireEvent(IApplicationEvent event)
	{
		applicationEventsListeners.fireEvent(event);
	}

	/**
	 * gets this
	 */
	public final Layout getLayout()
	{
		return this;
	}

	/**
	 * nothing to do
	 */
	public void initComponents()
	{
	}

	/**
	 * Initializes the showing, calls initLayout and initComponents;
	 */
	public void initialize()
	{
		initLayout();
		initComponents();
		loadData();
	}

	/**
	 * Default layout : 100% height, and 100% width
	 */
	public void initLayout()
	{
		setWidth("100%");
		setHeight("100%");
	}

	/**
	 * Loads data from the server
	 */
	public void loadData()
	{

	}

	/**
	 * method for removing new listener
	 * 
	 * @param listener
	 *            listener to be removed
	 */
	@SuppressWarnings("unchecked")
	public void removeListener(IApplicationEventListener listener)
	{
		applicationEventsListeners.remove(listener);
	}
	
	/**
	 * @see IPanel#autoFocus()
	 */
	public void autoFocus()
	{
		
	}
}
