package sk.qbsw.sgwt.winnetou.client.ui.component.window.container;

import sk.qbsw.sgwt.winnetou.client.ui.component.CMessageShowing;
import sk.qbsw.sgwt.winnetou.client.ui.component.screen.IScreen;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventListener;
import sk.qbsw.sgwt.winnetou.client.ui.event.component.CCloseScreenEvent;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.google.gwt.user.client.History;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;

/**
 * Window for external entrypoints
 * 
 * @author Dalibor Rak
 * @version 0.1.4
 * @since 0.1.1
 */
public class CCommonWindow extends CAppWindow
{
	private class CCloseScreenHandler implements IApplicationEventListener<CCloseScreenEvent>
	{

		public void handle (CCloseScreenEvent event)
		{
			destroy();
			History.back();
		}

		public boolean isApplicable (IApplicationEvent event)
		{
			return event.getClass().equals(CCloseScreenEvent.class);
		}

	}

	private CCloseScreenHandler closeScreenHandler;

	public CCommonWindow ()
	{
		super();

		closeScreenHandler = new CCloseScreenHandler();

		this.addCloseClickHandler(new CloseClickHandler()
		{
			public void onCloseClick (CloseClientEvent event)
			{
				CMessageShowing.showQuestion(ISystemLabels.Factory.getInstance().title_info(), ISystemMessages.Factory.getInstance().window_can_close(), new BooleanCallback()
				{
					public void execute (Boolean value)
					{
						if (value != null && true == value)
						{
							destroy();
							History.back();
						}
					}
				});
			}
		});

	}

	/**
	 * @see sk.qbsw.ondrej.application.client.components.IWindow#addItem(sk.qbsw.ondrej.application.client.screens.IScreen)
	 */
	public void addItem (IScreen screen)
	{
		screen.addListener(getCloseScreenHandler());

		super.addItem(screen.getLayout());
		setTitle(screen.getScreenTitle());
	}

	/**
	 * nothing to do.
	 */
	@SuppressWarnings ("unchecked")
	public void addListener (IApplicationEventListener listener)
	{
	}

	/**
	 * nothing to do.
	 */
	public void clearListeners ()
	{
	}

	protected IApplicationEventListener<CCloseScreenEvent> getCloseScreenHandler ()
	{
		return closeScreenHandler;
	}

	/**
	 * nothing to do.
	 */
	public void initComponents ()
	{
	}

	@Override
	protected void prepareModality ()
	{
		this.setIsModal(false);
		this.setShowModalMask(false);
	}

	@Override
	protected void prepareSize ()
	{
		this.setSize("950px", "700px");
		this.setMinHeight(500);
		this.setMinWidth(300);
		this.setCanDragResize(true);

	}

	/**
	 * nothing to do.
	 */
	@SuppressWarnings ("unchecked")
	public void removeListener (IApplicationEventListener listener)
	{
	}

	/**
	 * nothing to do.
	 */
	public void fireEvent (IApplicationEvent event)
	{
	}
}
