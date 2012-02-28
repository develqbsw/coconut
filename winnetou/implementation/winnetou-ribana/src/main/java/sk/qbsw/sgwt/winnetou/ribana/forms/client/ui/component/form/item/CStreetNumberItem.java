package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;
/**
 * Form item pre ulicu.
 * 
 * Maximalny pocet zadatelnych znakov je 100.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CStreetNumberItem extends CTextItem
{
	public CStreetNumberItem()
	{
		super();

		setTitle(ILabels.Factory.getInstance().item_street());
		setShowTitle(false);
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(50);
		
		setColSpan(1);
		setLength(100);
		setTextAlign(Alignment.LEFT);
	}
}
