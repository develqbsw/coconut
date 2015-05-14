package sk.qbsw.sgwt.winnetou.client.ui.component.screen;

import java.util.List;

import sk.qbsw.sgwt.winnetou.client.ui.component.IUIComponent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * Interface used by every screen in the application
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface IScreen extends IApplicationEventSource, IUIComponent
{
	/**
	 * Gets header controls located on the left side of title
	 */
	public List<Canvas> getHeaderControlsLeft ();

	/**
	 * Gets header controls located on the right side of title
	 */
	public List<Canvas> getHeaderControlsRight ();

	/**
	 * Gets window toolbar controls panel components
	 * @return
	 */
	public List<Canvas> getToolbarControls ();

	/**
	 * Method for getting GWT Canvas component as layout
	 * 
	 * @return
	 */
	public Layout getLayout ();

	/**
	 * Id of the screen (R-P01)
	 * 
	 * @return
	 */
	public String getScreenId ();

	/**
	 * Retrieves title of the window
	 * 
	 * @return
	 */
	public String getScreenTitle ();

	/**
	 * Hides screen: sets visibility to false and size to 0%
	 */
	public void hideScreen ();

	/**
	 * Loads data used on the screen for general purpose. Record specific data
	 * can be loaded by other methods
	 */
	public void loadData ();

	/**
	 * Shows screen: sets visibility to true and size to 100%
	 */
	public void showScreen ();

	/**
	 * Starts automatic focusing of items of form
	 */
	public void autoFocus ();

}
