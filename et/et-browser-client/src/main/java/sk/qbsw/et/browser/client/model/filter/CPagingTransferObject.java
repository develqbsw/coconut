package sk.qbsw.et.browser.client.model.filter;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * The paging - empty object has range from 0 to Integet.MAX_VALUE.
 * 
 * @author podmajersky
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CPagingTransferObject implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4778551662700950445L;

	/** The offset. */
	@NotNull
	private Integer offset = 0;

	/** The limit. */
	@NotNull
	private Integer limit = Integer.MAX_VALUE;

	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	public Integer getOffset ()
	{
		return offset;
	}

	/**
	 * Sets the offset.
	 *
	 * @param offset the new offset
	 */
	public void setOffset (Integer offset)
	{
		this.offset = offset;
	}

	/**
	 * Gets the limit.
	 *
	 * @return the limit
	 */
	public Integer getLimit ()
	{
		return limit;
	}

	/**
	 * Sets the limit.
	 *
	 * @param limit the new limit
	 */
	public void setLimit (Integer limit)
	{
		this.limit = limit;
	}
}
