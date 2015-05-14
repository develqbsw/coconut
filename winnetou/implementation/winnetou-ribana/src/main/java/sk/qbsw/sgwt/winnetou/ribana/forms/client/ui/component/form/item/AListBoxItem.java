package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.model.ICodelistValue;

import com.smartgwt.client.widgets.form.fields.SelectItem;

/**
 * Base class for listbox items used in SED
 * 
 * @author Dalibor Rak
 * @version 0.1
 * 
 */
public class AListBoxItem extends SelectItem
{
	/**
	 * Internally stored all records
	 */
	private HashMap<String, ICodelistValue> model;

	/**
	 * Returns selected value as CCodeListRecord
	 * 
	 * @return
	 */
	public ICodelistValue getSelectedCodeListRecord ()
	{
		String value = null;

		if (getValue() != null)
		{
			value = getValue().toString();
		}

		if (value != null && model.containsKey(value))
		{
			return model.get(value);
		}
		return null;
	}

	/**
	 * Returns ID of selected record
	 * 
	 * @return
	 */
	public Long getValueAsLong ()
	{
		Object strVal = (Object) getValue();

		if (strVal != null && strVal.toString().length()>0)
		{
			return Long.parseLong(strVal.toString());
		}
		return null;
	}

	/**
	 * Sets model to ListBox and selects previous value
	 * 
	 * @param newModel
	 */
	public void rememberAndSetModel (List<ICodelistValue> newModel, Long predefinedValue)
	{
		Object value = getValue();
		setModel(newModel);
		if (model.containsKey(value) && value != null)
		{
			setValue(value.toString());
		}
		else
		{
			if (predefinedValue != null)
			{
				setPredefinedValue(predefinedValue);
			}
			else
			{
				clearValue();
			}
		}
	}

	/**
	 * Sets icon according records type
	 * 
	 * @param newModel
	 */
	public void setIcons ()
	{
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		for (ICodelistValue cCodeListRecord : model.values())
		{
			Long id = cCodeListRecord.getId();
			String strId = "";
			if (id != null)
			{
				strId = id.toString();
				valueMap.put(strId, cCodeListRecord.getType());
			}
		}
		super.setValueIcons(valueMap);
	}

	/**
	 * Sets model to legacy form
	 * 
	 * @param newModel
	 */
	public void setModel (List<ICodelistValue> newModel)
	{
		HashMap<String, ICodelistValue> internalModel = new HashMap<String, ICodelistValue>();
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		for (ICodelistValue cCodeListRecord : newModel)
		{
			Long id = cCodeListRecord.getId();
			String strId = "";
			if (id != null)
			{
				strId = id.toString();
				valueMap.put(strId, cCodeListRecord.getCode());
				internalModel.put(strId, cCodeListRecord);
			}
		}

		this.model = internalModel;
		super.setValueMap(valueMap);
	}

	/**
	 * Sets model to the listbox and selects predefined value if value is not
	 * null and is in the listbox value list
	 * 
	 * @param newModel
	 * @param predefinedValue
	 */
	public void setModel (List<ICodelistValue> newModel, Long predefinedValue)
	{
		setModel(newModel);
		setPredefinedValue(predefinedValue);
	}

	/**
	 * Sets predefined value
	 * 
	 * @param predefinedValue
	 */
	private void setPredefinedValue (Long predefinedValue)
	{
		if (predefinedValue != null)
		{
			String value = predefinedValue.toString();
			if (model.containsKey(value))
			{
				setValue(value);
			}
		}
	}

	/**
	 * Doesn't select value when the value is not in the listbox model
	 */
	@Override
	public void setValue (String value)
	{
		// model nemusi byt vyplneny...
		if ( (model != null && model.containsKey(value)) || model == null)
		{
			super.setValue(value);
		}
	}
}
