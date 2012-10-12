package sk.qbsw.indy.base.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;

public class CPasswordStrongValidator extends AbstractValidator<String>
{
	private static final long serialVersionUID = 1L;

	// minimum password length for password validation
	private int MIN_PASS_LENGTH = 6;
	
	@Override
	protected void onValidate (IValidatable<String> validatable)
	{
		final String password = validatable.getValue();

		int digit = 0;
		int upper = 0;

		if (password.length() < MIN_PASS_LENGTH)
		{
			error(validatable, "short_password");
		}
		else
		{
			for (int i = 0; i < password.length(); i++)
			{
				if (Character.isDigit(password.charAt(i)))
				{
					digit++;
				}
				if (Character.isUpperCase(password.charAt(i)))
				{
					upper++;
				}				
			}

			if (digit == 0 || upper == 0)
			{
				error(validatable, "not_strong_password");
			}
		}
	}

}
