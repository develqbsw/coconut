package sk.qbsw.sgwt.winnetou.client.ui.event;

/**
 * Application event interface
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface IApplicationEvent
{

	/**
	 * Returns the description of this event. This can be a dynamic description
	 * that changes between event instances of the same type.
	 * 
	 * @return The description of this event.
	 */
	public abstract String getDescription ();

	/**
	 * Gets source of the event
	 * @return
	 */
	public abstract IApplicationEventSource getSource ();
}
