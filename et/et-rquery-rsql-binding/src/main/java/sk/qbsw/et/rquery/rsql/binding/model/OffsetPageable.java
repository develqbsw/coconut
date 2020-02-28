package sk.qbsw.et.rquery.rsql.binding.model;

import org.springframework.data.domain.Pageable;

/**
 * The offset and size based pageable - there is always just one page with whole result set.
 *
 * @author Tomas Lauro
 * @version 2.3.1
 * @since 2.3.1
 */
public interface OffsetPageable extends Pageable
{
	/**
	 * Is offset boolean.
	 *
	 * @return the boolean
	 */
	default boolean isOffset ()
	{
		return true;
	}
}
