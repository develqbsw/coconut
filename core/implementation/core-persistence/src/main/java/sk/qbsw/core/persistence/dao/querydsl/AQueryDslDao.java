package sk.qbsw.core.persistence.dao.querydsl;

import java.util.List;

import org.joda.time.DateTime;

import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.BooleanOperation;
import com.mysema.query.types.expr.ComparableExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.expr.StringExpression;

import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.persistence.dao.IDao;
import sk.qbsw.core.persistence.dao.jpa.AJpaDao;
import sk.qbsw.core.persistence.model.ACustomFilterParameter;
import sk.qbsw.core.persistence.model.CFilterParameter;
import sk.qbsw.core.persistence.model.COrderParameter;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * @author Marián Oravec
 * @since 1.13.0
 * @version 1.13.0
 */
@SuppressWarnings("serial")
@CLogged
public abstract class AQueryDslDao<PK, T extends IEntity<PK>> extends AJpaDao implements IDao {

	protected JPAQuery createQuery(EntityPath<?>... entityPaths) {
		return new JPAQuery(this.em).from(entityPaths);
	}

	protected JPASubQuery createSubQuery(EntityPath<?>... entityPaths) {
		return new JPASubQuery().from(entityPaths);
	}

	protected JPAUpdateClause createUpdateClause(EntityPath<?> entityPath) {
		return new JPAUpdateClause(this.em, entityPath);
	}

	protected JPADeleteClause createDeleteClause(EntityPath<?> entityPath) {
		return new JPADeleteClause(this.em, entityPath);
	}

	protected static <T> BooleanExpression eq(final SimpleExpression<T> path, final T right) {
		return right == null ? path.isNull() : path.eq(right);
	}

	protected boolean isAttached(Object entity) {
		return this.em.contains(entity);
	}

	protected void refresh(Object... entities) {
		for (final Object entity : entities) {
			this.em.refresh(entity);
		}
	}

	/**
	 * Append limit.
	 *
	 * @param q the q
	 * @param from the from
	 * @param count the count
	 */
	protected void appendLimit(final JPAQuery q, final Long from, final Long count) {
		if (from != null) {
			q.offset(from);
		}
	
		if (count != null) {
			q.limit(count);
		}
	}

	/**
	 * Append order by part.
	 *
	 * @param q the q
	 * @param orderSpecifiers the order specifiers
	 */
	@SuppressWarnings("rawtypes")
	protected void appendOrderByPart(final JPAQuery q, final List<COrderParameter> orderSpecifiers) {
		if ((orderSpecifiers != null) && !orderSpecifiers.isEmpty()) {
			for (final COrderParameter orderSpecifier : orderSpecifiers) {
				final ComparableExpressionBase<? extends Comparable<?>> exp = orderSpecifier.getExpression();
				final ComparableExpressionBase expComp = exp;
	
				q.orderBy(orderSpecifier.isAscending() ? expComp.asc() : expComp.desc());
			}
		}
	}

	/**
	 * Append where part.
	 *
	 * @param q the q
	 * @param wheres the wheres
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void appendWherePart(final JPAQuery q, final List<? extends CFilterParameter> wheres) {
		if ((wheres != null) && !wheres.isEmpty()) {
			for (final CFilterParameter where : wheres) {
				final Object value = where.getValue();
				final EOperator operator = where.getOperator();
				final SimpleExpression<?> exp = where.getExpression();
				final Predicate p;
	
				if (where instanceof ACustomFilterParameter) {
					q.where(((ACustomFilterParameter) where).getPredicate());
					continue;
				}
	
				if (exp instanceof BooleanOperation) {
					p = (BooleanOperation) exp;
				}
				else if (EOperator.LIKE_IGNORE_CASE == operator && (exp instanceof StringExpression)) {
					p = ((StringExpression) exp).containsIgnoreCase((String) value);
				}
				else if ((operator != null) && (exp instanceof ComparableExpression)) {
					switch (operator) {
						case GT:
							p = ((ComparableExpression) exp).goe((Comparable<?>) value);
							break;
						case LT:
							p = ((ComparableExpression) exp).loe((Comparable<?>) value);
							break;
						case BETWEEN_DATE_TIME:
							if (value instanceof DateTime) {
								DateTime valueDT = (DateTime) value;
								DateTime start = valueDT.withTimeAtStartOfDay();
								DateTime end = start.plusDays(1).minusMillis(1);
								p = ((ComparableExpression) exp).goe(start).and(((ComparableExpression) exp).loe(end));
							}
							else {
								throw new IllegalArgumentException("Unsuported value type for BETWEEN_DATE_TIME: " + value.getClass());
							}
	
							break;
						default:
							if (exp instanceof BooleanExpression) {
								p = ((ComparableExpression) exp).eq(Boolean.valueOf(value.toString()));
							}
							else {
								p = ((ComparableExpression) exp).eq(value);
							}
							break;
					}
				}
				else {
					p = ((SimpleExpression) exp).eq(value);
				}
	
				q.where(p);
			}
		}
	}

	/**
	 * Gets the count.
	 *
	 * @param q the q
	 * @return the count
	 */
	protected long getCount(final JPAQuery q) {
		return q.count();
	}
}
