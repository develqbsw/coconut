package sk.qbsw.sgwt.winnetou.client.model.table;

import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Interface for object which identifies table columns
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface ITableColumnDefinition
{
	/**
	 * Gets list of table columns
	 * @return
	 */
	public ListGridField[] getColumns ();

	/**
	 * Gets primary key for the table
	 * @return
	 */
	public String getPkId ();
}
