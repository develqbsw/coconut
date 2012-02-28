package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CNumericItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Item for form representing count of wokring days
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CDaysWorkingItem extends CNumericItem
{
	public CDaysWorkingItem()
	{
		setTitle(ILabels.Factory.getInstance().item_day_working());
		setWrapTitle(false);
		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth(50);
		setHeight(20);
		setTextAlign(Alignment.LEFT);
	}
}
