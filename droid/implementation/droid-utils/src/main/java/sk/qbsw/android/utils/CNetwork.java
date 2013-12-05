package sk.qbsw.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Class to work with network ussually check network availability
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public class CNetwork
{
	/**
	 * Check if is network available
	 * @param applicationContext to get system service
	 * @return true if is network available or false if not
	 */
	public static boolean isNetworkAvailable (Context applicationContext)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

		return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}

	/**
	 * Check if is network available
	 * @param applicationContext to get system service
	 * @return true if is network available or false if not
	 */
	public static boolean is3GAvailable (Context applicationContext)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		
		//if is no GSM
		boolean is3G = (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) && (activeNetworkInfo.getSubtype() != TelephonyManager.NETWORK_TYPE_EDGE && activeNetworkInfo.getSubtype() != TelephonyManager.NETWORK_TYPE_GPRS);
		
		return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting() && is3G;
	}

	/**
	 * 
	 * Check if is network available
	 * @param applicationContext to get system service
	 * @return true if is network available or false if not
	 */
	public static boolean isWifiAvailable (Context applicationContext)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

		return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting() && (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI || activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIMAX);
	}
}
