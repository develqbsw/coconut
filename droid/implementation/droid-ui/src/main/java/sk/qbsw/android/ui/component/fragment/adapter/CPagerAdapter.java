/**
 *
 */
package sk.qbsw.android.ui.component.fragment.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * The PagerAdapter serves the fragments when paging.
 * @author Michal Lacko, Tomas Lauro
 * @since 0.1.0
 * @version 0.4.51
 */
public class CPagerAdapter extends FragmentStatePagerAdapter
{
	private List<Fragment> fragments;

	public CPagerAdapter (FragmentManager fragmentManager, List<Fragment> fragments)
	{
		super(fragmentManager);
		this.fragments = fragments;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem (int position)
	{
		return fragments.get(position);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount ()
	{
		return fragments.size();
	}
}
