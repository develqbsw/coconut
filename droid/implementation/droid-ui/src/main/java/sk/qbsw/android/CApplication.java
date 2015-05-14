package sk.qbsw.android;

import sk.qbsw.android.CQBSWAndroidLoggerTag;
import android.app.Application;
import android.util.Log;

/**
 * @author Michal Lacko
 * @since 0.2.0
 * @version 0.4.0
 * class represent default application class which represent whole application
 */
public class CApplication extends Application implements Thread.UncaughtExceptionHandler
{
	private Thread.UncaughtExceptionHandler defaultUEH;

	private Boolean applicationCrashed;

	@Override
	public void onCreate ()
	{
		this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		//set default handler when application crash
		Thread.setDefaultUncaughtExceptionHandler(this);
		applicationCrashed = Boolean.FALSE;
		
		super.onCreate();
	}

	@Override
	public void uncaughtException (Thread thread, Throwable ex)
	{
		Log.e(CQBSWAndroidLoggerTag.TAG, "CApplication CRASHED", ex);

		//when application crashed set notification about application crash
		this.applicationCrashed = Boolean.TRUE;

		//throws exception to screen
		this.defaultUEH.uncaughtException(thread, ex);
	}

	public Boolean isApplicationCrashed ()
	{
		return applicationCrashed;
	}

}
