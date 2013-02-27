package sk.qbsw.sgwt.winnetou.client.ui.component.panel;

import sk.qbsw.sgwt.winnetou.client.ui.component.IUIComponent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface IPanel extends IUIComponent, IApplicationEventSource
{
	/**
	 * Adds child to panel
	 * 
	 * @param child
	 *            child to add
	 * @return me
	 */
	public Canvas addChild(Canvas child);

	/**
	 * Method for getting GWT Canvas component as layout
	 * 
	 * @return layout of the panel
	 */
	public Layout getLayout();

	/**
	 * Loads data from the server
	 */
	public void loadData();

	/**
	 * Focuses default items
	 */
	public void autoFocus();

}
