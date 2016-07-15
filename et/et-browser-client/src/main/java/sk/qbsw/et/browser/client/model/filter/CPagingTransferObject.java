package sk.qbsw.et.browser.client.model.filter;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * The paging.
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

	/** The page. */
	@NotNull
	private Integer page;

	/** The size. */
	@NotNull
	private Integer size;

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public Integer getPage ()
	{
		return page;
	}

	/**
	 * Sets the page.
	 *
	 * @param page the new page
	 */
	public void setPage (Integer page)
	{
		this.page = page;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public Integer getSize ()
	{
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize (Integer size)
	{
		this.size = size;
	}
}
