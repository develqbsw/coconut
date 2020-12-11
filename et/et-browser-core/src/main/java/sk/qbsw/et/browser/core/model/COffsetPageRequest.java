package sk.qbsw.et.browser.core.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * The offset and limit based pageable - there is always just one page with whole result set.
 * 
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.16.0
 */
public class COffsetPageRequest implements Pageable
{
	private final long offset;

	private final int limit;

	private final Sort sort;

	/**
	 * Instantiates a new c offset page request.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 */
	public COffsetPageRequest (long offset, int limit)
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
	public COffsetPageRequest (long offset, int limit, Direction direction, String... properties)
	{
		this(offset, limit, Sort.by(direction, properties));
	}

	/**
	 * Instantiates a new c offset page request.
	 *
	 * @param offset the offset
	 * @param limit the limit
	 * @param sort the sort
	 */
	public COffsetPageRequest (long offset, int limit, Sort sort)
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

	@Override
	public int getPageNumber ()
	{
		return 0;
	}

	@Override
	public int getPageSize ()
	{
		return limit;
	}

	@Override
	public long getOffset ()
	{
		return offset;
	}

	@Override
	public Sort getSort ()
	{
		return sort;
	}

	@Override
	public Pageable next ()
	{
		return null;
	}

	@Override
	public Pageable previousOrFirst ()
	{
		return this;
	}

	@Override
	public Pageable first ()
	{
		return this;
	}

	@Override
	public boolean hasPrevious ()
	{
		return false;
	}
}
