package sk.qbsw.sgwt.winnetou.client.ui.component.window;


/**
 * Base interface for window
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface IControlable 
{
	/**
	 * Disable all elements by Start activity
	 * @return
	 */
	public  void  disableByActivityStart();

	
	/**
	 *  Enable all elements by End activity
	 */
	public  void  enableByActivityEnd();
}
