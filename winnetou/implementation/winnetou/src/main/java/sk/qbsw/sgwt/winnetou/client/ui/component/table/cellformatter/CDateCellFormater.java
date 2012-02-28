package sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Formats cell as Date.
 * @author Dalibor Rak
 * @version 0.1
 *
 */
public class CDateCellFormater implements CellFormatter
{
	public static String DDMMYYYYHHMM = "d.M.yyyy HH:mm";
	public static String DMYYYY = "d.M.yyyy";
	public static String HHMM = "HH:mm";
	public static String TEST = "M/d/yyyy";

	
	private String pattern = "";

	public CDateCellFormater (String pattern)
	{
		this.pattern = pattern;
	}

	public void setOutputPattern (String pattern)
	{
		this.pattern = pattern;
	}

	public String format (Object value, ListGridRecord record, int rowNum, int colNum)
	{
		if (this.pattern == null)
		{
			this.pattern = DDMMYYYYHHMM;
		}
		if (value == null)
		{
			return null;
		}

		try
		{
			Date date = (Date)value;
			String retVal = DateTimeFormat.getFormat(this.pattern).format(date);
			return retVal;
		}
		catch (IllegalArgumentException ex)
		{
			return "unknown format";
		}
	}
}
