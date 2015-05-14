package sk.qbsw.sgwt.winnetou.client.ui.component.panel.menu;

import sk.qbsw.sgwt.winnetou.client.ui.component.panel.APanelHorizontal;
import sk.qbsw.sgwt.winnetou.client.ui.component.panel.IPanel;

import com.smartgwt.client.widgets.Canvas;

/**
 * Panel containing menu bar and login info about user. This panel is
 * responsible for shadow.
 * 
 * @author Dalibor Rak
 * @version 0.1.2
 * @since 0.0.1
 */
public class CMenuPanel extends APanelHorizontal
{
	private Canvas menuBar;

	@Override
	public void initLayout()
	{
		super.initLayout();
		this.setStyleName("panelMenu");

		this.setShowShadow(true);
		this.setShadowSoftness(10);
		this.setShadowOffset(5);
	}

	public void setMenuBar(Canvas menuBar)
	{
		this.menuBar = menuBar;
	}

	@Override
	public void initComponents()
	{
		if (menuBar != null)
		{
			this.addMember(menuBar);
		}

		if (infoPanel != null)
		{
			this.addMember(infoPanel.getLayout());
		}
	}

	/**
	 * Reference to information panel
	 */
	private IPanel infoPanel;

	public void setInfoPanel(IPanel infoPanel)
	{
		this.infoPanel = infoPanel;
	}
}
