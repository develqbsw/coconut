package sk.qbsw.sgwt.winnetou.client.service.table;

import sk.qbsw.sgwt.winnetou.client.model.CFetchResult;
import sk.qbsw.sgwt.winnetou.client.model.table.IFilterCriteria;
import sk.qbsw.sgwt.winnetou.client.model.table.CTableRow;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous interface for ITableService ({@link ITableService})
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public interface ITableServiceAsync
{
	void fetch (int startRow, int endRow, IFilterCriteria criteria, AsyncCallback<CFetchResult<CTableRow>> callback);

	void add (CTableRow record, AsyncCallback<CTableRow> callback);

	void update (CTableRow record, AsyncCallback<CTableRow> callback);

	void remove (CTableRow record, AsyncCallback<Void> callback);
}
