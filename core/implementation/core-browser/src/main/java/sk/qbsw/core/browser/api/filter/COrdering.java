package sk.qbsw.core.browser.api.filter;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The ordering.
 * 
 * @author podmajersky
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.15.0
 */
public class COrdering implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8521290234977635649L;

	/** The order column. */
	@NotNull
	@Size (min = 1, max = 255)
	private String orderColumn;

	/** The order direction. */
	@NotNull
	@Size (min = 1, max = 255)
	private String orderDirection;

	/**
	 * Gets the order column.
	 *
	 * @return the order column
	 */
	public String getOrderColumn ()
	{
		return orderColumn;
	}

	/**
	 * Sets the order column.
	 *
	 * @param orderColumn the new order column
	 */
	public void setOrderColumn (String orderColumn)
	{
		this.orderColumn = orderColumn;
	}

	/**
	 * Gets the order direction.
	 *
	 * @return the order direction
	 */
	public String getOrderDirection ()
	{
		return orderDirection;
	}

	/**
	 * Sets the order direction.
	 *
	 * @param orderDirection the new order direction
	 */
	public void setOrderDirection (String orderDirection)
	{
		this.orderDirection = orderDirection;
	}

}
