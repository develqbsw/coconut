package sk.qbsw.dockie.core.gopay.model;

/**
 * The Class CGopayAdditionalParameter.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopayAdditionalParameter
{
	private String name;
	private String value;

	public String getValue ()
	{
		return value;
	}

	public void setValue (String value)
	{
		this.value = value;
	}

	public String getName ()
	{
		return name;
	}

	public void setName (String name)
	{
		this.name = name;
	}
}
