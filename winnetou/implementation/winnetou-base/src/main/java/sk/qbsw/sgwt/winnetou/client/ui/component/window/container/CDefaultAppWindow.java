package sk.qbsw.sgwt.winnetou.client.ui.component.window.container;

import sk.qbsw.sgwt.winnetou.client.ui.component.screen.ILockable;
import sk.qbsw.sgwt.winnetou.client.ui.component.screen.IScreen;
import sk.qbsw.sgwt.winnetou.client.ui.component.window.CAppWindowManager;
import sk.qbsw.sgwt.winnetou.client.ui.component.window.IWindow;
import sk.qbsw.sgwt.winnetou.client.ui.component.window.information.CProcessingInfoWindow;
import sk.qbsw.sgwt.winnetou.client.ui.event.AApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.CApplicationEventListenerCollection;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventListener;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;
import sk.qbsw.sgwt.winnetou.client.ui.event.component.CCloseScreenEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.component.CScreenTitleChangeEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.processing.CLoadingDataFinishedEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.processing.CLoadingDataStartedEvent;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;

/**
 * Application window used during calling menu actions. This is default template
 * for application window
 * 
 * @author Dalibor Rak
 * @version 0.1.4
 * @since 0.1.2
 */
public class CDefaultAppWindow extends CAppWindow implements IWindow
{
	private class CCloseScreenHandler implements IApplicationEventListener<CCloseScreenEvent>
	{

		public void handle (CCloseScreenEvent event)
		{
			destroy();
		}

		public boolean isApplicable (IApplicationEvent event)
		{
			return event.getClass().equals(CCloseScreenEvent.class);
		}

	}

	private class CDefaultPublishingListener implements IApplicationEventListener<AApplicationEvent>
	{
		public void handle (AApplicationEvent event)
		{
			GWT.log("Window: publishing event " + event, null);

			listeners.fireEvent(event);
		}

		public boolean isApplicable (IApplicationEvent event)
		{
			return true;
		}

	}

	private class CLoadingDataFinishedListener implements IApplicationEventListener<CLoadingDataFinishedEvent>
	{

		public void handle (CLoadingDataFinishedEvent event)
		{
			if (statusWindow != null)
			{
				statusWindow.hideMessage();
			}
		}

		public boolean isApplicable (IApplicationEvent event)
		{
			return event.getClass().equals(CLoadingDataFinishedEvent.class);
		}
	}

	/**
	 * Handles title change on screen
	 * 
	 * @author Dalibor Rak
	 * @version 0.1
	 * @since 0.1
	 */
	private class CTitleChangeListener implements IApplicationEventListener<CScreenTitleChangeEvent>
	{

		public void handle (CScreenTitleChangeEvent event)
		{
			setTitle(event.getTitle());
			setStatus(event.getId());
		}

		public boolean isApplicable (IApplicationEvent event)
		{
			return event.getClass().equals(CScreenTitleChangeEvent.class);
		}
	}

	private class CLoadingDataStartedListener implements IApplicationEventListener<CLoadingDataStartedEvent>
	{

		public void handle (CLoadingDataStartedEvent event)
		{
			if (statusWindow == null)
			{
				statusWindow = CProcessingInfoWindow.getNewInstance((Canvas) event.getSource());
				statusWindow.showMessage(ISystemMessages.Factory.getInstance().data_loading(), getWindow());
			}
			statusWindow.show();
		}

		public boolean isApplicable (IApplicationEvent event)
		{
			return event.getClass().equals(CLoadingDataStartedEvent.class);
		}
	}

	private CCloseScreenHandler closeScreenHandler;

	private CApplicationEventListenerCollection listeners;

	private CLoadingDataFinishedListener loadingFinishedHandler;

	private CLoadingDataStartedListener loadingStartedHandler;

	private CDefaultPublishingListener publishingListener;

	private CTitleChangeListener titleChangeListener;

	private CProcessingInfoWindow statusWindow;

	/**
	 * Constructor
	 */
	public CDefaultAppWindow ()
	{
		closeScreenHandler = new CCloseScreenHandler();
		loadingStartedHandler = new CLoadingDataStartedListener();
		loadingFinishedHandler = new CLoadingDataFinishedListener();
		listeners = new CApplicationEventListenerCollection();
		publishingListener = new CDefaultPublishingListener();
		titleChangeListener = new CTitleChangeListener();

		CAppWindowManager.windowCreated();
	}

	/**
	 * @see sk.qbsw.ondrej.application.client.components.IWindow#addItem(sk.qbsw.ondrej.application.client.screens.IScreen)
	 */
	public void addItem (IScreen screen)
	{
		screen.addListener(getCloseScreenHandler());
		screen.addListener(getLoadingStartedHandler());
		screen.addListener(getLoadingFinishedHandler());
		screen.addListener(publishingListener);
		screen.addListener(titleChangeListener);

		super.addItem(screen.getLayout());
		setTitle(screen.getScreenTitle());
		setStatus(screen.getScreenId());
	}

	@SuppressWarnings ("unchecked")
	public void addListener (IApplicationEventListener listener)
	{
		listeners.add(listener);
	}

	public void clearListeners ()
	{
		listeners.clear();
	}

	@Override
	public void destroy ()
	{
		super.destroy();
		CAppWindowManager.windowDestroyed();
	}

	protected IApplicationEventListener<CCloseScreenEvent> getCloseScreenHandler ()
	{
		return closeScreenHandler;
	}

	protected IApplicationEventListener<CLoadingDataFinishedEvent> getLoadingFinishedHandler ()
	{
		return loadingFinishedHandler;
	}

	protected IApplicationEventListener<CLoadingDataStartedEvent> getLoadingStartedHandler ()
	{
		return loadingStartedHandler;
	}

	/**
	 * Nothing to do
	 */
	public void initComponents ()
	{
		super.initComponents();
	}

	public void removeListener (IApplicationEventListener listener)
	{
		listeners.remove(listener);
	}

	/**
	 * Defines own handling of close of window => will cause to unlock sections
	 */
	@Override
	protected void prepareCloseHandler ()
	{
		this.addCloseClickHandler(new CloseClickHandler()
		{
			public void onCloseClick (CloseClientEvent event)
			{
				Canvas[] items = getItems();
				for (Canvas canvas : items)
				{
					if (canvas instanceof ILockable)
					{
						((ILockable) canvas).unlock();
					}
				}
				destroy();
			}
		});
	}

	/**
	 * @See {@link IApplicationEventSource#fireEvent(IApplicationEvent)}
	 */
	public void fireEvent (IApplicationEvent event)
	{
		listeners.fireEvent(event);
	}

}
