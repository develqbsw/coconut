package sk.qbsw.core.browser.core.dao.jpa.qdsl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.path.EntityPathBase;

import sk.qbsw.core.browser.core.dao.IBrwDao;
import sk.qbsw.core.browser.core.dto.CBrwDataDto;
import sk.qbsw.core.browser.core.model.CFilterParameter;
import sk.qbsw.core.browser.core.model.CJoinExpression;
import sk.qbsw.core.browser.core.model.COrderParameter;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The browser query dsl dao.
 *
 * @author Marian Oravec
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @param <PK> the generic type
 * @param <T> the generic type
 * 
 * @version 1.15.0
 * @since 1.15.0
 */
public class ABrwQDslDao<PK, T extends IEntity<PK>>extends AFilterQDslDao<PK, T> implements IBrwDao<PK, T>
{
	/** The entity path. */
	protected final EntityPathBase<T> entityPath;

	/** The join expressions. */
	private final Set<CJoinExpression<T>> joinExpressions;

	/**
	 * Instantiates a new a brw q dsl dao.
	 *
	 * @param entityPath the entity path
	 */
	public ABrwQDslDao (EntityPathBase<T> entityPath)
	{
		this(entityPath, null);
	}

	/**
	 * Instantiates a new a brw q dsl dao.
	 *
	 * @param entityPath the entity path
	 * @param joinExpressions the join expressions
	 */
	public ABrwQDslDao (EntityPathBase<T> entityPath, CJoinExpression<T>[] joinExpressions)
	{
		this.entityPath = entityPath;

		if (joinExpressions == null)
		{
			this.joinExpressions = null;
		}
		else
		{
			this.joinExpressions = new HashSet<CJoinExpression<T>>();
			addJoinExpressionsWithCheck(joinExpressions);

		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.browser.dao.IBrwDao#read(java.lang.Object)
	 */
	@Override
	public T read (final PK id)
	{
		return this.em.find(entityPath.getType(), id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.browser.dao.IBrwDao#getCompleteList(java.util.List, java.util.List, java.lang.Long, java.lang.Long)
	 */
	@Override
	public CBrwDataDto<PK, T> getCompleteList (final List<? extends CFilterParameter> wheres, final List<COrderParameter> orderSpecifiers, final Long offset, final Long limit)
	{
		final JPAQuery q = this.createQuery(entityPath);

		this.appendJoinPart(q); // treba doriesit problem so selektom ktory obsahuje dvakrat rovnaky stlpec v pripade pridania joinov
		this.appendWherePart(q, wheres);
		this.appendOrder(q, orderSpecifiers);
		this.appendLimit(q, offset, limit);

		return this.getCompleteList(q);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.browser.dao.IBrwDao#getList(java.util.List, java.util.List, java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<T> getList (final List<? extends CFilterParameter> wheres, final List<COrderParameter> orderSpecifiers, final Long from, final Long count)
	{
		final JPAQuery q = this.createQuery(entityPath);

		this.appendJoinPart(q);
		this.appendWherePart(q, wheres);
		this.appendOrder(q, orderSpecifiers);
		this.appendLimit(q, from, count);

		return q.list(entityPath);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.browser.dao.IBrwDao#getCount(java.util.List)
	 */
	@Override
	public long getCount (final List<? extends CFilterParameter> wheres)
	{
		final JPAQuery q = this.createQuery(entityPath);

		this.appendWherePart(q, wheres);

		return q.count();
	}

	/**
	 * Gets the complete list.
	 *
	 * @param query the query
	 * @return the complete list
	 */
	protected CBrwDataDto<PK, T> getCompleteList (final JPAQuery query)
	{
		final CBrwDataDto<PK, T> result;
		final NumberExpression<Long> totalCountExpression = Expressions.numberTemplate(Long.class, "totalcount()");
		final List<Tuple> tuples = query.list(this.entityPath, totalCountExpression);
		final Iterator<Tuple> iter = tuples.iterator();
		final List<T> list;
		final Long totalCount;
		Tuple tuple;

		if (iter.hasNext())
		{
			tuple = iter.next();
			list = new ArrayList<>(tuples.size());
			totalCount = tuple.get(totalCountExpression);

			list.add(tuple.get(this.entityPath));

			while (iter.hasNext())
			{
				tuple = iter.next();
				list.add(tuple.get(this.entityPath));
			}
		}
		else
		{
			list = Collections.emptyList();
			totalCount = 0L;
		}

		result = new CBrwDataDto<>(list, totalCount);

		return result;
	}

	/**
	 * Adds the join expressions with check.
	 *
	 * @param joinExpressions the join expressions
	 */
	protected void addJoinExpressionsWithCheck (CJoinExpression<T>[] joinExpressions)
	{
		for (CJoinExpression<T> joinExpression : joinExpressions)
		{
			boolean existing = !this.joinExpressions.add(joinExpression);
			if (existing)
			{
				throw new IllegalStateException("duplicate item for " + joinExpression);
			}
		}
	}

	/**
	 * Append join part.
	 *
	 * @param query the query
	 */
	protected void appendJoinPart (JPAQuery query)
	{
		if (joinExpressions != null && !joinExpressions.isEmpty())
		{
			for (CJoinExpression<T> joinExpression : joinExpressions)
			{
				switch (joinExpression.getJoinType())
				{
					case LEFT:
						appendLeftJoinPart(query, joinExpression);
						break;
					case INNER:
						appendInnerJoinPart(query, joinExpression);
						break;
					default:
						throw new IllegalStateException("Unsupported joinType in CJoinExpression: " + joinExpression.getJoinType());
				}

				if (joinExpression.isFetch())
				{
					query.fetch();
				}

			}
		}

	}

	/**
	 * Append left join part.
	 *
	 * @param query the query
	 * @param joinExpression the join expression
	 */
	private void appendLeftJoinPart (JPAQuery query, CJoinExpression<T> joinExpression)
	{
		if (joinExpression.getAllias() != null)
		{
			if (joinExpression.getEntity() == null)
			{
				query.leftJoin(joinExpression.getCollection(), joinExpression.getAllias());
			}
			else
			{
				query.leftJoin(joinExpression.getEntity(), joinExpression.getAllias());
			}
		}
		else
		{
			if (joinExpression.getEntity() == null)
			{
				query.leftJoin(joinExpression.getCollection());
			}
			else
			{
				query.leftJoin(joinExpression.getEntity());
			}
		}
	}

	/**
	 * Append inner join part.
	 *
	 * @param query the query
	 * @param joinExpression the join expression
	 */
	private void appendInnerJoinPart (JPAQuery query, CJoinExpression<T> joinExpression)
	{
		if (joinExpression.getAllias() != null)
		{
			if (joinExpression.getEntity() == null)
			{
				query.innerJoin(joinExpression.getCollection(), joinExpression.getAllias());
			}
			else
			{
				query.innerJoin(joinExpression.getEntity(), joinExpression.getAllias());
			}
		}
		else
		{
			if (joinExpression.getEntity() == null)
			{
				query.innerJoin(joinExpression.getCollection());
			}
			else
			{
				query.innerJoin(joinExpression.getEntity());
			}
		}
	}

	/**
	 * Append limit.
	 *
	 * @param q the q
	 * @param offset the offset
	 * @param limit the limit
	 */
	protected void appendLimit (final JPAQuery q, final Long offset, final Long limit)
	{
		if (offset != null)
		{
			q.offset(offset);
		}

		if (limit != null)
		{
			q.limit(limit);
		}
	}
}
