package sk.qbsw.indy.base.components;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

import sk.qbsw.indy.base.utils.CStringResourceReader;

/**
 * Choice renderer for boolean status
 * @author rosenberg
 * @since 1.0
 * @version 1.0
 */
public class CCBooleanChoiceRenderer implements IChoiceRenderer<Boolean>
{
	private static final long serialVersionUID = 1L;

	@Override
	public Object getDisplayValue (Boolean object)
	{
		if (object != null)
		{
			Boolean so = (Boolean) object;
			if (so)
			{
				return CStringResourceReader.read("boolean.yes");
			}
			else
			{
				return CStringResourceReader.read("boolean.no");
			}
		}
		else
		{
			return CStringResourceReader.read("boolean.no");
		}
	}

	@Override
	public String getIdValue (Boolean object, int index)
	{
		if (object != null)
		{
			Boolean selectOption = (Boolean) object;
			return selectOption.toString();
		}
		return (String) null;
	}
}
