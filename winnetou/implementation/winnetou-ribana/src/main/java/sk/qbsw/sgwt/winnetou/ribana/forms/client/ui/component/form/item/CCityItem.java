package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * City form item
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CCityItem extends CTextItem
{
	public CCityItem()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_city());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");

		setLength(50);
		setTextAlign(Alignment.LEFT);
	}
}
 	