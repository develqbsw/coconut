package sk.qbsw.android.location;

/** 
 * Listener for address fetching events.
 * <p>
 * Classes should implement this interface if they want to be notified of the address for the specified location.
 * 
 * @author Ludovit Farkas
 * 
 * @since 1.12.0
 * @version 1.12.0
 */
public interface IOnAddressObtainedListener
{
	/**
	 * Called when the address fetching is started to start a progressbar.
	 *
	 */
	void onAddressFetchingStarted ();
	
	/**
	 * Called when the {@link CLocationManager} obtains the address.
	 *
	 * @param address - the obtained address
	 */
	void onAddressObtained (String address);
}
