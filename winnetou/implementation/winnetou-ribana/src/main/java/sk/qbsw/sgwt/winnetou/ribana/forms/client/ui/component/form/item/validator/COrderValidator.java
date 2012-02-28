package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.IMessages;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * Validates order (value can be null or number 0-999)
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class COrderValidator extends CustomValidator
{
	public COrderValidator()
	{
		setErrorMessage(IMessages.Factory.getInstance().activity_wrong_order());
	}

	@Override
	protected boolean condition(Object value)
	{
		if (value != null)
		{
			String valueStr = value.toString();
			// musi byt cislica od 0-999
			if (!valueStr.matches("^(\\d{0,3})$"))
			{
				return false;
			}
		}
		return true;
	}
}
