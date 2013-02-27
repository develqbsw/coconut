package sk.qbsw.sgwt.winnetou.client.model.menu;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Holder for all labels in menu
 * 
 * @author Dalibor Rak
 * @version 1.2
 * @since 1.2
 *
 */
@SuppressWarnings ("serial")
public class CMenuLabelsHolder implements Serializable
{
	/**
	 * Values memory
	 */
	private HashMap<String, String> values = new HashMap<String, String>();

	private static CMenuLabelsHolder instance;

	public static CMenuLabelsHolder getInstance ()
	{
		if (instance == null)
		{
			instance = new CMenuLabelsHolder();
		}

		return instance;
	}

	private CMenuLabelsHolder ()
	{

	}

	public void addLabel (String key, String value)
	{
		values.put(key, value);
	}

	/**
	 * Gets label or empty string if no label is inserted
	 * @param label
	 * @return
	 */
	public String getLabel (String label)
	{
		String retVal = values.get(label);

		if (retVal == null)
		{
			return "";
		}
		else
		{
			return retVal;
		}
	}
}
