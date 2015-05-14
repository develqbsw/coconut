package sk.qbsw.sgwt.winnetou.client.ui.component.table.titlerenderer;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Formats title as duration (format HH:mm)
 * 
 * @author Dalibor Rak
 * @since 0.1
 * @version 0.1
 */
public class CDurationGroupTitleRenderer implements GroupTitleRenderer
{

	public static String HHMM = "HH:mm";
	private String pattern;
	
	public CDurationGroupTitleRenderer(String pattern)
	{
		this.pattern = pattern;
	}

	@SuppressWarnings(value={"all"}) 
	public String getGroupTitle (Object groupValue, GroupNode groupNode, ListGridField field, String fieldName, ListGrid grid)
	{
		if (groupValue == null)
		{
			return "";
		}
		
		Date date = new Date(0);
		date.setHours(Integer.parseInt((String)groupValue) / 60);
		date.setMinutes(Integer.parseInt((String)groupValue) % 60);
		
		return DateTimeFormat.getFormat(this.pattern).format(date);
	}
}
