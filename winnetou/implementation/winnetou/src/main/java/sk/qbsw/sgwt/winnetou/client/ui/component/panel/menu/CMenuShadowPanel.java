package sk.qbsw.sgwt.winnetou.client.ui.component.panel.menu;

import sk.qbsw.sgwt.winnetou.client.ui.component.panel.APanelHorizontal;
import sk.qbsw.sgwt.winnetou.client.ui.component.panel.IPanel;

/**
 * Internal Menu panel responsible for padding of the menu
 * 
 * @author Dalibor Rak
 * @version 0.1.2
 * @since 0.1.2
 * 
 */
public class CMenuShadowPanel extends APanelHorizontal
{
	IPanel menuPanel;

	public CMenuShadowPanel()
	{
		super();
	}

	/**
	 * Initializes members
	 */
	public void initComponents()
	{
		addMember(menuPanel.getLayout());
	}

	/**
	 * Initializes layout
	 */
	public void initLayout()
	{
		this.setWidth100();
		this.setHeight(94);

		this.setStyleName("panelMenuShadow");
	}

	/**
	 * Initialized panel used as menu
	 * 
	 * @param panel
	 *            panel for menu
	 */
	public void setMenuPanel(IPanel panel)
	{
		menuPanel = panel;
	}
}