package sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Time formatter for listgrid fields. Uses DateTimeFormat.getShortTimeFormat()
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CTimeCellFormatter implements CellFormatter
{

	public String format(Object value, ListGridRecord record, int rowNum, int colNum)
	{
		Date date = (Date) value;
		if (date== null)
		{
			return "";
		}
		
		DateTimeFormat dtf = DateTimeFormat.getShortTimeFormat();
		return dtf.format(date);
	}
}
