package sk.qbsw.android.location;

import android.location.Location;

/** Listener for localization events
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
@Deprecated
public abstract class ALocationEventListener
{
	/** called when is location changed
	 * @param location
	 */
	public abstract void onLocationChanged (Location location);
}
