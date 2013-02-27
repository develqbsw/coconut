package sk.qbsw.sgwt.winnetou.client.ui.component.form.item;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.fields.CanvasItem;

/**
 * 
 * Firm item used for horizontal line separator
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CHLineSeparatorItem extends CanvasItem
{
	public CHLineSeparatorItem()
	{
		super();
		setShowTitle(false);
		setHeight(20);
		setWidth("*");
		
		setCanvas(new HTMLFlow("<HR/>"));
	}

}
