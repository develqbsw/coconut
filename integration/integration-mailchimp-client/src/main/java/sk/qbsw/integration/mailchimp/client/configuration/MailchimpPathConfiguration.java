package sk.qbsw.integration.mailchimp.client.configuration;

import java.io.Serializable;

/**
 * The mailchimp path configuration.
 *
 * @author Tomas Lauro
 * 
 * @version 1.17.0
 * @since 1.17.0
 */
public class MailchimpPathConfiguration implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3362399311168490573L;

	/* common */
	/** The Constant ID. */
	public static final String ID = "id";

	/* lists */
	/** The Constant LISTS. */
	public static final String LISTS = "/lists";

	/** The Constant LIST. */
	public static final String LIST = LISTS + "/{" + ID + "}";

	/** The Constant LIST_MEMBERS. */
	public static final String LIST_MEMBERS = LIST + "/members";

	/**
	 * Instantiates a new mailchimp path configuration.
	 */
	private MailchimpPathConfiguration ()
	{
	}
}
