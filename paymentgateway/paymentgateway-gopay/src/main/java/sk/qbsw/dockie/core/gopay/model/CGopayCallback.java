package sk.qbsw.dockie.core.gopay.model;

/**
 * The Class CGopayCallback.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopayCallback
{
	private String return_url;
	private String notification_url;

	public CGopayCallback ()
	{
	}

	public CGopayCallback (String string, String string2)
	{
		return_url = string;
		notification_url = string2;
	}

	public String getReturn_url ()
	{
		return return_url;
	}

	public void setReturn_url (String return_url)
	{
		this.return_url = return_url;
	}

	public String getNotification_url ()
	{
		return notification_url;
	}

	public void setNotification_url (String notification_url)
	{
		this.notification_url = notification_url;
	}

}
