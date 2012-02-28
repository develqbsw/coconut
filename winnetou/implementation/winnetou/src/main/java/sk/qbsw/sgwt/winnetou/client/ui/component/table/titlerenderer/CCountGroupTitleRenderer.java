package sk.qbsw.sgwt.winnetou.client.ui.component.table.titlerenderer;

import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Renders title as number (null is translated to 0)
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CCountGroupTitleRenderer implements GroupTitleRenderer
{

	public String getGroupTitle (Object groupValue, GroupNode groupNode, ListGridField field, String fieldName, ListGrid grid)
	{
		if (groupValue == null || "".equals(groupValue))
		{
			return "0";
		}
		
		return groupValue.toString();
	}

}
