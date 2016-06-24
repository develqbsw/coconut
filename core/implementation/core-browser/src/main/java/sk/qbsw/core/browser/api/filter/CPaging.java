package sk.qbsw.core.browser.api.filter;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * The ordering.
 * 
 * @author podmajersky
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.15.0
 */
public class CPaging implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4778551662700950445L;

	/** The offset. */
	@NotNull
	private Long offset;

	/** The limit. */
	@NotNull
	@Max (100L)
	private Long limit;

	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	public Long getOffset ()
	{
		return offset;
	}

	/**
	 * Sets the offset.
	 *
	 * @param offset the new offset
	 */
	public void setOffset (Long offset)
	{
		this.offset = offset;
	}

	/**
	 * Gets the limit.
	 *
	 * @return the limit
	 */
	public Long getLimit ()
	{
		return limit;
	}

	/**
	 * Sets the limit.
	 *
	 * @param limit the new limit
	 */
	public void setLimit (Long limit)
	{
		this.limit = limit;
	}
}
