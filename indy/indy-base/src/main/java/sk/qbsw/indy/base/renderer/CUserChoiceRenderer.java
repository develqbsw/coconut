package sk.qbsw.indy.base.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import sk.qbsw.security.core.model.domain.Account;

/**
 * Choice renderer for user DropDownChoice
 * @author Tomas Leken
 *
 */
public class CUserChoiceRenderer extends ChoiceRenderer<Account>
{

	private static final long serialVersionUID = 1L;

	public CUserChoiceRenderer ()
	{
		super();
	}

	/**
	 * @param key key of label from wicket properties file
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(Object)
	 */
	@Override
	public Object getDisplayValue (Account key)
	{
		String displayValue;
		if (key.getName() != null && key.getSurname() != null)
		{
			displayValue = key.getName() + " " + key.getSurname();
		}
		else
		{
			displayValue = key.getLogin();
		}
		return displayValue;
	}

	@Override
	public String getIdValue (Account object, int index)
	{
		return object.getId().toString();
	}

}
