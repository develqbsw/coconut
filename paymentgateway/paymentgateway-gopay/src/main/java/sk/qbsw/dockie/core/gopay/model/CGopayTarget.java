package sk.qbsw.dockie.core.gopay.model;

/**
 * The Class CGopayTarget.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopayTarget
{
	private String type;
	private Long goid;

	public CGopayTarget ()
	{
	}

	public CGopayTarget (long parseLong)
	{
		goid = parseLong;
		type = "ACCOUNT";
	}

	public String getType ()
	{
		return type;
	}

	public void setType (String type)
	{
		this.type = type;
	}

	public Long getGoid ()
	{
		return goid;
	}

	public void setGoid (Long goid)
	{
		this.goid = goid;
	}
}
