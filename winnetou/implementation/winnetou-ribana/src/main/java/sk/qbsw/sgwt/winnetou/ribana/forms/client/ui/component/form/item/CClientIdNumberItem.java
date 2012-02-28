package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.validation.CNumbersValidator;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Client identification number form item
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CClientIdNumberItem extends CTextItem
{
	public CClientIdNumberItem()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_client_idno());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(100);
		
		setLength(8);
		setTextAlign(Alignment.LEFT);
		setValidators(CNumbersValidator.getInstance());
	}
}
 	