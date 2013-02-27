package sk.qbsw.sgwt.winnetou.client.ui.event;


/**
 * Interface for event executor. (Source of application event)
 * 
 * @author Dalibor Rak
 * @version 0.1
 */
public interface IApplicationEventSource
{
	/**
	 * method for adding new listener
	 * 
	 * @param listener
	 *            listener to be added
	 */
	@SuppressWarnings ("unchecked")
	void addListener (IApplicationEventListener listener);

	/**
	 * removes all listeners
	 */
	void clearListeners ();

	/**
	 * method for removing new listener
	 * 
	 * @param listener
	 *            listener to be removed
	 */
	@SuppressWarnings ("unchecked")
	void removeListener (IApplicationEventListener listener);

	/**
	 * Fires event
	 * @param event
	 */
	void fireEvent (IApplicationEvent event);
}
