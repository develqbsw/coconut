package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.IMessages;

import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * Checks if the activated is set to true if main is set to true.
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CActivatedDependencyValidator extends CustomValidator
{
	private CheckboxItem dependency;

	public CActivatedDependencyValidator()
	{
		setErrorMessage(IMessages.Factory.getInstance().user_main_deactivated());
	}

	@Override
	protected boolean condition(Object value)
	{
		Boolean bval = (Boolean) value;

		if (dependency != null)
		{
			Boolean shouldBeTrue = (Boolean) dependency.getValue();
			if (shouldBeTrue && !bval)
				return false;
		}
		return true;
	}

	/**
	 * Sets dependent checkbox
	 * 
	 * @param dependency
	 */
	public void setDependencyCheckbox(CheckboxItem dependency)
	{
		this.dependency = dependency;
	}

}
