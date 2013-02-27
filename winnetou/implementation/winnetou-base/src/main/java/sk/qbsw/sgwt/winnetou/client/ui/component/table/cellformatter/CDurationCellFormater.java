package sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Formats cell as duration (format HH:mm)
 * 
 * @author Dalibor Rak
 * @since 0.1
 * @version 0.1
 */
public class CDurationCellFormater implements CellFormatter
{
	public static String HHMM = "HH:mm";
	private String pattern;

	public CDurationCellFormater(String pattern)
	{
		this.pattern = pattern;
	}

	@SuppressWarnings(value = { "all" })
	public String format(Object value, ListGridRecord record, int rowNum, int colNum)
	{
		if (value == null)
		{
			return "";
		}

		Date date = (Date) value;
		String retVal = DateTimeFormat.getFormat(this.pattern).format(date);
		
		return retVal;
	}
}
