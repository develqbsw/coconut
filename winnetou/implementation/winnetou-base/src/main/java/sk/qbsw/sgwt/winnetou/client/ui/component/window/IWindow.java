package sk.qbsw.sgwt.winnetou.client.ui.component.window;

import sk.qbsw.sgwt.winnetou.client.ui.component.IUIComponent;
import sk.qbsw.sgwt.winnetou.client.ui.component.screen.IScreen;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

import com.smartgwt.client.widgets.Window;

/**
 * Base interface for window
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface IWindow extends IUIComponent, IApplicationEventSource
{

	/**
	 * Gets screen
	 * 
	 * @param canvas
	 */
	public void addItem (IScreen canvas);

	/**
	 * Gets window instance
	 * 
	 * @return
	 */
	public Window getWindow ();

	/**
	 * Adds controls to the header
	 * 
	 * @param screen
	 *            screen to use
	 * @param window
	 *            window to add controls
	 */
	public abstract void prepareHeaderControls (IScreen screen);

	/**
	 * Adds controls to the toolbar
	 * 
	 * @param screen
	 *            screen to use
	 * @param window
	 *            window to add controls
	 */
	public abstract void prepareToolbarControls (IScreen screen);

}
