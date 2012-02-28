package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CDateItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

/**
 * Date from item
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CDateToItem extends CDateItem
{
	public CDateToItem()
	{
		super();
		setTitle(ILabels.Factory.getInstance().item_date_to());
		setUseTextField(true);
		setWidth(100);
	}
}
