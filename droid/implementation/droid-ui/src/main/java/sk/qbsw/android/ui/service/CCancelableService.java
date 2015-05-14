package sk.qbsw.android.ui.service;

import java.sql.SQLException;

import sk.qbsw.android.CApplication;
import sk.qbsw.android.exception.CExceptionHandler;
import sk.qbsw.android.service.exception.CServiceCanceledException;
import sk.qbsw.android.service.task.response.CServiceCancelModel;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service for synchronization where service cancel is allowed and when service is destroyed then cancel model is set to true and method checkCanceled throws CServiceCanceledException
 * 
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.4.0
 */
public abstract class CCancelableService extends CNotificationService
{
	/**
	 * Indices when user cancel thread - then is cancelled thread end last used operation and finish
	 */
	private CServiceCancelModel cancelModel;

	@Override
	public IBinder onBind (Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate ()
	{
		this.cancelModel = new CServiceCancelModel();
		this.cancelModel.setIsCanceled(Boolean.FALSE);

		super.onCreate();
	}

	/**
	 * method check when is thread cancelled by user and if is canceled by user then throws exception 
	 * @throws CServiceCanceledException when is thread canceled by user
	 */
	protected void checkCanceled () throws CServiceCanceledException
	{
		//when is thread canceled then no continue with synchronization
		if (this.cancelModel.getIsCanceled())
		{
			throw new CServiceCanceledException("thread cancelled");
		}

		if ( ((CApplication) getApplication()).isApplicationCrashed())
		{
			throw new CServiceCanceledException("application crashed and treand is cancelled");
		}
	}

	/**
	 * check if is service cancelled don't throw any exception
	 * @return return true if is service cancelled false otherwise
	 */
	protected Boolean isCancelled ()
	{
		if (this.cancelModel.getIsCanceled())
		{
			return Boolean.TRUE;
		}

		if ( ( ((CApplication) getApplication()).isApplicationCrashed()))
		{
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}

	@Override
	public void onDestroy ()
	{
		this.cancelModel.setIsCanceled(Boolean.TRUE);
		super.onDestroy();
	}


	@Override
	public int onStartCommand (Intent intent, int flags, int startId)
	{
		try
		{
			if (intent != null)
			{
				init(this);

				onRun(intent, flags, startId);
			}
		}
		catch (SQLException e)
		{
			//when is error on database then end service
			CExceptionHandler.logException(this.getClass(), e);
		}

		return Service.START_NOT_STICKY;
	}

	/**
	 * method which is called after is service initialized
	 * @param intent  The Intent supplied to startService(Intent), as given. This may be null if the service is being restarted after its process has gone away, and it had previously returned anything except START_STICKY_COMPATIBILITY. 
	 * @param flags  Additional data about this start request. Currently either 0, START_FLAG_REDELIVERY, or START_FLAG_RETRY. 
	 * @param startId  A unique integer representing this specific request to start. Use with stopSelfResult(int). 
	 */
	protected abstract void onRun (Intent intent, int flags, int startId);

	
	/**
	 * method to rewrite to init service
	 * @param applicationContext application content not context of the activity
	 * @throws SQLException when is error in database
	 */
	protected void init (Context applicationContext) throws SQLException
	{
	}
	
	public CServiceCancelModel getCancelModel ()
	{
		return cancelModel;
	}
}
