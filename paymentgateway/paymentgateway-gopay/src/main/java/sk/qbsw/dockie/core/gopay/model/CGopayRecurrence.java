package sk.qbsw.dockie.core.gopay.model;

/**
 * The Class CGopayRecurrence.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopayRecurrence
{
	private String recurrence_cycle;
	private Long recurrence_period;
	private String recurrence_date_to;
	private String recurrence_state;

	public String getRecurrence_cycle ()
	{
		return recurrence_cycle;
	}

	public void setRecurrence_cycle (String recurrence_cycle)
	{
		this.recurrence_cycle = recurrence_cycle;
	}

	public Long getRecurrence_period ()
	{
		return recurrence_period;
	}

	public void setRecurrence_period (Long recurrence_period)
	{
		this.recurrence_period = recurrence_period;
	}

	public String getRecurrence_date_to ()
	{
		return recurrence_date_to;
	}

	public void setRecurrence_date_to (String recurrence_date_to)
	{
		this.recurrence_date_to = recurrence_date_to;
	}

	public String getRecurrence_state ()
	{
		return recurrence_state;
	}

	public void setRecurrence_state (String recurrence_state)
	{
		this.recurrence_state = recurrence_state;
	}

}
