package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Type of the user 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CUserType extends CTextItem
{
	public CUserType()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_user_type());
		setWrapTitle(false);
		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth(150);
		
		setLength(100);
		setTextAlign(Alignment.LEFT);
	}
}
 	