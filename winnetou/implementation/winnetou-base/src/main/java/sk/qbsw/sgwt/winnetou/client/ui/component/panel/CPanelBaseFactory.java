package sk.qbsw.sgwt.winnetou.client.ui.component.panel;

import com.smartgwt.client.widgets.layout.Layout;

/**
 * Factory for panels.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CPanelBaseFactory
{
	private static Layout workPanel;

	/**
	 * Gets work panel used by the application
	 * 
	 * @return Layout of work panel
	 */
	public final static Layout getWorkPanel()
	{
		return workPanel;
	}

	/**
	 * Sets work panel used by the application
	 * 
	 * @param workPanel
	 */
	public static final void setWorkPanel(Layout workPanel)
	{
		CPanelBaseFactory.workPanel = workPanel;
	}
}