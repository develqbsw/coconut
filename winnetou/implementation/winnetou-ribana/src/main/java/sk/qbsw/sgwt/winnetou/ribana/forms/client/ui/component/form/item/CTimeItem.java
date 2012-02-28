package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

/**
 * Time item for forms
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CTimeItem extends sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTimeItem
{
	public CTimeItem()
	{
		super();
		setTitle(ILabels.Factory.getInstance().item_time());
		setWidth("*");
		setShowHint(false);
		setRequired(false);
		setWidth(50);
	}
}
