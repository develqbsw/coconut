package sk.qbsw.indy.security.model.enums;

/**
 * Status of user
 * @author rosenberg
 * @version 1.0.0
 * @since 1.0.0
 */

public enum EFlagEnabled
{
	ENABLED (Boolean.TRUE, "filter.enabled")
	{
	},
	DISABLED (Boolean.FALSE, "filter.disabled")
	{
	};
	
	
	/**
	 * Internal value representation
	 */
	private Boolean value;

	/**
	 * UI label
	 */
	private String label;
	
	private EFlagEnabled (Boolean value, String label)
	{
		this.value = value;
		this.label = label;
	}

	public String getLabel ()
	{
		return label;
	}

	public Boolean getValue ()
	{
		return value;
	}

	/**
	 * Parses the value 
	 * @param value
	 * @return found ENUM value
	 */
	public static EFlagEnabled parse (Boolean value)
	{
		EFlagEnabled status = null;
		for (EFlagEnabled item : EFlagEnabled.values())
		{
			if (item.getValue().equals(value))
			{
				status = item;
				break;
			}
		}
		return status;
	}
}
