package sk.qbsw.sgwt.winnetou.client.ui.component.table.titlerenderer;

import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;

import com.smartgwt.client.widgets.grid.GroupNode;
import com.smartgwt.client.widgets.grid.GroupTitleRenderer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Renderer for boolean as text. Shows values in ISystemLabels.label_no and ISystemLabels.label_yes.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CYesNoGroupTitleRenderer implements GroupTitleRenderer
{

	public String getGroupTitle (Object groupValue, GroupNode groupNode, ListGridField field, String fieldName, ListGrid grid)
	{
		if (groupValue == null)
		{
			return "";
		}

		if (groupValue.toString().toLowerCase().equals("t") || groupValue.toString().toLowerCase().equals("true"))
		{
			return ISystemLabels.Factory.getInstance().label_yes();
		}

		if (groupValue.toString().toLowerCase().equals("f") || groupValue.toString().toLowerCase().equals("false"))
		{
			return ISystemLabels.Factory.getInstance().label_no();
		}

		return "unknown";
	}
}
