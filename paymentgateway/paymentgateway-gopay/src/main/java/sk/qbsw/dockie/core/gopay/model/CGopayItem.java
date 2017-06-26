package sk.qbsw.dockie.core.gopay.model;

/**
 * The Class CGopayItem.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopayItem
{
	private String name;
	private Long amount;

	public CGopayItem ()
	{
	}

	public CGopayItem (String name2, long longValue)
	{
		name = name2;
		amount = longValue;
	}

	public String getName ()
	{
		return name;
	}

	public void setName (String name)
	{
		this.name = name;
	}

	public Long getAmount ()
	{
		return amount;
	}

	public void setAmount (Long amount)
	{
		this.amount = amount;
	}

}
