package sk.qbsw.sgwt.winnetou.client.ui.component.form.item;

import com.smartgwt.client.widgets.form.fields.IntegerItem;

/**
 * Item for numeric representation
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CNumericItem extends IntegerItem
{
	
	/**
	 * Gets value as integer
	 * 
	 * @return
	 */
	public Integer getValueAsInteger()
	{
		Object value = getValue();

		if (value != null)
		{
			return Integer.parseInt(value.toString());
		}
		return null;
	}

}
