package sk.qbsw.sgwt.winnetou.client.ui.component.window;

import sk.qbsw.sgwt.winnetou.client.ui.component.window.container.CDefaultAppWindow;

/**
 * Window factory is responsible for generating new window
 * 
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
public class CDefaultAppWindowFactory implements IWindowFactory
{
	/**
	 * Generates window
	 */
	public IWindow generateWindow ()
	{
		return new CDefaultAppWindow();
	}
}
