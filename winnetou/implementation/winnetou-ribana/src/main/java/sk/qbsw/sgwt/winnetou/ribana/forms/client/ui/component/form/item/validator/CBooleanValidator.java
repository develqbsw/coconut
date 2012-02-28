package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * Checks if
 */
public class CBooleanValidator extends CustomValidator
{
	/**
	 * Value to check content of boolean object
	 */
	private Boolean valueToCheck;

	public CBooleanValidator(Boolean valueToCheck, String messageToShow)
	{
		this.valueToCheck = valueToCheck;
		this.setErrorMessage(messageToShow);
	}

	@Override
	protected boolean condition(Object value)
	{
		if (value != null)
		{
			if (value instanceof Boolean)
			{
				return value.equals(valueToCheck);
			}
		}
		return true;
	}

}
