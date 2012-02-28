package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.validation.CEmailAddressValidator;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;
/**
 * TEXT �LENGTH=50, ALIGN=LEFT, SIZE=150 px
 * Mo�no zada� len mal� p�smen� (a..z) + �@� + �.�
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CEmailItem extends CTextItem
{
	public CEmailItem()
	{
		super();

		setTitle(ILabels.Factory.getInstance().item_email());
		setWrapTitle(false);
		setRequired(true);
		setAlign(Alignment.LEFT);
		setValidators(CEmailAddressValidator.getInstance());
		setWidth("*");
		
		setLength(50);
		setTextAlign(Alignment.LEFT);
	}
}
