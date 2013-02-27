package sk.qbsw.sgwt.winnetou.client.model.menu;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Menu item for the first level.
 * 
 * @author Dalibor Rak
 * @version 0.1
 */
public class CMenuTabItem implements IsSerializable
{
	private boolean enabled;
	private String hint;
	private String hintKey;
	private String iconPath;
	private String id;
	private String label;
	private String labelKey;

	/**
	 * Submenu items
	 */
	private ArrayList<CMenuTabSubItem> menuTabSubItems;

	/**
	 * String representation of main role
	 */
	private String role;

	public CMenuTabItem ()
	{
		this.enabled = true;
	}

	public String getHint ()
	{
		return hint;
	}

	public String getHintKey ()
	{
		return hintKey;
	}

	public String getIconPath ()
	{
		return iconPath;
	}

	public String getId ()
	{
		return this.id;
	}

	/**
	 * Returns the label.
	 * 
	 * @return The label.
	 */
	public String getLabel ()
	{
		return this.label;
	}

	public String getLabelKey ()
	{
		return labelKey;
	}

	public ArrayList<CMenuTabSubItem> getMenuTabSubItems ()
	{
		return menuTabSubItems;
	}

	public String getRole ()
	{
		return this.role;
	}

	public boolean isEnabled ()
	{
		return enabled;
	}

	public void setEnabled (boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setHint (String hint)
	{
		this.hint = hint;
	}

	public void setHintKey (String hintKey)
	{
		this.hintKey = hintKey;
	}

	public void setIconPath (String iconPath)
	{
		this.iconPath = iconPath;
	}

	public void setId (String labelId)
	{
		this.id = labelId;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            The label to set.
	 */
	public void setLabel (String label)
	{
		this.label = label;
	}

	public void setLabelKey (String labelKey)
	{
		this.labelKey = labelKey;
	}

	public void setMenuTabSubItems (ArrayList<CMenuTabSubItem> menuTabSubItems)
	{
		this.menuTabSubItems = menuTabSubItems;
	}

	public void setRole (String role)
	{
		this.role = role;
	}
}
