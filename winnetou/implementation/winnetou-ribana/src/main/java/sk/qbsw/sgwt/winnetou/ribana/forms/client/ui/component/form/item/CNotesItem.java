package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;

/**
 * Notest for entering data
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CNotesItem extends TextAreaItem
{
	public CNotesItem()
	{
		setTitle(ILabels.Factory.getInstance().item_note());
		setShowTitle(true);
		
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");
		
		setLength(1000);
		setTextAlign(Alignment.LEFT);
	}
}
