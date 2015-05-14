package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.AComboBoxItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.IMessages;

import com.smartgwt.client.widgets.form.validator.CustomValidator;

/**
 * Checks if combobox contains value from model
 * 
 * @author Dalibor Rak
 * @version 1.2
 * @since 0.1
 */
public class CCityValueValidator extends CustomValidator
{
	AComboBoxItem combo;

	public CCityValueValidator (AComboBoxItem item)
	{
		this.combo = item;
		this.setErrorMessage(IMessages.Factory.getInstance().city_wrong_value());
	}

	@Override
	protected boolean condition (Object value)
	{
		return combo.containsValueFromModel();
	}
}
