package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CNumericItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator.COrderValidator;
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
public class COrderIdItem extends CNumericItem
{
	public COrderIdItem()
	{
		super();

		setTitle(ILabels.Factory.getInstance().item_order());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(100);

		setTextAlign(Alignment.RIGHT);
		
		setValidators(new COrderValidator());
	}
}