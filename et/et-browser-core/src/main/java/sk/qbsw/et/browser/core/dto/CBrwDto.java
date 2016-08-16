package sk.qbsw.et.browser.core.dto;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The brw dto.
 *
 * @param <PK> the primary key type
 * @param <T> the data type
 *
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwDto<PK extends Serializable, T extends IEntity<PK>> implements IBrwDto<PK, T>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7640767817652819983L;

	/** The content. */
	private final List<T> content;

	/** The total pages. */
	private final int totalPages;

	/** The total elements. */
	private final long totalElements;

	/**
	 * Instantiates a new c brw dto.
	 *
	 * @param content the content
	 * @param totalPages the total pages
	 * @param totalElements the total elements
	 */
	public CBrwDto (List<T> content, int totalPages, long totalElements)
	{
		this.content = content;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dto.IBrwDto#getContent()
	 */
	@Override
	public List<T> getContent ()
	{
		return content;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dto.IBrwDto#getTotalPages()
	 */
	@Override
	public int getTotalPages ()
	{
		return totalPages;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dto.IBrwDto#getTotalElements()
	 */
	@Override
	public long getTotalElements ()
	{
		return totalElements;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		return "CBrwDto [totalPages=" + this.totalPages + ", totalElemetns=" + this.totalElements + ", content=" + this.content + "]";
	}
}
