package sk.qbsw.sgwt.winnetou.client.ui.component.panel;

import sk.qbsw.sgwt.winnetou.client.ui.component.panel.APanelVertical;

import com.smartgwt.client.widgets.Canvas;

/**
 * 
 * Panel, attachemd to main div. It is responsible for showing and hidin other
 * panels
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CMainPanel extends APanelVertical
{
	public void initLayout()
	{
		setWidth("97%");
		setHeight("95%");
	}

	/**
	 * sets child of this panel (other child will be removes)
	 * 
	 * @param newChild
	 *            child to add
	 */
	public void setChild(Canvas newChild)
	{
		Canvas[] chrildren = getChildren();

		for (Canvas canvas : chrildren)
		{
			removeChild(canvas);
		}

		this.addMember(newChild);
	}
}
