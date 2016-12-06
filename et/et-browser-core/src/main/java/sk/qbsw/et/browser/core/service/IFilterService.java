package sk.qbsw.et.browser.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;

import sk.qbsw.core.persistence.model.CJoinDescriptor;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The filter service.
 *
 * @author Marian Oravec
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IFilterService<PK extends Serializable, T extends IEntity<PK>>
{
	/**
	 * Find all.
	 *
	 * @param distinct the distinct
	 * @param predicate the predicate
	 * @param sorting the sorting
	 * @return the list
	 */
	List<T> findAll (final boolean distinct, final Predicate predicate, final Sort sorting);

	/**
	 * Find all.
	 *
	 * @param distinct the distinct
	 * @param predicate the predicate
	 * @param sorting the sorting
	 * @param joinDescriptors the join descriptors
	 * @return the list
	 */
	List<T> findAll (final boolean distinct, final Predicate predicate, final Sort sorting, final CJoinDescriptor<?>... joinDescriptors);
}
