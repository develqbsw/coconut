package sk.qbsw.android.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Class to get information about device like device id imei code etc.
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.2.0
 */
public class CDevice
{
	/**
	 * get id of android device
	 * @param context context of application
	 * @return return androidId 
	 * 	<b>this can be null sometimes</b> 
	 * 	see: http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
	 */
	public static String getAndroidId (Context context)
	{
		String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		return androidId;
	}
	
	/**
	 * save actual output from android logcat to file which come as parameter
	 * @throws IOException when file doesn't exists or error in the logcat execution
	 */
	public static Process logToFile(File fileToSaveLogs) throws IOException
	{
		    String cmd = "logcat -d -v time -f "+fileToSaveLogs.getAbsolutePath();
		    return Runtime.getRuntime().exec(cmd);
	}
	
	/**
	 * save actual output from android logcat to file which come as parameter 
	 * <b>!!! WARNING this process is not application process but  process of the system and don't stop when application crash or stop you must manage life cycle of this process alone
	 * but if is application killed for example by user(then application and services are killed), then you cannot detect end of application and you cannot kill the process which save log to file 
	 * these means that process newer ENDS !!!
	 * @throws IOException when file doesn't exists or error in the logcat execution
	 */
	public static Process logToFileNoEnd(File fileToSaveLogs) throws IOException
	{
		    String cmd = "logcat -v time -r 4096 -s -f "+fileToSaveLogs.getAbsolutePath()+" mObhliadka ACRA ActivityManager nukona-wrap nukona-ccapi AndroidRuntime SQLiteDatabase";
		    return Runtime.getRuntime().exec(cmd);
	}
	
	/**
	 * get info of system
	 * @return info of system
	 */
	public static String getSystemInfo ()
	{
		StringBuffer info = new StringBuffer();

		info.append("\n BOARD: ");
		info.append(Build.BOARD);
		info.append("\n BOOTLOADER: ");
		info.append(Build.BOOTLOADER);
		info.append("\n BRAND: ");
		info.append(Build.BRAND);
		info.append("\n BOOTLOADER: ");
		info.append(Build.BOOTLOADER);
		info.append("\n DEVICE: ");
		info.append(Build.DEVICE);
		info.append("\n DISPLAY: ");
		info.append(Build.DISPLAY);
		info.append("\n FINGERPRINT: ");
		info.append(Build.FINGERPRINT);
		info.append("\n HARDWARE: ");
		info.append(Build.HARDWARE);
		info.append("\n HOST: ");
		info.append(Build.HOST);
		info.append("\n ID: ");
		info.append(Build.ID);
		info.append("\n MANUFACTURER: ");
		info.append(Build.MANUFACTURER);
		info.append("\n MODEL: ");
		info.append(Build.MODEL);
		info.append("\n PRODUCT: ");
		info.append(Build.PRODUCT);
		info.append("\n TAGS: ");
		info.append(Build.TAGS);
		info.append("\n TIME: ");
		info.append(Build.TIME);
		info.append("\n TYPE: ");
		info.append(Build.TYPE);
		info.append("\n USER: ");
		info.append(Build.USER);
		info.append("\n VERSION.CODENAME: ");
		info.append(Build.VERSION.CODENAME);
		info.append("\n VERSION.INCREMENTAL: ");
		info.append(Build.VERSION.INCREMENTAL);
		info.append("\n VERSION.RELEASE: ");
		info.append(Build.VERSION.RELEASE);
		info.append("\n VERSION.SDK_INT: ");
		info.append(Build.VERSION.SDK_INT);

		return info.toString();

	}
	
	/**
	 * check if is google maps service installed if is installed the return true if isn't installed return false
	 * @param context application context
	 * @return true if are google play service installed false otherwise
	 */
	public static boolean isGoogleMapsInstalled(Context context)
	{
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		if(status == ConnectionResult.SUCCESS) {
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}

	
}
