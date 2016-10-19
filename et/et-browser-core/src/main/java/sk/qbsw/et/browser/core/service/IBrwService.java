package sk.qbsw.et.browser.core.service;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.core.dto.CBrwDto;
import sk.qbsw.et.browser.core.model.CJoinDescriptor;

/**
 * The browser service.
 *
 * @param <PK> the id type
 * @param <T> the entity type
 *
 * @author Marian Oravec
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IBrwService<PK extends Serializable, T extends IEntity<PK>>extends IFilterService<PK, T>
{
	/**
	 * Read.
	 *
	 * @param id the id
	 * @return the t
	 */
	T read (final PK id);

	/**
	 * Find all.
	 *
	 * @param predicate the predicate
	 * @param pageable the pageable
	 * @param joinDescriptors the join descriptors
	 * @return the i brw dto
	 */
	CBrwDto<PK, T> findAll (final Predicate predicate, final Pageable pageable, final CJoinDescriptor<?>... joinDescriptors);

	/**
	 * Count.
	 *
	 * @param predicate the predicate
	 * @return the long
	 */
	long count (final Predicate predicate);
}
