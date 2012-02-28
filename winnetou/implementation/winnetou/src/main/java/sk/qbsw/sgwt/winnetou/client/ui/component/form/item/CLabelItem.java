package sk.qbsw.sgwt.winnetou.client.ui.component.form.item;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.fields.CanvasItem;

/**
 * Label implemented as HTMLFlow
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CLabelItem extends CanvasItem
{
	public CLabelItem(String labelText)
	{
		this(labelText, "labelItem");
	}
	
	public CLabelItem(String labelText, String styleName)
	{
		super();
		setShowTitle(false);
		setHeight(20);
		setWidth("*");
		
		HTMLFlow flow = new HTMLFlow();
		flow.setStyleName(styleName);
		flow.setContents(labelText);
	
		setCanvas(flow);
	}

}
