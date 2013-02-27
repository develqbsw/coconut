package sk.qbsw.sgwt.winnetou.client.ui.event;

/**
 * Interface for application event listener
 * 
 * @author Dalibor Rak
 * @version 0.1
 * 
 * @param <E> class used for application event
 */
public interface IApplicationEventListener <E extends IApplicationEvent>
{
	/**
	 * Method for handling the event
	 * 
	 * @param event dispatched event
	 */
	public void handle(E event);

	/**
	 * Identifies if event is applicable to listener
	 * 
	 * @param event to check for applicability
	 * @return true if the listener will handle the event
	 */
	public boolean isApplicable(IApplicationEvent event);
}
