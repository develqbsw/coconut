package sk.qbsw.android.ui.component.fragment.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Viewer which disable scroll event for switching
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public class CDisableScrollingViewPager extends ViewPager
{

	public CDisableScrollingViewPager (Context arg0, AttributeSet arg1)
	{
		super(arg0, arg1);
	}

	public CDisableScrollingViewPager (Context arg0)
	{
		super(arg0);
	}
	
	@Override
	public boolean onInterceptTouchEvent (MotionEvent arg0)
	{
		//disabling motion events
		return Boolean.FALSE;
	}
	
	@Override
	public boolean onTouchEvent (MotionEvent arg0)
	{
		//enabling events on standard screen
		return Boolean.TRUE;
	}

}
