package sk.qbsw.android.utils;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

/**
 * Class to work system preferences and system configuration
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.2.0
 */
public class CSystemConfiguration
{
	/** get int value from application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @return preference value or null if preference value doesn't exist
	 */
	public static Integer getInt (Context context, String preferenceName)
	{
		Integer preference = null;
		if (contains(context, preferenceName))
		{
			SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			preference = appPreferences.getInt(preferenceName, -1);
		}
		return preference;
	}

	/** set integer value to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param intToAdd int to add to preferences
	 */
	public static void setInt (Context context, String preferenceName, Integer intToAdd)
	{
		SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = appPreferences.edit();
		editor.putInt(preferenceName, intToAdd);
		editor.commit();
	}
	
	/** get long value from application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @return preference value or null if preference value doesn't exist
	 */
	public static Long getLong (Context context, String preferenceName)
	{
		Long preference = null;
		if (contains(context, preferenceName))
		{
			SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			preference = appPreferences.getLong(preferenceName, -1);
		}
		return preference;
	}

	/** set long value to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param longToAdd long to add to preferences
	 */
	public static void setLong (Context context, String preferenceName, Long longToAdd)
	{
		SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = appPreferences.edit();
		editor.putLong(preferenceName, longToAdd);
		editor.commit();
	}
	
	/** set long value, if not exists, to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param longToAdd long to add to preferences
	 */
	public static void setLongIfNotExist (Context context, String preferenceName, Long longToAdd)
	{
		if (!contains(context, preferenceName))
		{
			setLong(context, preferenceName, longToAdd);
		}

	}

	/** set int value, if not exists, to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param intToAdd int to add to preferences
	 */
	public static void setIntIfNotExist (Context context, String preferenceName, Integer intToAdd)
	{
		if (!contains(context, preferenceName))
		{
			setInt(context, preferenceName, intToAdd);
		}

	}

	/** get date value from application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @return preference value or null if preference value doesn't exist
	 */
	public static Date getDate (Context context, String preferenceName)
	{
		Date preference = null;
		if (contains(context, preferenceName))
		{
			SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			preference = new Date(appPreferences.getLong(preferenceName, -1));
		}

		return preference;
	}

	/** set date value to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param dateToAdd date to add to preferences
	 */
	public static void setDate (Context context, String preferenceName, Date dateToAdd)
	{
		SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = appPreferences.edit();
		editor.putLong(preferenceName, dateToAdd.getTime());
		editor.commit();
	}

	/** set date value to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param dateToAdd date to add to preferences
	 */
	public static void setDateIfNotExist (Context context, String preferenceName, Date dateToAdd)
	{
		if (!contains(context, preferenceName))
		{
			setDate(context, preferenceName, dateToAdd);
		}

	}

	/**
	 * check when application preference exist
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @return true if preference exist false otherwise
	 */
	public static Boolean contains (Context context, String preferenceName)
	{
		SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return appPreferences.contains(preferenceName);
	}
	
	/**
	 * remove key from application preferences
	 * @param context context of application
	 * @param preferenceName string name of application preferences
	 * @return true if preference exist false otherwise
	 */
	public static void remove (Context context, String preferenceName)
	{
		SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = appPreferences.edit();
		editor.remove(preferenceName);
		editor.commit();
	}

	/** get float value from application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @return preference value or null if preference value doesn't exist
	 */
	public static Float getFloat (Context context, String preferenceName)
	{
		Float preference = null;
		if (contains(context, preferenceName))
		{
			SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			preference = appPreferences.getFloat(preferenceName, -1);
		}
		return preference;
	}

	/** set float value to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param floatToAdd float to add to preferences
	 */
	public static void setFloat (Context context, String preferenceName, Float floatToAdd)
	{
		SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = appPreferences.edit();
		editor.putFloat(preferenceName, floatToAdd);
		editor.commit();
	}
	
	/** set float value, if not exists, to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param floatToAdd float to add to preferences
	 */
	public static void setFloatIfNotExist (Context context, String preferenceName, Float floatToAdd)
	{
		if (!contains(context, preferenceName))
		{
			setFloat(context, preferenceName, floatToAdd);
		}

	}

	/** get string value from application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @return preference value or null if preference value doesn't exist
	 */
	public static String getString (Context context, String preferenceName)
	{
		String preference = null;
		if (contains(context, preferenceName))
		{
			SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			preference = appPreferences.getString(preferenceName, "");
		}
		return preference;
	}

	/** set string value to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param stringToAdd string to add to preferences
	 */
	public static void setString (Context context, String preferenceName, String stringToAdd)
	{
		SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = appPreferences.edit();
		editor.putString(preferenceName, stringToAdd);
		editor.commit();
	}

	/** set string value, if not exists, to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param stringToAdd string to add to preferences
	 */
	public static void setStringIfNotExist (Context context, String preferenceName, String stringToAdd)
	{
		if (!contains(context, preferenceName))
		{
			setString(context, preferenceName, stringToAdd);
		}

	}

	/**
	 * get version of application
	 * @param context context of application
	 * @return app version
	 * @throws NameNotFoundException when version tag is not found in manifest xml
	 */
	public static String getAppVersion (Context context) throws NameNotFoundException
	{
		return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

	}
	
	/** get boolean value from application preferences
	 * @param context context of application
	 * @param preferenceName string name of application preferences
	 * @return preference value or null if preference value doesn't exist
	 */
	public static Boolean getBoolean (Context context, String preferenceName)
	{
		Boolean preference = null;
		if (contains(context, preferenceName))
		{
			SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			preference = appPreferences.getBoolean(preferenceName, Boolean.FALSE);
		}

		return preference;
	}

	/** set date value to application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param booleanToAdd boolean to add to preferences
	 */
	public static void setBoolean (Context context, String preferenceName, Boolean booleanToAdd)
	{
		SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = appPreferences.edit();
		editor.putBoolean(preferenceName, booleanToAdd);
		editor.commit();
	}

	/** set boolean value to application preferences only if value not exist in application preferences
	 * @param context context of application
	 * @param preferenceName sting name of application preferences
	 * @param booleanToAdd boolean to add to preferences
	 */
	public static void setBooleanIfNotExist (Context context, String preferenceName, Boolean booleanToAdd)
	{
		if (!contains(context, preferenceName))
		{
			setBoolean(context, preferenceName, booleanToAdd);
		}

	}
}
