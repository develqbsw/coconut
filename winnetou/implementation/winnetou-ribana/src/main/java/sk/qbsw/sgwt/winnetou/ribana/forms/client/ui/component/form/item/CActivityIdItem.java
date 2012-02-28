package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Form item for activity id
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CActivityIdItem extends CTextItem
{
	public CActivityIdItem()
	{
		super();

		setTitle(ILabels.Factory.getInstance().item_activity_id());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(100);

		setLength(20);
		setTextAlign(Alignment.RIGHT);

		setDisabled(true);
	}
}