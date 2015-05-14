package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Client VAT number form item
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CClientVatNumberItem extends CTextItem
{
	public CClientVatNumberItem()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_client_vatno());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(150);

		setLength(12);
		setTextAlign(Alignment.LEFT);
	}
}
 	