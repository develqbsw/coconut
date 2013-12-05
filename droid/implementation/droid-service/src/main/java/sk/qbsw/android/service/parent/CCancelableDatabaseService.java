package sk.qbsw.android.service.parent;

import sk.qbsw.android.dao.jpa.CDatabaseHelper;
import sk.qbsw.android.service.exception.CServiceCanceledException;
import sk.qbsw.android.service.task.response.CServiceCancelModel;
import android.content.Context;

/** 
 * Service which methods can be canceled 
 * 
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.3.0
 */public class CCancelableDatabaseService<T extends CDatabaseHelper> extends CDatabaseService<T>
{

	public CCancelableDatabaseService (Context context, Class<T> type)
	{
		super(context, type);
	}
	
	/**
	 * this method throws serviceCanceled exception when is service canceled
	 * @param cancelModel model which represent canceled
	 * @throws CServiceCanceledException  when is model canceled
	 */
	protected void checkCanceled (CServiceCancelModel cancelModel) throws CServiceCanceledException
	{
		if (cancelModel.getIsCanceled())
		{
			throw new CServiceCanceledException("service canceled");
		}

	}

}
