package sk.qbsw.et.browser.core.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * The offset and limit based pageable - there is always just one page with whole result set.
 * 
 * @author Tomas Lauro
 *
 * @version 1.16.0
 * @since 1.16.0
 */
public class COffsetPageRequest implements Pageable
{
	/** The offset. */
	private final int offset;

	/** The limit. */
	private final int limit;

	/** The sort. */
	private final Sort sort;

	/**
	 * Instantiates a new c offset page request.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 */
	public COffsetPageRequest (int offset, int limit)
	{
		this(offset, limit, null);
	}

	/**
	 * Instantiates a new c offset page request.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @param direction the direction
	 * @param properties the properties
	 */
	public COffsetPageRequest (int offset, int limit, Direction direction, String... properties)
	{
		this(offset, limit, new Sort(direction, properties));
	}

	/**
	 * Instantiates a new c offset page request.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @param sort the sort
	 */
	public COffsetPageRequest (int offset, int limit, Sort sort)
	{
		if (offset < 0)
		{
			throw new IllegalArgumentException("The offset must not be less than zero!");
		}

		if (limit < 1)
		{
			throw new IllegalArgumentException("Limit must not be less than 1!");
		}

		this.offset = offset;
		this.limit = limit;
		this.sort = sort;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getPageNumber()
	 */
	@Override
	public int getPageNumber ()
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getPageSize()
	 */
	@Override
	public int getPageSize ()
	{
		return limit;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getOffset()
	 */
	@Override
	public int getOffset ()
	{
		return offset;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getSort()
	 */
	@Override
	public Sort getSort ()
	{
		return sort;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#next()
	 */
	@Override
	public Pageable next ()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#previousOrFirst()
	 */
	@Override
	public Pageable previousOrFirst ()
	{
		return this;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#first()
	 */
	@Override
	public Pageable first ()
	{
		return this;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#hasPrevious()
	 */
	@Override
	public boolean hasPrevious ()
	{
		return false;
	}
}
