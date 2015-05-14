package sk.qbsw.indy.base.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;

/**
 * Perform validation of phone number format
 * 
 * @author Tomas Leken
 * @version 1.0
 * @since 1.0
 */
public class CPhoneNumberValidator extends AbstractValidator<String>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;

	public CPhoneNumberValidator (String key)
	{
		this.key = key;
	}

	@Override
	protected void onValidate (IValidatable<String> validable)
	{
		String val = validable.getValue();

		Pattern pattern = Pattern.compile("\\+?(\\d+\\s?)+");

		Matcher matcher = pattern.matcher(val);


		if (!matcher.matches())
		{
			ValidationError error = new ValidationError();
			error.addMessageKey(key);
			validable.error(error);
		}
	}

}
