package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Position of the user 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CUserPosition extends CTextItem
{
	public CUserPosition()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_user_position());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(150);
		
		setLength(50);
		setTextAlign(Alignment.LEFT);
	}
}
 	