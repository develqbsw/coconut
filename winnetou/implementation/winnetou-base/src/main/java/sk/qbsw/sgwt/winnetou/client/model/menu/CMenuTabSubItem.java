package sk.qbsw.sgwt.winnetou.client.model.menu;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CMenuTabSubItem implements IsSerializable
{
	/**
	 * Identifies menu command
	 */
	private String command;

	/**
	 * IS valid
	 */
	private boolean enabled;

	/**
	 * Hint shown on the menu item
	 */
	private String hint;

	/**
	 * Key for property managed labels
	 */
	private String hintKey;

	/**
	 * Path to find icon
	 */
	private String iconPath;

	/**
	 * ID of menu item and screen
	 */
	private String id;

	/**
	 * identifies if item is separator
	 */
	private boolean isSeparator;

	/**
	 * label shown on the item
	 */
	private String label;

	/**
	 * Key for property managed labels
	 */
	private String labelKey;

	/**
	 * roles needed to access functionality
	 */
	private String[] roles;

	public CMenuTabSubItem ()
	{
		this.isSeparator = false;
		this.enabled = true;
		this.iconPath = null;
	}

	public String getCommand ()
	{
		return command;
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
		return id;
	}

	/**
	 * Returns the label.
	 * 
	 * @return The label.
	 */
	public String getLabel ()
	{
		return label;
	}

	public String getLabelKey ()
	{
		return labelKey;
	}

	public String[] getRoles ()
	{
		return roles;
	}

	public boolean isEnabled ()
	{
		return enabled;
	}

	public boolean isSeparator ()
	{
		return isSeparator;
	}

	public void setCommand (String action)
	{
		this.command = action;
	}

	public void setEnabled (boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setHint (String value)
	{
		this.hint = value;
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

	public void setRoles (String[] roles)
	{
		this.roles = roles;
	}

	public void setSeparator (boolean isSeparator)
	{
		this.isSeparator = isSeparator;
	}
}
