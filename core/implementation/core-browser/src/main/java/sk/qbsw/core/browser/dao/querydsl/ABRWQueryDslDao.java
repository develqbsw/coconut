package sk.qbsw.core.browser.dao.querydsl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.mysema.query.JoinType;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.path.EntityPathBase;

import sk.qbsw.core.browser.dao.IBRWDao;
import sk.qbsw.core.browser.dto.CBRWDataDTO;
import sk.qbsw.core.browser.model.CJoinedExpression;
import sk.qbsw.core.persistence.dao.querydsl.AQueryDslDao;
import sk.qbsw.core.persistence.model.CFilterParameter;
import sk.qbsw.core.persistence.model.COrderParameter;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The Class ABRWQueryDslDao.
 *
 * @param <PK> Primary key
 * @param <V> Browser Entity
 */
@SuppressWarnings("serial")
public abstract class ABRWQueryDslDao<PK, V extends IEntity<PK>> extends AQueryDslDao<PK, V> implements IBRWDao<PK, V> {

	/** The entity path brw. */
	protected final EntityPathBase<V> entityPathBRW;

	/**
	 * The Class EntityFetchInformation.
	 */
	private static class EntityFetchInformation implements Serializable {

		/** The entity class. */
		private final Class<?> entityClass;

		/** The fields fetch information. */
		private final List<FieldFetchInformation> fieldsFetchInformation;

		/**
		 * Instantiates a new entity fetch information.
		 *
		 * @param entityClass the entity class
		 * @param fieldsFetchInformation the fields fetch information
		 */
		public EntityFetchInformation(Class<?> entityClass, List<FieldFetchInformation> fieldsFetchInformation) {
			this.entityClass = entityClass;
			this.fieldsFetchInformation = fieldsFetchInformation;
		}

		/**
		 * Gets the entity class.
		 *
		 * @return the entity class
		 */
		@SuppressWarnings("unused")
		public Class<?> getEntityClass() {
			return this.entityClass;
		}

		/**
		 * Gets the fields fetch information.
		 *
		 * @return the fields fetch information
		 */
		public List<FieldFetchInformation> getFieldsFetchInformation() {
			return this.fieldsFetchInformation;
		}

	}

	/**
	 * The Class FieldFetchInformation.
	 */
	private static class FieldFetchInformation implements Serializable {

		/** The entity path. */
		private final EntityPathBase<?> entityPath;

		/** The join type. */
		private final JoinType joinType;

		/**
		 * Instantiates a new field fetch information.
		 *
		 * @param entityPath the entity path
		 * @param joinType the join type
		 */
		public FieldFetchInformation(EntityPathBase<?> entityPath, JoinType joinType) {
			this.entityPath = entityPath;
			this.joinType = joinType;
		}

		/**
		 * Gets the entity path.
		 *
		 * @return the entity path
		 */
		public EntityPathBase<?> getEntityPath() {
			return this.entityPath;
		}

		/**
		 * Gets the join type.
		 *
		 * @return the join type
		 */
		public JoinType getJoinType() {
			return this.joinType;
		}

	}

	/** The Constant ENTITIES_FETCH_INFORMATION_CACHE. */
	private static final Map<Class<?>, EntityFetchInformation> ENTITIES_FETCH_INFORMATION_CACHE = new HashMap<>();

	/**
	 * Evalute entity fetch information.
	 *
	 * @param entityPath the entity path
	 * @return the entity fetch information
	 */
	private static EntityFetchInformation evaluteEntityFetchInformation(EntityPathBase<?> entityPath) {
		final Class<?> entityClass = (Class<?>) entityPath.getAnnotatedElement();
		final Field[] entityFields = entityClass.getDeclaredFields();
		EntityFetchInformation result = null;
		List<FieldFetchInformation> fieldFetchInformationList = null;

		for (final Field entityField : entityFields) {
			final Annotation[] entityFieldAnnotations = entityField.getAnnotations();

			Fetch foundFetch = null;
			JoinColumn foundJoinColumn = null;

			for (final Annotation entityFieldAnnotation : entityFieldAnnotations) {
				if (entityFieldAnnotation instanceof Fetch) {
					foundFetch = (Fetch) entityFieldAnnotation;

					if (foundJoinColumn != null) {
						break;
					}
				}
				else if (entityFieldAnnotation instanceof JoinColumn) {
					foundJoinColumn = (JoinColumn) entityFieldAnnotation;

					if (foundFetch != null) {
						break;
					}
				}
			}

			if ((foundFetch != null) && (foundFetch.value() == FetchMode.JOIN) && (foundJoinColumn != null)) {
				EntityPathBase<?> joinedEntityPath = null;

				for (final Field qfield : entityPath.getClass().getFields()) {
					if (qfield.getName().equals(entityField.getName())) {
						try {
							joinedEntityPath = (EntityPathBase<?>) qfield.get(entityPath);
							break;
						} catch (final IllegalAccessException e) {
							throw new IllegalStateException(e.getMessage(), e);
						}
					}
				}

				if (joinedEntityPath != null) {
					final JoinType joinType = foundJoinColumn.nullable() ? JoinType.LEFTJOIN : JoinType.INNERJOIN;
					final FieldFetchInformation fieldFetchInformation = new FieldFetchInformation(joinedEntityPath, joinType);

					if (fieldFetchInformationList == null) {
						fieldFetchInformationList = new ArrayList<FieldFetchInformation>();
					}

					fieldFetchInformationList.add(fieldFetchInformation);
				}
			}
		}

		if (fieldFetchInformationList != null) {
			result = new EntityFetchInformation(entityClass, fieldFetchInformationList);
		}

		return result;
	}

	/**
	 * Gets the entity fetch information.
	 *
	 * @param entityPath the entity path
	 * @return the entity fetch information
	 */
	private static EntityFetchInformation getEntityFetchInformation(EntityPathBase<?> entityPath) {
		EntityFetchInformation result;
		final Class<?> entityClass = (Class<?>) entityPath.getAnnotatedElement();

		synchronized (ENTITIES_FETCH_INFORMATION_CACHE) {
			result = ENTITIES_FETCH_INFORMATION_CACHE.get(entityClass);

			if (result == null) {
				result = evaluteEntityFetchInformation(entityPath);

				ENTITIES_FETCH_INFORMATION_CACHE.put(entityClass, result);
			}
		}

		return result;
	}

	/** The join query. */
	private final Set<CJoinedExpression<?>> joinQuery;//used for brw

	/**
	 * Instantiates a new ABRW query dsl dao.
	 *
	 * @param entityPathBRW the entity path brw
	 * @param entityPathCRUD the entity path crud
	 * @param joinQueryArr the join query arr
	 */
	public ABRWQueryDslDao(EntityPathBase<V> entityPathBRW, CJoinedExpression<?>[] joinQueryArr) {
		super();
		this.entityPathBRW = entityPathBRW;

		if (joinQueryArr == null) {
			this.joinQuery = null;
		}
		else {
			this.joinQuery = new HashSet<CJoinedExpression<?>>();
			addAndCheckCJoinedExpression(joinQueryArr);

		}
	}

	/**
	 * Adds the and check c joined expression.
	 *
	 * @param joinQueryArr the join query arr
	 */
	private void addAndCheckCJoinedExpression(CJoinedExpression<?>[] joinQueryArr) {
		for (CJoinedExpression<?> cJoinedExpression : joinQueryArr) {
			boolean existing = !this.joinQuery.add(cJoinedExpression);
			if (existing) {
				throw new IllegalStateException("duplicate item for " + cJoinedExpression);
			}
		}
	}

	/**
	 * Append join part.
	 *
	 * @param q the q
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void appendJoinPart(JPAQuery q) {
		final EntityFetchInformation efi = getEntityFetchInformation(this.entityPathBRW);

		if (efi != null) {
			final List<FieldFetchInformation> fieldsFetchInformation = efi.getFieldsFetchInformation();

			for (final FieldFetchInformation fieldFetchInformation : fieldsFetchInformation) {
				final EntityPathBase<?> entityPath = fieldFetchInformation.getEntityPath();
				final JoinType joinType = fieldFetchInformation.getJoinType();

				switch (joinType) {
					case INNERJOIN:
						q.innerJoin(entityPath);
						break;

					case LEFTJOIN:
						q.leftJoin(entityPath);
						break;

					default:
						throw new IllegalStateException("Unsupported joinType in fieldFetchInformation: " + joinType);
				}

				q.fetch();
			}
		}
		if (joinQuery != null && joinQuery.size() > 0) {
			for (CJoinedExpression exp : joinQuery) {
				// TODO: treba sa opytat autora, naco je toto dobre...
				JPAQuery quer;
				switch (exp.getJoinType()) {
					case LEFT:
						if (exp.getAllias() != null) {
							if (exp.getEntity() == null) {
								quer = q.leftJoin(exp.getCollection(), exp.getAllias());
							}
							else {
								quer = q.leftJoin(exp.getEntity(), exp.getAllias());
							}
						}
						else {
							if (exp.getEntity() == null) {
								quer = q.leftJoin(exp.getCollection());
							}
							else {
								quer = q.leftJoin(exp.getEntity());
							}

						}
						break;
					case INNER:
						if (exp.getAllias() != null) {
							if (exp.getEntity() == null) {
								quer = q.innerJoin(exp.getCollection(), exp.getAllias());
							}
							else {
								quer = q.innerJoin(exp.getEntity(), exp.getAllias());
							}
						}
						else {
							if (exp.getEntity() == null) {
								quer = q.innerJoin(exp.getCollection());
							}
							else {
								quer = q.innerJoin(exp.getEntity());
							}
						}
						break;
					default:
						throw new IllegalStateException("Unsupported joinType in CJoinedExpression: " + exp.getJoinType());
				}
				if (exp.isFetch()) {
					q.fetch();
				}

			}
		}

	}

	/**
	 * Creates the brw query.
	 *
	 * @return the JPA query
	 */
	private JPAQuery createBRWQuery() {
		if (this.entityPathBRW == null) {
			throw new IllegalStateException("Cannot create query: pathBaseBRW is null. Subclass of " + this.getClass() + " passed null to super contructor call?");
		}

		return this.createQuery(this.entityPathBRW);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.IBRWDAO#getBRWCount(java.util.List)
	 */
	@Override
	public long getBRWCount(final List<? extends CFilterParameter> wheres) {
		final JPAQuery q = this.createBRWQuery();

		this.appendWherePart(q, wheres);

		final long result = this.getCount(q);

		return result;
	}

	/**
	 * Gets the BRW list.
	 *
	 * @param q the q
	 * @return the BRW list
	 */
	protected List<V> getBRWList(final JPAQuery q) {

		return q.list(this.entityPathBRW);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.IBRWDAO#getBRWList(java.util.List, java.util.List, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<V> getBRWList(final List<? extends CFilterParameter> wheres, final List<COrderParameter> orderSpecifiers, final Long from, final Long count) {
		final JPAQuery q = this.createBRWQuery();

		this.appendJoinPart(q);
		this.appendWherePart(q, wheres);
		this.appendOrderByPart(q, orderSpecifiers);
		this.appendLimit(q, from, count);

		final List<V> result = this.getBRWList(q);

		return result;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.IFilterDAO#getColummnValuesList(com.mysema.query.types.expr.SimpleExpression, java.util.List)
	 */
	@Override
	public <X> List<X> getColummnValuesList(SimpleExpression<X> column, List<? extends CFilterParameter> fixedFilter) {
		final JPAQuery q = this.createBRWQuery();

		this.appendWherePart(q, fixedFilter);
		final List<X> result = q.distinct().list(column);

		return result;
	}

	/**
	 * Gets the complete brw list.
	 *
	 * @param q the q
	 * @return the complete brw list
	 */
	protected CBRWDataDTO<PK, V> getCompleteBRWList(final JPAQuery q) {
		final CBRWDataDTO<PK, V> result;
		final NumberExpression<Long> totalCountExpression = Expressions.numberTemplate(Long.class, "totalcount()");
		final List<Tuple> tuples = q.list(this.entityPathBRW, totalCountExpression);
		final Iterator<Tuple> iter = tuples.iterator();
		final List<V> list;
		final Long totalCount;
		Tuple tuple;

		if (iter.hasNext()) {
			tuple = iter.next();
			list = new ArrayList<V>(tuples.size());
			totalCount = tuple.get(totalCountExpression);

			list.add(tuple.get(this.entityPathBRW));

			while (iter.hasNext()) {
				tuple = iter.next();
				list.add(tuple.get(this.entityPathBRW));
			}
		}
		else {
			list = Collections.emptyList();
			totalCount = 0L;
		}

		result = new CBRWDataDTO<PK, V>(list, totalCount);

		return result;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.IBRWDAO#getCompleteBRWList(java.util.List, java.util.List, java.lang.Long, java.lang.Long)
	 */
	@Override
	public CBRWDataDTO<PK, V> getCompleteBRWList(final List<? extends CFilterParameter> wheres, final List<COrderParameter> orderSpecifiers, final Long from, final Long count) {
		final JPAQuery q = this.createBRWQuery();

		this.appendJoinPart(q); // treba doriesit problem so selektom ktory obsahuje dvakrat rovnaky stlpec v pripade pridania joinov
		this.appendWherePart(q, wheres);
		this.appendOrderByPart(q, orderSpecifiers);
		this.appendLimit(q, from, count);

		final CBRWDataDTO<PK, V> result = this.getCompleteBRWList(q);

		return result;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.IBRWDAO#readFromView(java.lang.Object)
	 */
	@Override
	public V readFromView(PK id) {
		final Class<? extends V> cls = this.entityPathBRW.getType();
		final V result = this.em.find(cls, id);

		if (result == null) {
			throw new EntityNotFoundException("Entity (BRW) of class " + cls + " with primary key " + id + " was not found.");
		}

		return result;
	}
}