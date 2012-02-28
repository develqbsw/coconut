package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CCheckBoxItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator.CBooleanValidator;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.IMessages;

import com.smartgwt.client.types.Alignment;

/**
 * Checkbox item for flag activated
 * 
 * @author Dalibor Rak
 * @Version 0.1
 * @since 0.1
 * 
 */
public class CAgreementCheckBoxItem extends CCheckBoxItem
{
	public CAgreementCheckBoxItem()
	{
		super();
		setTitle(ILabels.Factory.getInstance().labelAgreement());
		setLabelAsTitle(false);
		setShowTitle(false);
		setWrapTitle(false);

		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth("*");

		setTextAlign(Alignment.LEFT);

		setValue(false);
		
		setValidators(new CBooleanValidator(true, IMessages.Factory.getInstance().registration_accept_processing()));
	}
}
