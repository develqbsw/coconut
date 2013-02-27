package sk.qbsw.sgwt.winnetou.client.ui.component.form.item.validation;

import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * Validation of item with numbers only.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CNumbersValidator extends RegExpValidator
{
	private CNumbersValidator()
	{
		setExpression("^([0-9]{0,})$");
		
		ISystemMessages messages =ISystemMessages.Factory.getInstance();
		setErrorMessage(messages.validation_contains_not_numbers());
	}
	
	public static CNumbersValidator getInstance()
	{
		return new CNumbersValidator();
	}
}
