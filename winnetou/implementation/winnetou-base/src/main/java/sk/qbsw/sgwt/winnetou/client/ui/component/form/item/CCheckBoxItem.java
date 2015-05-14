package sk.qbsw.sgwt.winnetou.client.ui.component.form.item;

import com.smartgwt.client.widgets.form.fields.CheckboxItem;

/**
 * Base Form Item represented as Checkbox item
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CCheckBoxItem extends CheckboxItem
{
	public CCheckBoxItem ()
	{
		super();
		super.setStartRow(false);
		super.setEndRow(false);
	}

	public CCheckBoxItem (String name)
	{
		super(name);
	}
}
