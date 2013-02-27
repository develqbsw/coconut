package sk.qbsw.sgwt.winnetou.client.ui.component.window.container;

import java.util.List;

import sk.qbsw.sgwt.winnetou.client.ui.component.CMessageShowing;
import sk.qbsw.sgwt.winnetou.client.ui.component.screen.CToolStrip;
import sk.qbsw.sgwt.winnetou.client.ui.component.screen.IScreen;
import sk.qbsw.sgwt.winnetou.client.ui.component.window.IControlable;
import sk.qbsw.sgwt.winnetou.client.ui.component.window.IWindow;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Base window implementation. Supports IUICompontns principle.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public abstract class CAppWindow extends Window implements IWindow, IControlable
{
	private Img activeIcon;

	/**
	 * Toolbar panel
	 */
	private CToolStrip toolbarPanel;

	public CAppWindow ()
	{
		super();
	}

	public CAppWindow (JavaScriptObject jsObj)
	{
		super(jsObj);
	}

	/**
	 * Method constructs panel with items for control elements in the window
	 * header
	 * 
	 * @param items
	 * @return
	 */
	private Canvas constructControlPanel (List<Canvas> items, Alignment align)
	{
		if (items.size() == 0)
		{
			return null;
		}

		HLayout layout = new HLayout();
		layout.setMembersMargin(3);
		layout.setAlign(align);

		int size = 0;

		for (Canvas canvas : items)
		{

			if (canvas.getWidth() != null)
			{
				size += canvas.getWidth();
			}
			layout.addMember(canvas);
		}
		layout.setWidth(size);

		return layout;
	}

	/**
	 * @see sk.qbsw.ondrej.application.client.components.IWindow#getWindow()
	 */
	public Window getWindow ()
	{
		return this;
	}

	/**
	 * @see IWindow#initComponents()
	 */
	public void initComponents ()
	{
		toolbarPanel = new CToolStrip();
		toolbarPanel.setWidth100();
		toolbarPanel.setMembersMargin(5);
		toolbarPanel.setPadding(5);
		addItem(toolbarPanel);
	}

	/**
	 * @see sk.qbsw.ondrej.application.client.components.IWindow#initLayout()
	 */
	public final void initLayout ()
	{
		this.setShowCustomScrollbars(false);
		this.setShowFooter(true);

		prepareCloseHandler();
		prepareDefaultActions();
		prepareSize();
		prepareModality();
		prepareDraging();
		prepareShadow();
	}

	/**
	 * Method called during initializing of layout. Can be overridden to change
	 * adding of listeners from GWT and SmartGWT (as closeClickHandler)
	 */
	protected void prepareCloseHandler ()
	{
		this.addCloseClickHandler(new CloseClickHandler()
		{
			public void onCloseClick (CloseClientEvent event)
			{
				destroy();
			}
		});
	}

	/**
	 * Method called for preparation of header default controls. To change
	 * behaviour, override this method.
	 */
	protected void prepareDefaultActions ()
	{
		this.setShowCloseButton(true);
		this.setShowMinimizeButton(true);
	}

	/**
	 * Method called for preparation of dragging features. To change behaviour,
	 * override this method.
	 */
	protected void prepareDraging ()
	{
		this.setCanDragReposition(true);
		this.setCanDragScroll(false);
	}

	/**
	 * @see sk.qbsw.ondrej.application.client.components.IWindow#prepareHeaderControls(sk.qbsw.ondrej.application.client.screens.IScreen)
	 */
	public void prepareHeaderControls (IScreen screen)
	{
		Canvas itemsLeft = constructControlPanel(screen.getHeaderControlsLeft(), Alignment.LEFT);
		Canvas itemsRight = constructControlPanel(screen.getHeaderControlsRight(), Alignment.RIGHT);

		// no left and right icons
		if (itemsLeft == null && itemsRight == null)
		{
			if (activeIcon != null)
			{
				setHeaderControls(activeIcon, HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.MINIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
			}
			else
			{
				setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.MINIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
			}
		}
		else
		{
			// only right items
			if (itemsLeft == null && itemsRight != null)
			{
				if (activeIcon != null)
				{
					setHeaderControls(activeIcon, HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, itemsRight, HeaderControls.MINIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
				}
				else
				{
					setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, itemsRight, HeaderControls.MINIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
				}
			}
			// only left items
			else if (itemsLeft != null && itemsRight == null)
			{
				if (activeIcon != null)
				{
					setHeaderControls(activeIcon, HeaderControls.HEADER_ICON, itemsLeft, HeaderControls.HEADER_LABEL, HeaderControls.MINIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
				}
				else
				{
					setHeaderControls(HeaderControls.HEADER_ICON, itemsLeft, HeaderControls.HEADER_LABEL, HeaderControls.MINIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
				}
			}
			// left and right items
			else
			{
				if (activeIcon != null)
				{
					setHeaderControls(activeIcon, HeaderControls.HEADER_ICON, itemsLeft, HeaderControls.HEADER_LABEL, itemsRight, HeaderControls.MINIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
				}
				else
				{
					setHeaderControls(HeaderControls.HEADER_ICON, itemsLeft, HeaderControls.HEADER_LABEL, itemsRight, HeaderControls.MINIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
				}
			}
		}
	}

	/**
	 * Method called for preparation of modality. To change behaviour, override
	 * this method.
	 */
	protected void prepareModality ()
	{
		this.setIsModal(false);
		this.setShowModalMask(false);
	}

	/**
	 * this method should be called in overiden prepareCloseHandler for asking
	 * question before window close
	 */
	protected void prepareQuestionCloseClickHandler ()
	{
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
						}
					}
				});
			}
		});
	}

	/**
	 * Prepares shadow of the window.To change behaviour, override this method..
	 */
	protected void prepareShadow ()
	{
		this.setShowShadow(true);
		this.setShadowSoftness(5);
		this.setShadowOffset(6);
	}

	/**
	 * Method called for preparation of window size. To change behaviour,
	 * override this method.
	 */
	protected void prepareSize ()
	{
		this.setSize("800px", "700px");
		this.setMinHeight(500);
		this.setMinWidth(300);
		this.setCanDragResize(true);
	}

	/**
	 * @see IWindow#prepareToolbarControls(IScreen)
	 */
	public void prepareToolbarControls (IScreen screen)
	{
		if (toolbarPanel != null)
		{
			List<Canvas> toolbarControls = screen.getToolbarControls();

			if (toolbarControls.size() > 0)
			{
				for (Canvas control : toolbarControls)
				{
					toolbarPanel.addMember(control);
				}

				toolbarPanel.setBackgroundColor("lightblue");
			}
			else
			{
				toolbarPanel.hide();
			}
		}
	}

	/**
	 * Gets toolbar strip panel
	 * @return
	 */
	protected ToolStrip getToolbarPanel ()
	{
		return toolbarPanel;
	}

	/**
	 * Return active icon to the header of window
	 * @return
	 */
	public Img getActiveIcon() 
	{
		return activeIcon;
	}

	/**
	 * Adds active icon to the header of window
	 * @param activeIcon
	 */
	public void setActiveIcon (Img activeIcon)
	{
		this.activeIcon = activeIcon;
		// activity icon - shows menu...
	}

	/**
	 * Adds menu actions to the Active icon
	 */
	public void setActiveIconMenu (final Menu menu)
	{
		if (activeIcon != null)
		{
			activeIcon.addDoubleClickHandler(new DoubleClickHandler()
			{
				public void onDoubleClick (DoubleClickEvent event)
				{
					destroy();
				}
			});
			activeIcon.addRightMouseDownHandler(new RightMouseDownHandler()
			{
				public void onRightMouseDown (RightMouseDownEvent event)
				{
					menu.setTop(event.getY());
					menu.setLeft(event.getX());
					menu.show();
				}
			});
		}
	}
	
	/**
	 *	@see IControlable#disableByActivityStart 
	 */
	public void disableByActivityStart ()
	{
		Canvas[] items = getItems();
		
		for (Canvas canvas : items)
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
		Canvas[] items = getItems();

		for (Canvas canvas : items)
		{
			if (canvas instanceof IControlable)
			{
				IControlable controlable = (IControlable) canvas;
				controlable.enableByActivityEnd();
			}
		}
	}
}
