package sk.qbsw.et.browser.core.dto;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.base.dto.IDto;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The brw dto interface.
 *
 * @param <PK> the primary key type
 * @param <T> the data type
 *
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IBrwDto<PK extends Serializable, T extends IEntity<PK>>extends IDto
{
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	List<T> getContent ();

	/**
	 * Gets the total pages.
	 *
	 * @return the total pages
	 */
	int getTotalPages ();

	/**
	 * Gets the total elements.
	 *
	 * @return the total elements
	 */
	long getTotalElements ();
}
