package sk.qbsw.indy.base.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import sk.qbsw.core.security.model.domain.CUser;

/**
 * Choice renderer for user DropDownChoice
 * @author Tomas Leken
 *
 */
public class CUserChoiceRenderer extends ChoiceRenderer<CUser>
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
	public Object getDisplayValue (CUser key)
	{
		return key.getName() + " " + key.getSurname();
	}
	
	@Override
	public String getIdValue (CUser object, int index)
	{
		return object.getPkId().toString();
	}

}
