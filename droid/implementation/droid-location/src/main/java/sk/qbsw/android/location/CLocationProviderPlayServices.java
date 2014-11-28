package sk.qbsw.android.location;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

/** 
 * The location provider class which implements Google Play Services provides a more powerful, 
 * high-level framework that automates tasks such as location provider choice and power management.
 * <p>
 * If classes want to be notified of location changes, they should implement {@link IOnLocationChangedListener} 
 * and register themselves with the addLocationChangedListener() method.
 * <p>
 * To start the location update process, call the start() method, to stop the update process, call the stop() method.
 * <p> 
 * BEFORE USING THIS FUNCTIONALITY, THE FOLLOWING PERMISSIONS MUST BE SET IN THE ANDROID MANIFEST:
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 * 
 * @author Ludovit Farkas
 * 
 * @since 1.12.0
 * @version 1.12.0  
 * 
 */
public class CLocationProviderPlayServices implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener
{
	public static final String TAG = "CLocationProviderPlayServices";

	private final Set<IOnLocationChangedListener> listeners;

	private LocationClient locationClient;

	private Activity activity;

	// Define an object that holds accuracy and frequency parameters
	LocationRequest mLocationRequest;


	/**
	 * Initializes a new instance of {@link CLocationProviderPlayServices}, using the specified context to
	 * access system services.
	 * The default values for the LocationRequest are (interval = 10000 ms, fastest update interval = 5000 ms, smallest displacement 10 m)
	 */
	public CLocationProviderPlayServices (Activity activity)
	{
		this(activity, LocationRequest.create());
		
		/*
		 * Create a default LocationRequest object in constructor and setup parameters. 
		 */
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(10000);
		mLocationRequest.setFastestInterval(5000);
		mLocationRequest.setSmallestDisplacement(10.0f);
	}
	
	/**
	 * Initializes a new instance of {@link CLocationProviderPlayServices}, using the specified context to
	 * access system services and a {@link LocationRequest} object with defined accuracy and interval values.
	 */
	public CLocationProviderPlayServices (Activity activity, LocationRequest locationRequest)
	{
		listeners = new LinkedHashSet<IOnLocationChangedListener>();
		this.activity = activity;

		/*
		 * Create a new location client, using the enclosing class to
		 * handle callbacks.
		 */
		locationClient = new LocationClient(activity, this, this);
		
		if (locationRequest == null)
		{
			throw new IllegalArgumentException("LocationRequest is null.");
		}
		
		mLocationRequest = locationRequest;
	}

	/**
	 * Adds a listener that will be notified when the user's location changes.
	 */
	public void addOnLocationChangedListener (IOnLocationChangedListener listener)
	{
		listeners.add(listener);
	}

	/**
	 * Removes a listener from the list of those that will be notified when the user's location 
	 * changes.
	 */
	public void removeOnLocationChangedListener (IOnLocationChangedListener listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Removes all listeners from the list of those that will be notified when the user's location 
	 * changes.
	 */
	public void removeAllOnLocationChangedListeners ()
	{
		listeners.clear();
	}

	/**
	 * Starts tracking the user's location. After calling this method, any
	 * {@link IOnLocationChangedListener}s added to this object will be notified of these events.
	 */
	public void start ()
	{
		// Connect the client.
		locationClient.connect();
	}

	/**
	 * Stops tracking the user's location. Listeners will no longer be notified of
	 * these events.
	 */
	public void stop ()
	{
		// Disconnecting the client invalidates it.
		locationClient.disconnect();
	}

	/**
	 * Gets the user's current location.
	 *
	 * @return the user's current location
	 */
	public Location getCurrentLocation ()
	{
		if (locationClient.isConnected())
		{
			return locationClient.getLastLocation();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Notifies all listeners that the user's location has changed.
	 * 
	 * @param location  - the new location
	 */
	private void notifyLocationChanged (Location location)
	{
		for (IOnLocationChangedListener listener : listeners)
		{
			if (listener != null)
			{
				listener.onLocationChanged(location);
			}			
		}
	}

	/*
	 * Called by Location Services if the attempt to
	 * Location Services fails.
	 */
	@Override
	public void onConnectionFailed (ConnectionResult connectionResult)
	{
		/*
		 * Google Play services can resolve some errors it detects.
		 * If the error has a resolution, try sending an Intent to
		 * start a Google Play services activity that can resolve
		 * error.
		 */
		if (connectionResult.hasResolution())
		{
			try
			{
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(activity, 100);
			}
			catch (IntentSender.SendIntentException e)
			{
				// Log the error
				e.printStackTrace();
			}
		}
		else
		{
			// If no resolution is available
			Log.e(TAG, "Connection to location services failed.");
		}
	}

	/*
	 * Called by Location Services when the request to connect the
	 * client finishes successfully. At this point, you can
	 * request the current location or start periodic updates
	 */
	@Override
	public void onConnected (Bundle bundle)
	{
		locationClient.requestLocationUpdates(mLocationRequest, this);
	}

	/*
	 * Called by Location Services if the connection to the
	 * location client drops because of an error.
	 */
	@Override
	public void onDisconnected ()
	{
		Log.e(TAG, "Connection to location services has dropped.");
	}

	/*
	 * Called when the location has changed according to the criteria.
	 */
	@Override
	public void onLocationChanged (Location location)
	{
		notifyLocationChanged(location);
	}
	
	/**
	 * A call to check if the location services are enabled
	 * 
	 * @param context - the current context
	 * @return true if the services are enabled
	 */
	public static boolean getIsLocationServicesEnabled (Context context)
	{
		final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * A call to get the address for the specified location. The call is synchronous and  may take a long time to do its work,
	 * so the resulting address is returned to the included {@link IOnAddressObtainedListener}.
	 * 
	 * @param context - the current context
	 * @param location - the specified location
	 * @param listener - the {@link IOnAddressObtainedListener} to which the address is returned.
	 */
	public static void getAddressForLocation (Context context, Location location, IOnAddressObtainedListener listener)
	{
		GetAddressTask getAddressTask = new GetAddressTask(context, listener);
		getAddressTask.execute(location);
	}

	/**
	 * A subclass of AsyncTask that calls getFromLocation() in the
	 * background. The class definition has these generic types:
	 * 
	 * Location - A Location object containing the current location.
	 * Void     - indicates that progress units are not used
	 * String   - An address passed to onPostExecute()
	 */
	private static class GetAddressTask extends AsyncTask<Location, Void, String>
	{
		WeakReference<Context> contextReference;
		WeakReference<IOnAddressObtainedListener> listenerReference;

		public GetAddressTask (Context context, IOnAddressObtainedListener listener)
		{
			super();
			contextReference = new WeakReference<Context>(context);
			listenerReference = new WeakReference<IOnAddressObtainedListener>(listener);
		}
		
		@Override
		protected void onPreExecute()
		{
			if (listenerReference != null)
			{
				IOnAddressObtainedListener listener = listenerReference.get();
				if (listener != null)
				{
					listener.onAddressFetchingStarted();
				}
			}
		}
		
		@Override
		protected String doInBackground (Location... params)
		{
			// Get the current location from the input parameter list
			Location loc = params[0];
			// Create a list to contain the result address
			List<Address> addresses = null;
			
			if (contextReference != null)
			{
				Context context = contextReference.get();
				if (context != null)
				{
					Geocoder geocoder = new Geocoder(context, Locale.getDefault());
					
					try
					{
						/*
						 * Return 1 address.
						 */
						addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
					}
					catch (IOException e1)
					{
						Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
						e1.printStackTrace();
						return null;
					}
					catch (IllegalArgumentException e2)
					{
						// Error message to post in the log
						String errorString = "Illegal arguments " + Double.toString(loc.getLatitude()) + " , " + Double.toString(loc.getLongitude()) + " passed to address service";
						Log.e("LocationSampleActivity", errorString);
						e2.printStackTrace();
						return null;
					}
				}
			}						
			
			// If the reverse geocode returned an address
			if (addresses != null && addresses.size() > 0)
			{
				// Get the first address
				Address address = addresses.get(0);
				/*
				 * Format the first line of address (if available),
				 * city, and country name.
				 */
				String addressText = String.format("%s, %s, %s",
					// If there's a street address, add it
					address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
					// Locality is usually a city
					address.getLocality(),
					// The country of the address
					address.getCountryName());
				// Return the text
				return addressText;
			}
			else
			{
				return new String("");
			}
		}
		
		@Override
		protected void onPostExecute (String address)
		{
			if (listenerReference != null)
			{
				IOnAddressObtainedListener listener = listenerReference.get();
				if (listener != null)
				{
					listener.onAddressObtained(address);
				}
			}
		}
	}
}