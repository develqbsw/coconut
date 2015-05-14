package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator.CPasswordStructureValidator;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

/**
 * Password item for entering new password for user. Checked = the length and
 * other checks are done during validation
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CPasswordNewItemChecked extends PasswordItem
{
	public CPasswordNewItemChecked()
	{
		this(null);
	}

	public CPasswordNewItemChecked(FormItem loginItem)
	{
		super();
		setTitle(ILabels.Factory.getInstance().item_password_new());

		setRequired(true);
		
		LengthRangeValidator validatorLength = new LengthRangeValidator();
		validatorLength.setMin(8);
		validatorLength.setMax(20);

		CPasswordStructureValidator structureVal = new CPasswordStructureValidator();
		structureVal.setLoginItem(loginItem);

		setValidators(validatorLength, structureVal);
	}
}
