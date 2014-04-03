package sk.qbsw.sgwt.winnetou.client.ui.datasource;

import java.io.Serializable;
import java.util.Date;

import sk.qbsw.sgwt.winnetou.client.exception.CClientExceptionPublisher;
import sk.qbsw.sgwt.winnetou.client.model.CFetchResult;
import sk.qbsw.sgwt.winnetou.client.model.table.CTableRow;
import sk.qbsw.sgwt.winnetou.client.model.table.IFilterCriteria;
import sk.qbsw.sgwt.winnetou.client.model.table.ITableColumnDefinition;
import sk.qbsw.sgwt.winnetou.client.service.table.ITableServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Example <code>GwtRpcDataSource</code> implementation.
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
public class CTableDataSource<T extends ITableServiceAsync> extends AGwtRpcDataSource
{
	private T service;
	private IBrowserFilter browserFilter;

	/**
	 * Sets interface to browser filter
	 */
	public void setBrowserFilter (IBrowserFilter browserFilter)
	{
		this.browserFilter = browserFilter;
	}

	public CTableDataSource (T service)
	{
		this.service = service;
	}

	/**
	 * Prepares datasource from current listgridfields. Supported types of
	 * ListGrid are: ListGridFieldType.DATE ListGridFieldType.TIME
	 * ListGridFieldType.BOOLEAN ListGridFieldType.INTEGER
	 * ListGridFieldType.IMAGE ListGridFieldType.TEXT
	 * 
	 * Name and title of the datasource field is passed from listgrid field
	 * 
	 * @param tableServiceAsync
	 */
	public void prepareFields (ITableColumnDefinition columnDefinition)
	{
		ListGridField[] columns = columnDefinition.getColumns();

		if (columns != null)
		{
			for (ListGridField listGridField : columns)
			{
				ListGridFieldType type = listGridField.getType();

				String name = listGridField.getName();
				String title = listGridField.getTitle();

				DataSourceField field = null;

				if (ListGridFieldType.DATE.equals(type))
				{
					field = new DataSourceDateField(name, title);
				}
				else if (ListGridFieldType.TIME.equals(type))
				{
					field = new DataSourceDateTimeField(name, title);
				}
				else if (ListGridFieldType.BOOLEAN.equals(type))
				{
					field = new DataSourceBooleanField(name, title);
				}
				else if (ListGridFieldType.INTEGER.equals(type))
				{
					field = new DataSourceIntegerField(name, title);
				}
				else if (ListGridFieldType.FLOAT.equals(type))
				{
					field = new DataSourceFloatField(name, title);
				}
				else if (ListGridFieldType.IMAGE.equals(type))
				{
					field = new DataSourceImageField(name, title);
				}
				else
				{
					field = new DataSourceTextField(listGridField.getName(), listGridField.getTitle());
				}
				addField(field);
			}
		}
		
		if (columnDefinition.getId() != null)
		{
			getField(columnDefinition.getId()).setPrimaryKey(true);
		}
	}

	@Override
	protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response)
	{
		GWT.log("Start: " + request.getStartRow() + " End: " + request.getEndRow(), null);

		if (service != null)
		{
			IFilterCriteria criteria = null;

			if (this.browserFilter != null)
			{
				criteria = browserFilter.getFilterCriteria();
			}

			this.service.fetch(request.getStartRow(), request.getEndRow(), criteria, new AsyncCallback<CFetchResult<CTableRow>>()
			{
				public void onFailure (Throwable caught)
				{
					response.setStatus(RPCResponse.STATUS_FAILURE);
					processResponse(requestId, response);
					CClientExceptionPublisher.publish(caught);
				}

				public void onSuccess (CFetchResult<CTableRow> result)
				{

					ListGridRecord[] list = new ListGridRecord[result.getFetchedList().size()];
					for (int i = 0; i < list.length; i++)
					{
						ListGridRecord record = new ListGridRecord();
						CTableRow row = result.getFetchedList().get(i);
						copyValues(row, record);
						list[i] = record;
					}
					response.setData(list);
					response.setStartRow(result.getStartRow());
					response.setEndRow(result.getEndRow());
					response.setTotalRows(result.getTotalRows());
					processResponse(requestId, response);
				}
			});
		}
		else
		{
			response.setData(new ListGridRecord[0]);
			response.setStartRow(0);
			response.setEndRow(0);
			response.setTotalRows(0);
			processResponse(requestId, response);
		}
	}

	@Override
	protected void executeAdd (final String requestId, final DSRequest request, final DSResponse response)
	{
		/*
		 * // Retrieve record which should be added. JavaScriptObject data =
		 * request.getData(); ListGridRecord rec = new ListGridRecord(data);
		 * TestRecord testRec = new TestRecord(); copyValues(rec, testRec);
		 * IDataTestServiceAsync service = GWT.create(IDataTestService.class);
		 * service.add(testRec, new AsyncCallback<TestRecord>() { public void
		 * onFailure (Throwable caught) {
		 * response.setStatus(RPCResponse.STATUS_FAILURE);
		 * processResponse(requestId, response); }
		 * 
		 * public void onSuccess (TestRecord result) { ListGridRecord[] list =
		 * new ListGridRecord[1]; ListGridRecord newRec = new ListGridRecord();
		 * // copyValues(result, newRec); list[0] = newRec;
		 * response.setData(list); processResponse(requestId, response); } });
		 */
	}

	@Override
	protected void executeUpdate (final String requestId, final DSRequest request, final DSResponse response)
	{
		/*
		 * // Retrieve record which should be updated. JavaScriptObject data =
		 * request.getData(); ListGridRecord rec = new ListGridRecord(data); //
		 * Find grid ListGrid grid = (ListGrid)
		 * Canvas.getById(request.getComponentId()); // Get record with old and
		 * new values combined int index = grid.getRecordIndex(rec); rec =
		 * (ListGridRecord) grid.getEditedRecord(index); TestRecord testRec =
		 * new TestRecord(); copyValues(rec, testRec); IDataTestServiceAsync
		 * service = GWT.create(IDataTestService.class); service.update(testRec,
		 * new AsyncCallback<TestRecord>() { public void onFailure (Throwable
		 * caught) { response.setStatus(RPCResponse.STATUS_FAILURE);
		 * processResponse(requestId, response); }
		 * 
		 * public void onSuccess (TestRecord result) { ListGridRecord[] list =
		 * new ListGridRecord[1]; ListGridRecord updRec = new ListGridRecord();
		 * // copyValues(result, updRec); list[0] = updRec;
		 * response.setData(list); processResponse(requestId, response); } });
		 */
	}

	@Override
	protected void executeRemove (final String requestId, final DSRequest request, final DSResponse response)
	{
		/*
		 * // Retrieve record which should be removed. JavaScriptObject data =
		 * request.getData(); final ListGridRecord rec = new
		 * ListGridRecord(data); TestRecord testRec = new TestRecord();
		 * copyValues(rec, testRec); IDataTestServiceAsync service =
		 * GWT.create(IDataTestService.class); service.remove(testRec, new
		 * AsyncCallback() { public void onFailure (Throwable caught) {
		 * response.setStatus(RPCResponse.STATUS_FAILURE);
		 * processResponse(requestId, response); }
		 * 
		 * public void onSuccess (Object result) { ListGridRecord[] list = new
		 * ListGridRecord[1]; // We do not receive removed record from server.
		 * // Return record from request. list[0] = rec; response.setData(list);
		 * processResponse(requestId, response); }
		 * 
		 * });
		 */
	}

	private static void copyValues (CTableRow from, ListGridRecord to)
	{
		for (String columnName : from.keySet())
		{
			Serializable object = from.get(columnName);

			if (object != null)
			{
				String className = (object.getClass().getName());

				if (Date.class.getName().equals(className))
				{
					to.setAttribute(columnName, (Date) object);
				}
				else if (Boolean.class.getName().equals(className))
				{
					to.setAttribute(columnName, (Boolean) object);
				}
				else if (Integer.class.getName().equals(className))
				{
					to.setAttribute(columnName, (Integer) object);
				}
				else if (Float.class.getName().equals(className))
				{
					to.setAttribute(columnName, (Float) object);
				}
				else
				{
					if (object != null)
					{
						to.setAttribute(columnName, object.toString());
					}
					else
					{
						to.setAttribute(columnName, (String) null);
					}
				}
			}
			else
			{
				to.setAttribute(columnName, (String) null);
			}
		}
	}
}
