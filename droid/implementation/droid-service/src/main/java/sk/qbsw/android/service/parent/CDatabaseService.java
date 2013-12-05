package sk.qbsw.android.service.parent;

import sk.qbsw.android.dao.jpa.CDatabaseHelper;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * This class is parent for Services which use a database
 * @param databaseHelperType
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 */
public class CDatabaseService<T extends CDatabaseHelper> extends CService implements IDatabaseService
{
	private T databaseHelper;
	
	
	public CDatabaseService (Context context, Class<T> helperClass)
	{
		super(context);
		databaseHelper = createHelper(helperClass);
	}

	@Override
	public void removeHelper ()
	{
		if (databaseHelper != null)
		{
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	private T createHelper (Class<T> helperClass)
	{
		if (databaseHelper == null)
		{
			databaseHelper = OpenHelperManager.getHelper(getContext(), helperClass);
		}
		return databaseHelper;
	}

	public T getDatabaseHelper ()
	{
		return databaseHelper;
	}
}
