package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CNumericItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CDaysCalendarItem extends CNumericItem
{
	public CDaysCalendarItem()
	{
		setTitle(ILabels.Factory.getInstance().item_day_calendar());
		setWrapTitle(false);
		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth(50);
		setHeight(20);
		setTextAlign(Alignment.LEFT);
	}
}
