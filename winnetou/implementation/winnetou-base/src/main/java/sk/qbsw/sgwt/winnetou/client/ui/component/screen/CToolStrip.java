package sk.qbsw.sgwt.winnetou.client.ui.component.screen;

import sk.qbsw.sgwt.winnetou.client.ui.component.window.IControlable;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

/**
 * Layout from ToolStrip
 * @author Pavol Kucka
 * @version 0.1
 * @since 0.1
 */
public class CToolStrip extends ToolStrip implements IControlable
{

	/**
	 *	@see IControlable#disableByActivityStart 
	 */
	public void disableByActivityStart ()
	{
		Canvas[] chrildren = getChildren();

		for (Canvas canvas : chrildren)
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
		Canvas[] chrildren = getChildren();

		for (Canvas canvas : chrildren)
		{
			if (canvas instanceof IControlable)
			{
				IControlable controlable = (IControlable) canvas;
				controlable.enableByActivityEnd();
			}
		}
	}

}
