package sk.qbsw.android.ui.activity;

import sk.qbsw.android.ui.activity.broardcast.EBroadcastAction;
import sk.qbsw.android.ui.activity.broardcast.EBroadcastActionType;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Add method which is called when come notification from outside(e.g service)
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.4.0
 */
public class CNotificatedActivity extends CActiveActivity
{
	/**
	 * receiver which receive event from outside
	 */
	private BroadcastReceiver outSideReceiver;

	@Override
	protected void onCreate (Bundle bundle)
	{
		if (bundle == null)
		{
			//create receivers
			createOutsideReceiver();
			//register them
			registerOutsideReceiver();
		}
		super.onCreate(bundle);
	}

	//if bundle is not null the receiver is registered in onrestore because activities using fragment adapter creates fragments in onrestorestate
	@Override
	protected void onRestoreInstanceState (Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null)
		{
			//create receivers
			createOutsideReceiver();
			//register them
			registerOutsideReceiver();
		}
	}

	@Override
	protected void onDestroy ()
	{
		unregisterOutSideReceiver();
		super.onDestroy();
	}

	/**
	 * register receiver to get events from outside with priority 1
	 */
	private void registerOutsideReceiver ()
	{
		IntentFilter filter = new IntentFilter(EBroadcastAction.OUTSIDE_RESPONSE.getValue());
		filter.setPriority(1);
		registerReceiver(this.outSideReceiver, filter);
	}

	private void createOutsideReceiver ()
	{
		//is called after detail has updated
		this.outSideReceiver = new BroadcastReceiver()
		{

			@Override
			public void onReceive (Context context, Intent intent)
			{
				EBroadcastActionType type = getTypeFromIntent(intent);

				//this event call always
				onOutsideNotification(context, intent, type);

				if (isActive())
				{
					//this event is call only when is activity in foreground
					onOutsideActiveNotification(context, intent, type);
				}
			}
		};

	}

	private EBroadcastActionType getTypeFromIntent (Intent intent)
	{
		return (EBroadcastActionType) intent.getSerializableExtra("type");

	}

	/**
	 * unregister outside receiver from activity
	 */
	private void unregisterOutSideReceiver ()
	{
		//because in some causes is this receiver null I don't find reason for this.(maybe in rotate )
		if (this.outSideReceiver != null)
		{
			unregisterReceiver(this.outSideReceiver);
		}
	}

	/**
	 * method for overload is called always when come notification from outside
	 * @param context application content
	 * @param intent intent which can include data which are send to activity
	 */
	protected void onOutsideNotification (Context context, Intent intent, EBroadcastActionType type)
	{

	}

	/**
	 * method for overload is called when come notification from outside ansd activty is in foreground
	 * @param context application content
	 * @param intent intent which can include data which are send to activity
	 */
	protected void onOutsideActiveNotification (Context context, Intent intent, EBroadcastActionType type)
	{

	}
}
