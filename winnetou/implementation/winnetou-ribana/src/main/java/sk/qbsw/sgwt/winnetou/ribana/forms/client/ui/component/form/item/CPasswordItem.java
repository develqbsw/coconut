package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.PasswordItem;

/**
 * Password item for password to access the system
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CPasswordItem extends PasswordItem
{
	public CPasswordItem()
	{
		super();
		
		setTitle(ILabels.Factory.getInstance().item_password());
		setWrapTitle(false);
		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth(150);
		
		setLength(100);
		setTextAlign(Alignment.LEFT);
	}
}
