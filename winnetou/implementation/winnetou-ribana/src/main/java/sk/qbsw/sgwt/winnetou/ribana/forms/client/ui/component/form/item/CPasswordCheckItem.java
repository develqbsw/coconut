package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.IMessages;

import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;

/**
 * Form item for validation of password which is entered by user during the registration
 * and changing of password.
 * 

 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CPasswordCheckItem extends CPasswordItem
{
	public CPasswordCheckItem(String otherField)
	{
		super();
		setTitle(ILabels.Factory.getInstance().item_password_check());

		MatchesFieldValidator match = new MatchesFieldValidator();
		match.setOtherField(otherField);
		match.setErrorMessage(IMessages.Factory.getInstance().registration_passwords_not_matching());

		setValidators(match);
	}
}
