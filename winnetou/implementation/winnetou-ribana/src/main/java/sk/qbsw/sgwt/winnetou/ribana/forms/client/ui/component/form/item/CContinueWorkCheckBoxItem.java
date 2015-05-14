package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CCheckBoxItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Checkbox item for flag activated
 * 
 * @author Dalibor Rak
 * @Version 0.1
 * @since 0.1
 * 
 */
public class CContinueWorkCheckBoxItem extends CCheckBoxItem
{
	public CContinueWorkCheckBoxItem()
	{
		super();
		setTitle(ILabels.Factory.getInstance().label_continue_work());
		setLabelAsTitle(false);
		setShowTitle(false);
		setWrapTitle(false);

		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth("*");

		setTextAlign(Alignment.LEFT);

		setValue(true);
	}
}
