package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Item for phone (mobile)
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CPhoneMobileItem extends CTextItem
{
	public CPhoneMobileItem()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_phone_mobile());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");
		
		setLength(50);
		setTextAlign(Alignment.RIGHT);
	}
}
 	