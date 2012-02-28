package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Item for login without check 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CLoginItem extends CTextItem
{
	public CLoginItem()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_login_name());
		setWrapTitle(false);
		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth(150);

		setLength(20);
		setTextAlign(Alignment.LEFT);
	}
}
 	