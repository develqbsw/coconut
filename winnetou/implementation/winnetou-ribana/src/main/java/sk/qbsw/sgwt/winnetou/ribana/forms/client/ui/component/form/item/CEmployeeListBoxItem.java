package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Listbox defining employees for filtering in overview screen
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CEmployeeListBoxItem extends AListBoxItem
{

	public CEmployeeListBoxItem()
	{
		setTitle(ILabels.Factory.getInstance().item_employees());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");
		setHeight(20);
		setTextAlign(Alignment.LEFT);
	}
}
