package sk.qbsw.core.communication.mail.model.domain;

/**
 * The of mail sending.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
public enum EMailState
{
	/** The mail hasn't been sent yet. */
	UNSENT ("UNSENT", "mailState.UNSENT"),

	/** The mail was sent. */
	SENT ("SENT", "mailState.SENT"),

	/** The error occurred during sending. The job will try to sent it again. */
	ERROR ("ERROR", "mailState.ERROR"),

	/** The fatal error occurred during sending. The job will not try to sent it again. */
	FATAL_ERROR ("FATAL_ERROR", "mailState.FATAL_ERROR");

	/** The key. */
	private String key;

	/** The value. */
	private String value;

	/**
	 * Instantiates a new e mail state.
	 *
	 * @param key the key
	 * @param value the value
	 */
	private EMailState (String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey ()
	{
		return key;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue ()
	{
		return value;
	}

	/**
	 * Parses the value.
	 *
	 * @param value the value
	 * @return found ENUM value
	 */
	public static EMailState parse (String value)
	{
		EMailState itemToReturn = null;
		if (value != null)
		{
			for (EMailState item : EMailState.values())
			{
				if (item.getValue().equals(value))
				{
					itemToReturn = item;
					break;
				}
			}
		}
		return itemToReturn;
	}

	/**
	 * Parses the key.
	 *
	 * @param key the key
	 * @return found ENUM value
	 */
	public static EMailState parseKey (String key)
	{
		EMailState itemToReturn = null;
		if (key != null)
		{
			for (EMailState item : EMailState.values())
			{
				if (item.getKey().equals(key))
				{
					itemToReturn = item;
					break;
				}
			}
		}
		return itemToReturn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString ()
	{
		return getKey();
	}

	/**
	 * From value.
	 *
	 * @param value the value
	 * @return the e mail state
	 */
	public static EMailState fromValue (String value)
	{
		return valueOf(value);
	}
}
