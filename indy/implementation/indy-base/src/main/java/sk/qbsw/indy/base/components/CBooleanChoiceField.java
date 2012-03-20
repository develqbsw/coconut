package sk.qbsw.indy.base.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

/**
 * Choice field for boolean value
 * @author rosenberg
 *
 * @since 1.0
 * @version 1.0
 */
public class CBooleanChoiceField  extends DropDownChoice<Boolean>
{

	private static final long serialVersionUID = 1L;

	public CBooleanChoiceField (String id)
	{
		super(id, getListOfValues(), new CCBooleanChoiceRenderer());
	}

	public CBooleanChoiceField (String id, IModel<Boolean> model)
	{
		super(id, model, getListOfValues(), new CCBooleanChoiceRenderer());
	}
	
	public static List<Boolean> getListOfValues ()
	{
		List<Boolean> retVal = new ArrayList<Boolean>();

		retVal.add(Boolean.TRUE);
		retVal.add(Boolean.FALSE);

		return retVal;
	}

}
