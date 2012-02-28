package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CCheckBoxItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Chackbox Item for flag main
 *
 * @author Dalibor Rak
 * @Version 0.1
 * @since 0.1
 *
 */
public class CMainCheckBoxItem extends CCheckBoxItem
{
	public CMainCheckBoxItem()
	{
		super();
		setTitle(ILabels.Factory.getInstance().item_flag_main());
		setLabelAsTitle(true);
		setShowTitle(true);
		setWrapTitle(false);
		
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");
		
		setTextAlign(Alignment.LEFT);
		
		setValue(false);
	}
}
