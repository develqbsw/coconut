package sk.qbsw.sgwt.winnetou.client.ui.component.form.item.validation;

import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemMessages;

import com.smartgwt.client.widgets.form.validator.RegExpValidator;

/**
 * Validation of email address
 * 
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CEmailAddressValidator extends RegExpValidator
{
	private static CEmailAddressValidator instance;
	
	private CEmailAddressValidator()
	{
		setExpression(".+@.+\\.[a-z]+");
		
		ISystemMessages messages =ISystemMessages.Factory.getInstance();
		setErrorMessage(messages.validation_email_bad_format());
	}
	
	public static CEmailAddressValidator getInstance()
	{
		if (instance == null)
		{
			instance = new CEmailAddressValidator();
		}
		
		return instance;
	}
}
