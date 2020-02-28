package sk.qbsw.core.persistence.dao.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import sk.qbsw.core.persistence.model.CJoinDescriptor;

/**
 * The join fetch capable querydsl repository implementation.
 *
 * @author Adrian Lopez (http://stackoverflow.com/a/21630123)
 * @author Tomas Lauro
 * @version 1.16.0
 * @param <T> the entity type
 * @param <PK> the id type
 * @since 1.16.0
 */
public class CFetchCapableQueryDslJpaRepository<T, PK extends Serializable>extends QuerydslJpaRepository<T, PK> implements IFetchCapableQueryDslJpaRepository<T, PK>
{
	/** The Constant DEFAULT_ENTITY_PATH_RESOLVER. */
	// All instance variables are available in super, but they are private
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
	public CFetchCapableQueryDslJpaRepository (JpaEntityInformation<T, PK> entityInformation, EntityManager entityManager)
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
	public CFetchCapableQueryDslJpaRepository (JpaEntityInformation<T, PK> entityInformation, EntityManager entityManager, EntityPathResolver resolver)
	{
		super(entityInformation, entityManager);
		this.path = resolver.createPath(entityInformation.getJavaType());
		this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
		this.querydsl = new Querydsl(entityManager, builder);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dao.support.IFetchCapableQueryDslJpaRepository#findOne(java.io.Serializable, sk.qbsw.et.browser.core.model.CJoinDescriptor[])
	 */
	@Override
	public T findOne (PK id, CJoinDescriptor<?>... joinDescriptors)
	{
		return createFetchQuery(false, this.builder.get("id").eq(id), joinDescriptors).select(path).fetchOne();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dao.support.IFetchCapableQueryDslJpaRepository#findAll(com.querydsl.core.types.Predicate, org.springframework.data.domain.Pageable, sk.qbsw.et.browser.core.dao.support.CJoinDescriptor[])
	 */
	@Override
	public Page<T> findAll (Predicate predicate, Pageable pageable, CJoinDescriptor<?>... joinDescriptors)
	{
		return findAll(false, predicate, pageable, joinDescriptors);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dao.support.IFetchCapableQueryDslJpaRepository#findAll(com.querydsl.core.types.Predicate, org.springframework.data.domain.Sort, sk.qbsw.et.browser.core.model.CEntityJoin[])
	 */
	@Override
	public List<T> findAll (Predicate predicate, Sort sort, CJoinDescriptor<?>... joinDescriptors)
	{
		return findAll(false, predicate, sort, joinDescriptors);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.support.IFetchCapableQueryDslJpaRepository#findAll(org.springframework.data.domain.Sort, sk.qbsw.core.persistence.model.CJoinDescriptor[])
	 */
	@Override
	public List<T> findAll (Sort sort, CJoinDescriptor<?>... joinDescriptors)
	{
		return findAll(false, null, sort, joinDescriptors);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dao.support.IFetchCapableQueryDslJpaRepository#findAll(boolean, com.querydsl.core.types.Predicate, org.springframework.data.domain.Pageable, sk.qbsw.et.browser.core.model.CJoinDescriptor[])
	 */
	@Override
	public Page<T> findAll (boolean distinct, Predicate predicate, Pageable pageable, CJoinDescriptor<?>... joinDescriptors)
	{
		JPQLQuery<T> countQuery = (JPQLQuery<T>) createFetchCountQuery(distinct, predicate, joinDescriptors);
		JPQLQuery<T> query = querydsl.applyPagination(pageable, createFetchQuery(distinct, predicate, joinDescriptors));

		Long total = countQuery.fetchCount();
		if (pageable.isPaged())
		{
			List<T> content = total > pageable.getOffset() ? query.fetch() : Collections.<T>emptyList();
			return new PageImpl<>(content, pageable, total);
		}
		else
		{
			return new PageImpl<>(query.fetch(), pageable, total);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dao.support.IFetchCapableQueryDslJpaRepository#findAll(boolean, com.querydsl.core.types.Predicate, org.springframework.data.domain.Sort, sk.qbsw.et.browser.core.model.CJoinDescriptor[])
	 */
	@Override
	public List<T> findAll (boolean distinct, Predicate predicate, Sort sort, CJoinDescriptor<?>... joinDescriptors)
	{
		return executeSorted(createFetchQuery(distinct, predicate, joinDescriptors).select(path), sort);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.support.IFetchCapableQueryDslJpaRepository#findAll(boolean, org.springframework.data.domain.Sort, sk.qbsw.core.persistence.model.CJoinDescriptor[])
	 */
	@Override
	public List<T> findAll (boolean distinct, Sort sort, CJoinDescriptor<?>... joinDescriptors)
	{
		return findAll(distinct, null, sort, joinDescriptors);
	}


	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dao.support.IFetchCapableQueryDslJpaRepository#count(com.querydsl.core.types.Predicate, sk.qbsw.et.browser.core.model.CJoinDescriptor[])
	 */
	@Override
	public long count (Predicate predicate, CJoinDescriptor<?>... joinDescriptors)
	{
		return createFetchCountQuery(false, predicate, joinDescriptors).fetchCount();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.et.browser.core.dao.support.IFetchCapableQueryDslJpaRepository#count(boolean, com.querydsl.core.types.Predicate, sk.qbsw.et.browser.core.model.CJoinDescriptor[])
	 */
	@Override
	public long count (boolean distinct, Predicate predicate, CJoinDescriptor<?>... joinDescriptors)
	{
		return createFetchCountQuery(distinct, predicate, joinDescriptors).fetchCount();
	}

	/**
	 * Creates the fetch count query.
	 *
	 * @param distinct the distinct
	 * @param predicate the predicate
	 * @param joinDescriptors the join descriptors
	 * @return the JPQL query
	 */
	@SuppressWarnings ("unchecked")
	protected JPQLQuery<T> createFetchCountQuery (boolean distinct, Predicate predicate, CJoinDescriptor<?>... joinDescriptors)
	{
		JPQLQuery<T> countQuery = (JPQLQuery<T>) createQuery(predicate);

		if (distinct)
		{
			countQuery.distinct(); // add distinct
		}

		for (CJoinDescriptor<?> joinDescriptor : joinDescriptors)
		{
			addFetchJoin(joinDescriptor, countQuery);
		}
		return countQuery;
	}

	/**
	 * Creates the fetch query.
	 *
	 * @param predicate the predicate
	 * @param joinDescriptors the join descriptors
	 * @return the JPQL query
	 */
	@SuppressWarnings ("unchecked")
	protected JPQLQuery<T> createFetchQuery (boolean distinct, Predicate predicate, CJoinDescriptor<?>... joinDescriptors)
	{
		JPQLQuery<T> query = (JPQLQuery<T>) querydsl.createQuery(path);

		if (distinct)
		{
			query.distinct(); // add distinct
		}

		for (CJoinDescriptor<?> joinDescriptor : joinDescriptors)
		{
			addFetchJoin(joinDescriptor, query);
		}

		if (predicate != null)
		{
			return query.where(predicate);
		}
		else
		{
			return query;
		}
	}

	/**
	 * Adds the fetch join.
	 *
	 * @param joinDescriptor the join descriptor
	 * @param query the query
	 * @return the JPQL query
	 */
	private JPQLQuery<T> addFetchJoin (CJoinDescriptor<?> joinDescriptor, JPQLQuery<T> query)
	{
		switch (joinDescriptor.getType())
		{
			case INNERJOIN:
				addInnerJoin(joinDescriptor, query);
				break;
			case JOIN:
				addJoin(joinDescriptor, query);
				break;
			case LEFTJOIN:
				addLeftJoin(joinDescriptor, query);
				break;
			case RIGHTJOIN:
				addRightJoin(joinDescriptor, query);
				break;
			default:
				throw new IllegalArgumentException("the join not supported");
		}
		return query.fetchJoin();
	}

	/**
	 * Adds the left join.
	 *
	 * @param <P> the generic type
	 * @param joinDescriptor the join descriptor
	 * @param query the query
	 * @return the JPQL query
	 */
	private <P> JPQLQuery<T> addLeftJoin (CJoinDescriptor<P> joinDescriptor, JPQLQuery<T> query)
	{
		if (joinDescriptor.getTarget() != null)
		{
			if (joinDescriptor.getAlias() != null)
			{
				query.leftJoin(joinDescriptor.getTarget(), joinDescriptor.getAlias());
			}
			else
			{
				query.leftJoin(joinDescriptor.getTarget());
			}
		}
		else
		{
			if (joinDescriptor.getAlias() != null)
			{
				query.leftJoin(joinDescriptor.getCollectionTarget(), joinDescriptor.getAlias());
			}
			else
			{
				query.leftJoin(joinDescriptor.getCollectionTarget());
			}
		}

		return query;
	}

	/**
	 * Adds the right join.
	 *
	 * @param <P> the generic type
	 * @param joinDescriptor the join descriptor
	 * @param query the query
	 * @return the JPQL query
	 */
	private <P> JPQLQuery<T> addRightJoin (CJoinDescriptor<P> joinDescriptor, JPQLQuery<T> query)
	{
		if (joinDescriptor.getTarget() != null)
		{
			if (joinDescriptor.getAlias() != null)
			{
				query.rightJoin(joinDescriptor.getTarget(), joinDescriptor.getAlias());
			}
			else
			{
				query.rightJoin(joinDescriptor.getTarget());
			}
		}
		else
		{
			if (joinDescriptor.getAlias() != null)
			{
				query.rightJoin(joinDescriptor.getCollectionTarget(), joinDescriptor.getAlias());
			}
			else
			{
				query.rightJoin(joinDescriptor.getCollectionTarget());
			}
		}

		return query;
	}

	/**
	 * Adds the inner join.
	 *
	 * @param <P> the generic type
	 * @param joinDescriptor the join descriptor
	 * @param query the query
	 * @return the JPQL query
	 */
	private <P> JPQLQuery<T> addInnerJoin (CJoinDescriptor<P> joinDescriptor, JPQLQuery<T> query)
	{
		if (joinDescriptor.getTarget() != null)
		{
			if (joinDescriptor.getAlias() != null)
			{
				query.innerJoin(joinDescriptor.getTarget(), joinDescriptor.getAlias());
			}
			else
			{
				query.innerJoin(joinDescriptor.getTarget());
			}
		}
		else
		{
			if (joinDescriptor.getAlias() != null)
			{
				query.innerJoin(joinDescriptor.getCollectionTarget(), joinDescriptor.getAlias());
			}
			else
			{
				query.innerJoin(joinDescriptor.getCollectionTarget());
			}
		}

		return query;
	}

	/**
	 * Adds the join.
	 *
	 * @param <P> the generic type
	 * @param joinDescriptor the join descriptor
	 * @param query the query
	 * @return the JPQL query
	 */
	private <P> JPQLQuery<T> addJoin (CJoinDescriptor<P> joinDescriptor, JPQLQuery<T> query)
	{
		if (joinDescriptor.getTarget() != null)
		{
			if (joinDescriptor.getAlias() != null)
			{
				query.join(joinDescriptor.getTarget(), joinDescriptor.getAlias());
			}
			else
			{
				query.join(joinDescriptor.getTarget());
			}
		}
		else
		{
			if (joinDescriptor.getAlias() != null)
			{
				query.join(joinDescriptor.getCollectionTarget(), joinDescriptor.getAlias());
			}
			else
			{
				query.join(joinDescriptor.getCollectionTarget());
			}
		}

		return query;
	}

	/**
	 * Executes the given {@link JPQLQuery} after applying the given {@link Sort}.
	 *
	 * @param query must not be {@literal null}.
	 * @param sort must not be {@literal null}.
	 * @return the list
	 */
	private List<T> executeSorted (JPQLQuery<T> query, Sort sort)
	{
		return querydsl.applySorting(sort, query).fetch();
	}
}
