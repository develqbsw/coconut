package sk.qbsw.sgwt.winnetou.client.ui.component.form.item.validation;

import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * Validation of item with no white characters.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CNoWhiteCharsValidator extends RegExpValidator
{
	private CNoWhiteCharsValidator()
	{
		setExpression("[\\S ]*+");

		ISystemMessages messages = ISystemMessages.Factory.getInstance();
		setErrorMessage(messages.validation_contains_white_chars());
	}

	public static CNoWhiteCharsValidator getInstance()
	{
		return new CNoWhiteCharsValidator();
	}
}
