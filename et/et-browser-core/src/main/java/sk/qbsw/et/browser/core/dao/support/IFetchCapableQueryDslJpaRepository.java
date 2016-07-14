package sk.qbsw.et.browser.core.dao.support;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;

/**
 * The join fetch capable querydsl repository.
 *
 * @param <T> the entity type
 * @param <ID> the id type
 *
 * @author Adrian Lopez (http://stackoverflow.com/a/21630123)
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
@NoRepositoryBean
public interface IFetchCapableQueryDslJpaRepository<T, ID extends Serializable>extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T>
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
}
