package sk.qbsw.sgwt.winnetou.client.ui.component.table.titlerenderer;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Class is group title renderer for date. See supported date formats.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CDateGroupTitleRenderer implements GroupTitleRenderer
{
	public static String DDMMYYYYHHMM = "dd.MM.yyyy HH:mm";
	public static String DDMMYYYY = "dd.MM.yyyy";
	public static String HHMM = "HH:mm";

	private String pattern = "";

	public CDateGroupTitleRenderer (String pattern)
	{
		this.pattern = pattern;
	}

	public void setOutputPattern (String pattern)
	{
		this.pattern = pattern;
	}

	public String getGroupTitle (Object groupValue, GroupNode groupNode, ListGridField field, String fieldName, ListGrid grid)
	{
		if (this.pattern == null)
		{
			this.pattern = DDMMYYYYHHMM;
		}
		if (groupValue == null)
		{
			return null;
		}

		try
		{
			Date date = (Date)groupValue;
			return DateTimeFormat.getFormat(this.pattern).format(date);
		}
		catch (IllegalArgumentException ex)
		{
			return "unknown format";
		}
	}

}
