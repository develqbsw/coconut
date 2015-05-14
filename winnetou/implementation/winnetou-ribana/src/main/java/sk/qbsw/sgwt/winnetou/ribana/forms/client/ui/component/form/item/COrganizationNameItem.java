package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Organization name item used in forms
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class COrganizationNameItem extends CTextItem
{
	public COrganizationNameItem()
	{
		super();

		setTitle(ILabels.Factory.getInstance().item_organization_name());
		setWrapTitle(false);
		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth("*");
		
		setLength(100);
		setTextAlign(Alignment.LEFT);
	}
}