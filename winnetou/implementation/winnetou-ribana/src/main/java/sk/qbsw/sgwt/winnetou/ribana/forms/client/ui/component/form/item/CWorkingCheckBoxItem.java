package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CCheckBoxItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Checkbox Item for flag working
 * 
 * @author Dalibor Rak
 * @Version 0.1
 * @since 0.1
 * 
 */
public class CWorkingCheckBoxItem extends CCheckBoxItem
{
	public CWorkingCheckBoxItem()
	{
		super();
		setTitle(ILabels.Factory.getInstance().item_flag_working());
		setLabelAsTitle(true);
		setShowTitle(true);
		setWrapTitle(false);

		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth("*");

		setTextAlign(Alignment.LEFT);

		setValue(false);
	}
}
