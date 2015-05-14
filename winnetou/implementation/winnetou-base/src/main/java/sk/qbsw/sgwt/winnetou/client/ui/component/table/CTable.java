package sk.qbsw.sgwt.winnetou.client.ui.component.table;

import java.util.ArrayList;
import java.util.List;

import sk.qbsw.sgwt.winnetou.client.model.table.ITableColumnDefinition;
import sk.qbsw.sgwt.winnetou.client.ui.component.CMessageShowing;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;
import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceImageField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Table extends ListGrid and provides functionality for passing column model
 * and getting selected records.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CTable extends ListGrid
{
	/**
	 * Definition of columns in table
	 */
	private ITableColumnDefinition columnDefinition;

	/**
	 * Default constructor for table
	 * 
	 * @param columnDefinition
	 *            definition of columns used in this table
	 */
	public CTable (ITableColumnDefinition columnDefinition)
	{
		super();
		this.columnDefinition = columnDefinition;

		// table structure
		initLayout();
		initColumns();
	}

	/**
	 * Initializes layout of listgrid
	 */
	protected void initLayout ()
	{
		this.setWidth100();
		this.setHeight100();
		this.setShowFilterEditor(true);
	}

	/**
	 * initializes processing amd
	 */
	protected void initColumns ()
	{
		this.setFields(columnDefinition.getColumns());
		this.setAutoFetchData(true);
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
	public List<DataSourceField> getFieldsForDS ()
	{
		ArrayList<DataSourceField> columnsDS = new ArrayList<DataSourceField>();

		ListGridField[] columns = columnDefinition.getColumns();

		if (columns != null)
		{
			for (ListGridField listGridField : columns)
			{
				ListGridFieldType type = listGridField.getType();

				String name = listGridField.getName();
				String title = listGridField.getTitle();

				if (ListGridFieldType.DATE.equals(type))
				{
					columnsDS.add(new DataSourceDateField(name, title));
				}
				else if (ListGridFieldType.TIME.equals(type))
				{
					columnsDS.add(new DataSourceDateTimeField(name, title));
				}
				else if (ListGridFieldType.BOOLEAN.equals(type))
				{
					columnsDS.add(new DataSourceBooleanField(name, title));
				}
				else if (ListGridFieldType.INTEGER.equals(type))
				{
					columnsDS.add(new DataSourceIntegerField(name, title));
				}
				else if (ListGridFieldType.FLOAT.equals(type))
				{
					columnsDS.add(new DataSourceFloatField(name, title));
				}
				else if (ListGridFieldType.IMAGE.equals(type))
				{
					columnsDS.add(new DataSourceImageField(name, title));
				}
				else
				{
					columnsDS.add(new DataSourceTextField(listGridField.getName(), listGridField.getTitle()));
				}
			}
		}

		return columnsDS;
	}

	/**
	 * Setter for fields provides default listgrid alignment to center
	 */
	@Override
	public void setFields (ListGridField... fields)
	{
		if (fields != null)
		{
			for (ListGridField field : fields)
			{
				field.setAlign(Alignment.CENTER);
				if (field.getCellAlign() == null)
				{
					field.setCellAlign(Alignment.LEFT);
				}
			}

			super.setFields(fields);
		}
	}

	/**
	 * Method returns list of ID's in entered column (casted to Long!) selected
	 * from the table
	 * 
	 * @param columnName
	 *            name of the column
	 * @return List of object IDs
	 */
	public List<Long> getSelectedRecordsIds (String columnName)
	{
		List<Long> retVal = new ArrayList<Long>();

		ListGridRecord records[] = getSelection();

		if (records != null)
		{
			for (ListGridRecord record : records)
			{
				Long id = Long.parseLong(record.getAttribute(columnName));
				retVal.add(id);
			}
		}

		return retVal;
	}

	/**
	 * Method returns ID of selected column (casted to Long!) from the table
	 * 
	 * @param columnName
	 *            name of the column
	 * @return List of object IDs
	 */
	public Long getSelectedRecordId (String columnName)
	{
		ListGridRecord records[] = getSelection();

		if (records != null && records.length > 0)
		{
			return Long.parseLong(records[0].getAttribute(columnName));
		}

		return null;
	}

	/**
	 * Method returns value for selected records with specified column name
	 * 
	 * @param columnName
	 *            column to get value
	 * @return List of values
	 */
	public List<String> getSelectedRecordsAttribute (String columnName)
	{
		List<String> retVal = new ArrayList<String>();

		ListGridRecord records[] = getSelection();

		if (records != null)
		{
			for (ListGridRecord record : records)
			{
				retVal.add(record.getAttribute(columnName));
			}
		}

		return retVal;
	}

	/**
	 * Identifies if the had user selected at least one record
	 * 
	 * @return true if at least one record is selected, othrwise false
	 */
	public boolean checkAtLeastOneSelection ()
	{
		ListGridRecord[] record = getSelection();
		if ( (record == null || record.length < 1))
		{
			CMessageShowing.showWarning(ISystemLabels.Factory.getInstance().title_warning(), ISystemMessages.Factory.getInstance().table_select_something(), (BooleanCallback) null);
			return false;
		}
		return true;
	}
}
