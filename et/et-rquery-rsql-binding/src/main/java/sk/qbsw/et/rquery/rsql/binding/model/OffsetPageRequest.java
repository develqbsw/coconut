package sk.qbsw.et.rquery.rsql.binding.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * The offset and size based pageable - there is always just one page with whole result set.
 * 
 * @author Tomas Lauro
 * @version 2.3.1
 * @since 2.3.1
 */
public class OffsetPageRequest implements OffsetPageable
{
	private final long offset;

	private final int size;

	private final Sort sort;

	/**
	 * Creates a new OffsetPageRequest.
	 *
	 * @param offset the offset
	 * @param size the size
	 */
	public static OffsetPageRequest of (long offset, int size)
	{
		return new OffsetPageRequest(offset, size, Sort.unsorted());
	}

	/**
	 * Creates a new OffsetPageRequest.
	 *
	 * @param offset the offset
	 * @param size the size
	 * @param sort the sort
	 */
	public static OffsetPageRequest of (long offset, int size, Sort sort)
	{
		return new OffsetPageRequest(offset, size, sort);
	}

	/**
	 * Creates a new OffsetPageRequest.
	 *
	 * @param offset the offset
	 * @param size the size
	 * @param direction the direction
	 * @param properties the properties
	 */
	public static OffsetPageRequest of (long offset, int size, Direction direction, String... properties)
	{
		return new OffsetPageRequest(offset, size, new Sort(direction, properties));
	}

	/**
	 * Instantiates a new c offset page request.
	 *
	 * @param offset the offset
	 * @param size the size
	 * @param sort the sort
	 */
	private OffsetPageRequest (long offset, int size, Sort sort)
	{
		if (offset < 0)
		{
			throw new IllegalArgumentException("The offset must not be less than zero!");
		}

		if (size < 1)
		{
			throw new IllegalArgumentException("Size must not be less than 1!");
		}

		this.offset = offset;
		this.size = size;
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
		return size;
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
