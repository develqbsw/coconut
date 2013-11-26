package sk.qbsw.android.ui.component.tabhost;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

/**
 * A simple factory that returns dummy views to the Tabhost
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public class CEmptyTabFactory implements TabContentFactory
{

	private final Context context;
	
	public CEmptyTabFactory (Context context)
	{
		this.context = context;
	}

	/** (non-Javadoc)
	 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
	 */
	public View createTabContent (String tag)
	{
		View v = new View(context);
		v.setMinimumWidth(0);
		v.setMinimumHeight(0);
		return v;
	}

}