package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item.validator.CPasswordStructureValidator;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;

/**
 * Password item for password to access the system. Contains checks(min 8 and
 * max 20 chars). Checks structure of the password.
 * 
 * @see CPasswordStructureValidator
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CPasswordItemChecked extends CPasswordItem
{
	public CPasswordItemChecked()
	{
		this(null);
	}

	public CPasswordItemChecked(FormItem loginItem)
	{
		super();
		LengthRangeValidator validatorLength = new LengthRangeValidator();
		validatorLength.setMin(6);
		validatorLength.setMax(20);

		CPasswordStructureValidator structureVal = new CPasswordStructureValidator();
		structureVal.setLoginItem(loginItem);

		setValidators(validatorLength, structureVal);
	}
}
