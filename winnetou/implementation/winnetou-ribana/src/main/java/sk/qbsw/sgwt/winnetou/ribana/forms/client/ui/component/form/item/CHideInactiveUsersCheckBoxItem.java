package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CCheckBoxItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * FOrm checkbox used for filtering 
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CHideInactiveUsersCheckBoxItem extends CCheckBoxItem
{
	public CHideInactiveUsersCheckBoxItem()
	{
		super();
		setLabelAsTitle(false);
		setTitle(ILabels.Factory.getInstance().item_flag_hide_inactive());
		setShowTitle(false);

		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");

		setTextAlign(Alignment.LEFT);

		setValue(false);
	}
}
