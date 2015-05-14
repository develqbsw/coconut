package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Listbox defining activity
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CCityListBoxItem extends AComboBoxItem
{
	public CCityListBoxItem()
	{
		setTitle(ILabels.Factory.getInstance().item_city());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");
		setHeight(20);
		setTextAlign(Alignment.LEFT);
	}
}
