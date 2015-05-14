package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Code of the user 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CEmployeeCode extends CTextItem
{
	public CEmployeeCode()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_employee_code());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(100);
		
		setLength(10);
		setTextAlign(Alignment.RIGHT);
	}
}
 	