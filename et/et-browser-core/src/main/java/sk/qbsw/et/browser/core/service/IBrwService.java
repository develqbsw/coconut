package sk.qbsw.et.browser.core.service;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.et.browser.core.dto.CBrwDto;

/**
 * The browser service.
 *
 * @param <PK> the id type
 * @param <T> the entity type
 *
 * @author Marian Oravec
 * @author Tomas Lauro
 * 
 * @version 1.16.1
 * @since 1.16.0
 */
public interface IBrwService<PK extends Serializable, T extends IEntity<PK>>extends IFilterService<PK, T>
{
	/**
	 * Find one.
	 *
	 * @param id the id
	 * @param joinDescriptors the join descriptors
	 * @return the t
	 */
	T findOne (final PK id, final CJoinDescriptor<?>... joinDescriptors);

	/**
	 * Find all.
	 *
	 * @param distinct the distinct
	 * @param predicate the predicate
	 * @param pageable the pageable
	 * @param joinDescriptors the join descriptors
	 * @return the i brw dto
	 */
	CBrwDto<PK, T> findAll (final boolean distinct, final Predicate predicate, final Pageable pageable, final CJoinDescriptor<?>... joinDescriptors);

	/**
	 * Count.
	 *
	 * @param distinct the distinct
	 * @param predicate the predicate
	 * @param joinDescriptors the join descriptors
	 * @return the long
	 */
	long count (final boolean distinct, final Predicate predicate, final CJoinDescriptor<?>... joinDescriptors);
}
