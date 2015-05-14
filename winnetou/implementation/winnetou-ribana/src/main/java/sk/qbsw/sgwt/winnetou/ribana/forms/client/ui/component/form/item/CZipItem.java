package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * ZIP code form item
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CZipItem extends CTextItem
{
	public CZipItem()
	{
		super();

		setTitle(ILabels.Factory.getInstance().item_zip());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(70);

		setLength(5);
		setTextAlign(Alignment.LEFT);
	}
}
