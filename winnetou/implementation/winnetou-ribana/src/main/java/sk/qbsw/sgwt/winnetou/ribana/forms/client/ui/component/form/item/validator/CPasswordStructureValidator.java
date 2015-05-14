package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.IMessages;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * Validates password against login field and it must contain at least one small
 * letter and at leas one capital
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CPasswordStructureValidator extends CustomValidator
{
	private FormItem loginItem;

	public CPasswordStructureValidator()
	{
		setErrorMessage(IMessages.Factory.getInstance().registration_bad_password_format());
	}

	/**
	 * Login doesn't have to be set
	 * 
	 * @param loginItem
	 *            item to check
	 */
	public void setLoginItem(FormItem loginItem)
	{
		this.loginItem = loginItem;
	}

	@Override
	protected boolean condition(Object value)
	{
		if (value != null)
		{
			// login item check
			if (loginItem != null)
			{
				String login = loginItem.getValue().toString();
				if (login.equals(value))
				{
					return false;
				}
			}

			String valueStr = value.toString();

			// musi mat aspon jedno male, jedno velke
			if (valueStr.matches("^(.{0,}[A-Z].{0,})$") && (valueStr.matches("^(.{0,}[a-z].{0,})$")))
			{
				return true;
			}
		}
		return true;
	}
}
