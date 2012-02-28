package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import java.util.LinkedHashMap;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Listbox defining actions available for tree
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CDayPartListBoxItem extends AListBoxItem
{

	public CDayPartListBoxItem()
	{
		setTitle(ILabels.Factory.getInstance().item_day_part());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth(50);
		setHeight(20);
		setTextAlign(Alignment.LEFT);

		// values
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();

		for (int i = 1; i <= 24; i++)
		{
			valueMap.put("" + i, "" + i);
		}
		setValueMap(valueMap);
	}
}
