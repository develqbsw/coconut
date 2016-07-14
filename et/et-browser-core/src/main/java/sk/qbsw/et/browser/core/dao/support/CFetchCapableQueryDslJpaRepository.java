package sk.qbsw.et.browser.core.dao.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

/**
 * The join fetch capable querydsl repository implementation. 
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
public class CFetchCapableQueryDslJpaRepository<T, ID extends Serializable>extends QueryDslJpaRepository<T, ID> implements IFetchCapableQueryDslJpaRepository<T, ID>
{
	/** The Constant DEFAULT_ENTITY_PATH_RESOLVER. */
	//All instance variables are available in super, but they are private
	private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

	/** The path. */
	private final EntityPath<T> path;

	/** The builder. */
	private final PathBuilder<T> builder;

	/** The querydsl. */
	private final Querydsl querydsl;

	/**
	 * Instantiates a new c fetch capable query dsl jpa repository.
	 *
	 * @param entityInformation the entity information
	 * @param entityManager the entity manager
	 */
	public CFetchCapableQueryDslJpaRepository (JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager)
	{
		this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
	}

	/**
	 * Instantiates a new c fetch capable query dsl jpa repository.
	 *
	 * @param entityInformation the entity information
	 * @param entityManager the entity manager
	 * @param resolver the resolver
	 */
	public CFetchCapableQueryDslJpaRepository (JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager, EntityPathResolver resolver)
	{
		super(entityInformation, entityManager);
		this.path = resolver.createPath(entityInformation.getJavaType());
		this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
		this.querydsl = new Querydsl(entityManager, builder);
	}


	/* (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dao.support.IFetchCapableQueryDslJpaRepository#findAll(com.querydsl.core.types.Predicate, org.springframework.data.domain.Pageable, sk.qbsw.et.browser.core.dao.support.CJoinDescriptor[])
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public Page<T> findAll (Predicate predicate, Pageable pageable, CJoinDescriptor<?>... joinDescriptors)
	{
		JPQLQuery<T> countQuery = (JPQLQuery<T>) createQuery(predicate);
		JPQLQuery<T> query = querydsl.applyPagination(pageable, createFetchQuery(predicate, joinDescriptors));

		Long total = countQuery.fetchCount();
		List<T> content = total > pageable.getOffset() ? query.fetch() : Collections.<T>emptyList();

		return new PageImpl<>(content, pageable, total);
	}

	/**
	 * Creates the fetch query.
	 *
	 * @param predicate the predicate
	 * @param joinDescriptors the join descriptors
	 * @return the JPQL query
	 */
	@SuppressWarnings ("unchecked")
	protected JPQLQuery<T> createFetchQuery (Predicate predicate, CJoinDescriptor<?>... joinDescriptors)
	{
		JPQLQuery<T> query = (JPQLQuery<T>) querydsl.createQuery(path);
		for (CJoinDescriptor<?> joinDescriptor : joinDescriptors)
		{
			join(joinDescriptor, query);
		}
		return query.where(predicate);
	}

	/**
	 * Join.
	 *
	 * @param joinDescriptor the join descriptor
	 * @param query the query
	 * @return the JPQL query
	 */
	private JPQLQuery<T> join (CJoinDescriptor<?> joinDescriptor, JPQLQuery<T> query)
	{
		switch (joinDescriptor.type)
		{
			case INNERJOIN:
				query.innerJoin(joinDescriptor.path).fetchJoin();
				break;
			case JOIN:
				query.join(joinDescriptor.path).fetchJoin();
				break;
			case LEFTJOIN:
				query.leftJoin(joinDescriptor.path).fetchJoin();
				break;
			case RIGHTJOIN:
				query.rightJoin(joinDescriptor.path).fetchJoin();
				break;
			default:
				throw new IllegalArgumentException("the join not supported");
		}
		return query;
	}
}
