package sk.qbsw.et.browser.core.dao.support;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;

import sk.qbsw.et.browser.core.model.CJoinDescriptor;

/**
 * The join fetch capable querydsl repository.
 *
 * @author Adrian Lopez (http://stackoverflow.com/a/21630123)
 * @author Tomas Lauro
 * @version 1.16.0
 * @param <T> the entity type
 * @param <PK> the id type
 * @since 1.16.0
 */
@NoRepositoryBean
public interface IFetchCapableQueryDslJpaRepository<T, PK extends Serializable>extends JpaRepository<T, PK>, QueryDslPredicateExecutor<T>
{
	/**
	 * Find all.
	 *
	 * @param predicate the predicate
	 * @param pageable the pageable
	 * @param joinDescriptors the join descriptors
	 * @return the page
	 */
	Page<T> findAll (Predicate predicate, Pageable pageable, CJoinDescriptor<?>... joinDescriptors);

	/**
	 * Find all.
	 *
	 * @param predicate the predicate
	 * @param sort the sort
	 * @param joinDescriptors the join descriptors
	 * @return the page
	 */
	List<T> findAll (Predicate predicate, Sort sort, CJoinDescriptor<?>... joinDescriptors);

	/**
	 * Find all.
	 *
	 * @param distinct the distinct
	 * @param predicate the predicate
	 * @param pageable the pageable
	 * @param joinDescriptors the join descriptors
	 * @return the page
	 */
	Page<T> findAll (boolean distinct, Predicate predicate, Pageable pageable, CJoinDescriptor<?>... joinDescriptors);

	/**
	 * Find all.
	 *
	 * @param distinct the distinct
	 * @param predicate the predicate
	 * @param sort the sort
	 * @param joinDescriptors the join descriptors
	 * @return the list
	 */
	List<T> findAll (boolean distinct, Predicate predicate, Sort sort, CJoinDescriptor<?>... joinDescriptors);
	
	/**
	 * Count.
	 *
	 * @param predicate the predicate
	 * @param joinDescriptors the join descriptors
	 * @return the long
	 */
	long count (Predicate predicate, CJoinDescriptor<?>... joinDescriptors);
	
	/**
	 * Count.
	 *
	 * @param distinct the distinct
	 * @param predicate the predicate
	 * @param joinDescriptors the join descriptors
	 * @return the long
	 */
	long count (boolean distinct, Predicate predicate, CJoinDescriptor<?>... joinDescriptors);
}
