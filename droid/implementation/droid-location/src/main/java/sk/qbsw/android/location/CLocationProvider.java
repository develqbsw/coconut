package sk.qbsw.android.location;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/** 
 * The location provider class which does not need Google Play Services to run. However,
 * it handles the location accuracy by itself and is not as battery efficient as the Google Play version, but is more low-level.
 * <p>
 * If classes want to be notified of location changes, they should implement {@link IOnLocationChangedListener}
 * or extend {@link ALocationEventListener} and register themselves with the appropriate method 
 * (addLocalizationEventListener() or addLocationChangedListener()).
 * <p>
 * To start the location update process, call the open() method, to stop the update process, call the close() method.
 * <p> 
 * BEFORE USING THIS FUNCTIONALITY, THE FOLLOWING PERMISSIONS MUST BE SET IN THE ANDROID MANIFEST:
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 * 
 * @author Michal Lacko
 * @author Ludovit Farkas
 * 
 * @since 0.1.0
 * @version 1.12.0  
 * 
 */
public class CLocationProvider
{
	//time in miliseconds (every minute)
	private static final int TIME_BETWEEN_CHECK_POSITION = 1000 * 60;

	//distance in meters(every ten meters)
	private static final float DISTANCE_BETWEEN_CHECK_POSITION = 10;
	private Context context;
	
	@SuppressWarnings ("deprecation")
	private List<ALocationEventListener> listeners;
	private List<IOnLocationChangedListener> locationChangedListeners;
	
	private Location location;
	private LocationListener locationListener;
	private LocationManager locationManager;

	@SuppressWarnings ("deprecation")
	public CLocationProvider (Context context)
	{
		super();
		this.context = context;
		this.listeners = new ArrayList<ALocationEventListener>();
		this.locationChangedListeners = new ArrayList<IOnLocationChangedListener>();
		init();
	}


	/** This methods allows classes to register for Events
	 * @param listener to add
	 */
	@SuppressWarnings ("deprecation")
	public void addLocalizationEventListener (ALocationEventListener listener)
	{
		listeners.add(listener);
	}
	
	/** 
	 * This methods allows classes to register for location changes
	 * @param listener to add
	 */
	public void addLocationChangedListener (IOnLocationChangedListener listener)
	{
		locationChangedListeners.add(listener);
	}

	/** 
	 * Get actual Latitude
	 * @return return Latitude in double or null if gps data are not loaded
	 */
	public Double getLatitude ()
	{
		return (this.location == null) ? null : (double) this.location.getLatitude();
	}

	/** 
	 * Get actual Longitude
	 * @return return Longitude in double or null if gps data are not loaded
	 */
	public Double getLongitude ()
	{
		return (this.location == null) ? null : (double) this.location.getLongitude();
	}

	/**
	 * Get last known location
	 * @return Last known location
	 */
	public Location getLastKnownLocation ()
	{
		return this.location;
	}

	private void init ()
	{
		// Acquire a reference to the system Location Manager
		this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		this.locationListener = new LocationListener()
		{
			@Override
			public void onLocationChanged (Location newLocation)
			{
				// Called when a new location is found by the location provider.
				if (isBetterLocation(newLocation, location))
				{
					CLocationProvider.this. location = newLocation;
					onEventOccured();
				}
			}

			@Override
			public void onProviderDisabled (String provider)
			{
			}

			@Override
			public void onProviderEnabled (String provider)
			{
			}

			@Override
			public void onStatusChanged (String provider, int status, Bundle extras)
			{
			}
		};

		//set default location
		Location newLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (isBetterLocation(newLocation, location))
		{
			this.location = newLocation;
		}
		newLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (isBetterLocation(newLocation, location))
		{
			this.location = newLocation;
		}

	}

	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	protected boolean isBetterLocation (Location location, Location currentBestLocation)
	{
		if (currentBestLocation == null)
		{
			// A new location is always better than no location
			return true;
		}
		else if (location == null)
		{
			//if is new null then no update
			return false;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TIME_BETWEEN_CHECK_POSITION;
		boolean isSignificantlyOlder = timeDelta < -TIME_BETWEEN_CHECK_POSITION;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use the new location
		// because the user has likely moved
		if (isSignificantlyNewer)
		{
			return true;
			// If the new location is more than two minutes older, it must be worse
		}
		else if (isSignificantlyOlder)
		{
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and accuracy
		if (isMoreAccurate)
		{
			return true;
		}
		else if (isNewer && !isLessAccurate)
		{
			return true;
		}
		else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider)
		{
			return true;
		}
		return false;
	}

	/** 
	 * Checks whether two providers are the same
	 * 
	 * @param provider1 first provider
	 * @param provider2 second provider
	 * @return true if providers are same else otherwise 
	 */
	private boolean isSameProvider (String provider1, String provider2)
	{
		if (provider1 == null)
		{
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}


	/** 
	 * This method is called when the location has changed.
	 * 
	 */
	@SuppressWarnings ("deprecation")
	private void onEventOccured ()
	{
		for (ALocationEventListener listener : this.listeners)
		{
			listener.onLocationChanged(this.location);
		}
		for (IOnLocationChangedListener listener : this.locationChangedListeners)
		{
			listener.onLocationChanged(this.location);
		}
	}

	/**
	 * run process to get data from gps
	 */
	public void open ()
	{
		//check if is provider enabled(usually when is gps disabled in device IN emulator throw exception IlegalArgumentException for method locationManager.requestLocationUpdates and NETWORK_PROVIDER)
		if (this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			// Register the listener with the Location Manager to receive location updates for METWORK
			this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME_BETWEEN_CHECK_POSITION, DISTANCE_BETWEEN_CHECK_POSITION, locationListener);
		}

		//check if is provider enabled(usually when is gps disabled in device)
		if (this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			//Register the listener with the Location Manager to receive location updates for GPS
			this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_BETWEEN_CHECK_POSITION, DISTANCE_BETWEEN_CHECK_POSITION, locationListener);
		}
	}

	/**
	 * close connection to get gps coordinates can be called in onDestroy method in activity
	 */
	public void close ()
	{
		// Acquire a reference to the system Location Manager
		if (this.locationManager != null)
		{
			this.locationManager.removeUpdates(locationListener);
		}
	}

	/**
	 * Find out if provider's last location has sufficient accuracy
	 * @param requestedAccuracy Requested accuracy
	 * @return Boolean value of sufficient accuracy
	 */
	public Boolean hasSufficientAccuracy (float requestedAccuracy)
	{
		return hasSufficientAccuracy(this.location, requestedAccuracy);
	}

	/**
	 * Find out if given location has sufficient accuracy
	 * @param location Given location
	 * @param requestedAccuracy Requested accuracy
	 * @return Boolean value of sufficient accuracy
	 */
	public Boolean hasSufficientAccuracy (Location location, float requestedAccuracy)
	{
		Float accuracy = location.getAccuracy();

		if (accuracy != 0.0 && accuracy <= requestedAccuracy)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** 
	 * This methods allows classes to unregister for Events
	 * @param listener listener to remove
	 */
	@SuppressWarnings ("deprecation")
	public void removeLocalizationEventListener (ALocationEventListener listener)
	{
		listeners.remove(listener);
	}
	
	/** 
	 * This methods allows classes to unregister from location changed events.
	 * 
	 * @param listener listener to remove
	 */
	public void removeLocationChangedListener (IOnLocationChangedListener listener)
	{
		locationChangedListeners.remove(listener);
	}
}
