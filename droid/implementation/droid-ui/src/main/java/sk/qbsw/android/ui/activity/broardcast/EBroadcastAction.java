package sk.qbsw.android.ui.activity.broardcast;



/**
 * List of activity types
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 */
public enum EBroadcastAction
{
	OUTSIDE_RESPONSE ("sk.qbsw.mscan.ui.activity.broardcast.EBroadcastAction.OUTSIDE_RESPONSE")
	{
	};

	/**
	 * Parses the value 
	 * @param value
	 * @return found ENUM value
	 */
	public static EBroadcastAction parse (String value)
	{
		EBroadcastAction status = null;
		for (EBroadcastAction item : EBroadcastAction.values())
		{
			if (item.getValue().equals(value))
			{
				status = item;
				break;
			}
		}
		return status;
	}

	/**
	 * Internal value representation
	 */
	private String value;


	private EBroadcastAction (String value)
	{
		this.value = value;
	}

	public String getValue ()
	{
		return value;
	}
}
