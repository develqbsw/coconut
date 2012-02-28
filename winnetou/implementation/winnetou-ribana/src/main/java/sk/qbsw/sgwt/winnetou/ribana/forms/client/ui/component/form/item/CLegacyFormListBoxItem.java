package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import java.util.LinkedHashMap;
import java.util.List;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.model.ICodelistValue;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;

/**
 * Listbox containing values of legacy form
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CLegacyFormListBoxItem extends AListBoxItem
{
	public CLegacyFormListBoxItem ()
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

	/**
	 * Sets model to legacy form
	 * 
	 * @param newModel
	 */
	@Override
	public void setModel (List<ICodelistValue> newModel)
	{
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		for (ICodelistValue cCodeListRecord : newModel)
		{
			Long id = cCodeListRecord.getId();
			String strId = "";
			if (id != null)
			{
				strId = id.toString();
			}
			valueMap.put(strId, cCodeListRecord.getDescription());
		}

		setValueMap(valueMap);
	}

}
