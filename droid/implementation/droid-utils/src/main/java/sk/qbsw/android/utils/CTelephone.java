package sk.qbsw.android.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Class to work system services and itents
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public class CTelephone
{
	/**
	 * Method call telephone number
	 * <uses-permission android:name="android.permission.CALL_PHONE" /> - This permission must be added to AndroidManifest file
	 * @param telephoneNumber - number which is called
	 * @param context - applicationContext to call 
	 */
	public static void call (String telephoneNumber, Context context)
	{
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:"+telephoneNumber));
		context.startActivity(callIntent);
	}
}
