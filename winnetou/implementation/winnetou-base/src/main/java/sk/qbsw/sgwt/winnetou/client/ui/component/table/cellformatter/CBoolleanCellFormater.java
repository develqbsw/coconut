package sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter;

import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Formats cell as count (null is transformed to 0)
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CBoolleanCellFormater implements CellFormatter
{
	public String format (Object value, ListGridRecord record, int rowNum, int colNum)
	{		
		if (value == null || "".equals(value))
		{
			return "false";
		}
		
		return value.toString();
	}
}

