package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.SelectItem;

/**
 * Listbox containing values of legacy form
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CLegacyFormItem extends SelectItem
{
	public CLegacyFormItem()
	{
		super();

		// LinkedHashMap<String, String> valueMap = new LinkedHashMap<String,
		// String>();
		// valueMap.put("SP", "Spain");
		// selectItem.setValueMap(valueMap);

		setTitle(ILabels.Factory.getInstance().item_legacy_form());
		setWrapTitle(false);
		setRequired(true);
		setAlign(Alignment.LEFT);
		setWidth("*");
		
		setTextAlign(Alignment.LEFT);
	}

}
