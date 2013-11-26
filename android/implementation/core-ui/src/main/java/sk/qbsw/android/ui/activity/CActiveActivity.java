package sk.qbsw.android.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Add atribute which indicies if is activity on foreground
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.4.21
 */
public class CActiveActivity extends FragmentActivity
{
	private Boolean isActive;

	@Override
	protected void onCreate (Bundle bundle)
	{
		//when creating activity then set isActive to true
		isActive = Boolean.TRUE;
		super.onCreate(bundle);
	}

	@Override
	protected void onDestroy ()
	{
		//when destroying activity set isActiveTo false because when come some listener form service
		isActive = Boolean.FALSE;
		super.onDestroy();
	}

	@Override
	protected void onStart ()
	{
		//when start activity set active to true because activity go to foreground
		isActive = Boolean.TRUE;
		super.onStart();
	}

	@Override
	protected void onResume ()
	{
		//when resume activity set active to true because activity go to foreground
		isActive = Boolean.TRUE;
		super.onResume();
	}

	@Override
	protected void onStop ()
	{
		//when stop activity set active to false because activity go to background or is stopped for low memory
		isActive = Boolean.FALSE;
		super.onStop();
	}

	@Override
	protected void onPause ()
	{
		//when pause activity set active to false because activity go to background or is stopped for low memory
		isActive = Boolean.FALSE;
		super.onPause();
	}

	/**
	 * indicies if activity is active(is in foreground or in background)
	 * @return boolean represent which is activity active or not if is active then return true if no active return false
	 */
	public Boolean isActive ()
	{
		return isActive;
	}
}
