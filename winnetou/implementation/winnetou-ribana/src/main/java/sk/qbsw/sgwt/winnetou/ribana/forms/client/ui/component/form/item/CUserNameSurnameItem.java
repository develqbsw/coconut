package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Name with surname of the user item
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CUserNameSurnameItem extends CTextItem
{
	public CUserNameSurnameItem()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_name_surname());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");
		
		setLength(100);
		setTextAlign(Alignment.LEFT);
	}
}
 	