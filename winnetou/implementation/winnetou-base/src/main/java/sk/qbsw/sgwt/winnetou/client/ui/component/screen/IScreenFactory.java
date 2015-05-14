package sk.qbsw.sgwt.winnetou.client.ui.component.screen;

import sk.qbsw.sgwt.winnetou.client.ui.component.window.IWindow;

/**
 * Screen factory is responsible for instantiating screen and window after
 * clicking on menu item
 * 
 * @author Dalibor Rak
 * @since 0.1
 * @version 0.1
 * 
 */
public interface IScreenFactory
{
	/**
	 * Instanciates appropriate screen
	 * 
	 * @return screen instance
	 */
	public IScreen getScreen();

	/**
	 * ID of the screen to instantiate
	 * 
	 * @return screen ID
	 */
	public String getScreenId();

	/**
	 * Window in which should be the screen shown
	 * 
	 * @return
	 */
	public IWindow getWindow();
}
