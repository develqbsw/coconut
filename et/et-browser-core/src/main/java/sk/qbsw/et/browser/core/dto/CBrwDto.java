package sk.qbsw.et.browser.core.dto;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The brw dto.
 *
 * @version 1.16.0
 * @param <PK> the primary key type
 * @param <T> the data type
 * @since 1.16.0
 */
public class CBrwDto<PK extends Serializable, T extends IEntity<PK>> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7640767817652819983L;

	/** The content. */
	private List<T> content;

	/** The total elements. */
	private long totalElements;

	/**
	 * Instantiates a new brw dto.
	 */
	public CBrwDto ()
	{
		//default constructor
	}

	/**
	 * Instantiates a new c brw dto.
	 *
	 * @param content the content
	 * @param totalElements the total elements
	 */
	public CBrwDto (List<T> content, long totalElements)
	{
		this.content = content;
		this.totalElements = totalElements;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public List<T> getContent ()
	{
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent (List<T> content)
	{
		this.content = content;
	}

	/**
	 * Gets the total elements.
	 *
	 * @return the total elements
	 */
	public long getTotalElements ()
	{
		return totalElements;
	}

	/**
	 * Sets the total elements.
	 *
	 * @param totalElements the new total elements
	 */
	public void setTotalElements (long totalElements)
	{
		this.totalElements = totalElements;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		return "CBrwDto [totalElements=" + this.totalElements + ", content=" + this.content + "]";
	}
}
