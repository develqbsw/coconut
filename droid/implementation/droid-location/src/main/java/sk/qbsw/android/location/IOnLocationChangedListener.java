package sk.qbsw.android.location;

import android.location.Location;

/** 
 * Listener for location changed events.
 * <p>
 * Classes should implement this interface if they want to be notified of changes in the user's
 * location.
 * 
 * @author Ludovit Farkas
 * 
 * @since 1.12.0
 * @version 1.12.0
 */
public interface IOnLocationChangedListener 
{
	/**
	 * Called when the user's location changes.
	 *
	 * @param location - the new location
	 */
	void onLocationChanged (Location location);
}
