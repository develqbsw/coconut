package sk.qbsw.sgwt.winnetou.client.ui.component.form.item;

import com.smartgwt.client.types.TimeFormatter;
import com.smartgwt.client.widgets.form.fields.TimeItem;

/**
 * Time item for form
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CTimeItem extends TimeItem
{
	public CTimeItem()
	{
		super();
		setDisplayFormat(TimeFormatter.TOSHORT24HOURTIME);
	}
	
	public CTimeItem(String name)
	{
		super(name);
		setDisplayFormat(TimeFormatter.TOSHORT24HOURTIME);
	}
}
