package sk.qbsw.et.browser.core.dto;

import java.util.List;

import sk.qbsw.core.base.dto.IDto;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The brw dto.
 *
 * @version 1.15.0
 * @since 1.15.0
 *
 * @param <PK> the primary key type
 * @param <T> the data type
 */
public class CBrwDataDto<PK, T extends IEntity<PK>> implements IDto
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7640767817652819983L;

	/** The data. */
	private final List<T> data;

	/** The total count. */
	private final Long totalCount;

	/**
	 * Instantiates a new c brw data dto.
	 *
	 * @param data the data
	 * @param totalCount the total count
	 */
	public CBrwDataDto (List<T> data, Long totalCount)
	{
		this.data = data;
		this.totalCount = totalCount;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<T> getData ()
	{
		return this.data;
	}

	/**
	 * Gets the total count.
	 *
	 * @return the total count
	 */
	public Long getTotalCount ()
	{
		return this.totalCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		return "CBrwDataDto [totalCount=" + this.totalCount + ", data=" + this.data + "]";
	}
}
