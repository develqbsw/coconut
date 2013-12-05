package sk.qbsw.android.ui.activity.broardcast;



/**
 * List of activity types
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.4.0
 */
public enum EBroadcastActionType
{
	PHOTO_UPLOADED ("sk.qbsw.mscan.ui.activity.broardcast.EBroadcastActionType.PHOTO_UPLOADED")
	{
	},
	SYNCHRONIZATION_START ("sk.qbsw.mscan.ui.activity.broardcast.EBroadcastActionType.SYNCHRONIZATION_START")
	{
	},
	SYNCHRONIZATION_END ("sk.qbsw.mscan.ui.activity.broardcast.EBroadcastActionType.SYNCHRONIZATION_END")
	{
	};

	/**
	 * Parses the value 
	 * @param value
	 * @return found ENUM value
	 */
	public static EBroadcastActionType parse (String value)
	{
		EBroadcastActionType status = null;
		for (EBroadcastActionType item : EBroadcastActionType.values())
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


	private EBroadcastActionType (String value)
	{
		this.value = value;
	}

	public String getValue ()
	{
		return value;
	}
}
