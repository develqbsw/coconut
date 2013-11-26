package sk.qbsw.android.service.parent;

import android.content.Context;

/**
 * This class is parent for all services
 * @author Michal Lacko
 *
 */
public class CService
{
	private Context context;
	
	public CService (Context context)
	{
		super();
		this.context = context;
	}

	
	public Context getContext ()
	{
		return context;
	}

	public void setContext (Context context)
	{
		this.context = context;
	}
	
}
