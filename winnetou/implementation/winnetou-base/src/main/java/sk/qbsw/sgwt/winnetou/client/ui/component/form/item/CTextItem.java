package sk.qbsw.sgwt.winnetou.client.ui.component.form.item;

import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Text form item with added features
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CTextItem extends TextItem
{
	public CTextItem ()
	{
		super();
	}

	public CTextItem (String name)
	{
		super(name);
	}

	/**
	 * Sets readonly feature
	 * 
	 * @param readOnly
	 */
	public void setReadOnly (Boolean readOnly)
	{
		setAttribute("readOnly", readOnly);
	}

	/**
	 * Returns ID of selected record
	 * 
	 * @return
	 */
	public Long getValueAsLong ()
	{
		String strVal = (String) getValue();

		if (strVal != null)
		{
			return Long.parseLong(strVal);
		}
		return null;
	}

}
