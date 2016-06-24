package sk.qbsw.core.browser.core.dao.jpa.qdsl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.BooleanOperation;
import com.mysema.query.types.expr.ComparableExpression;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.expr.SimpleExpression;
import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.path.NumberPath;

import sk.qbsw.core.browser.core.dao.IFilterDao;
import sk.qbsw.core.browser.core.model.CFilterParameter;
import sk.qbsw.core.browser.core.model.COrderParameter;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.EOperator;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The filter query dsl dao.
 *
 * @param <PK> the generic type
 * @param <T> the generic type
 * 
 * @author Marian Oravec
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.15.0
 */
public class AFilterQDslDao<PK, T extends IEntity<PK>>extends AQDslDao<PK, T> implements IFilterDao<PK, T>
{
	/* (non-Javadoc)
	 * @see sk.qbsw.core.browser.dao.IFilterDao#getColumnValuesList(com.mysema.query.types.expr.SimpleExpression, java.util.List, boolean)
	 */
	@Override
	@SuppressWarnings ({"unchecked", "rawtypes"})
	public List<T> getColumnValuesList (final SimpleExpression<T> column, final List<? extends CFilterParameter> fixedFilter, boolean ascendingOrder)
	{
		final JPAQuery query = this.createQuery();

		this.appendWherePart(query, fixedFilter);

		if (column instanceof ComparableExpressionBase)
		{
			final List<COrderParameter> orderSpecifiers = new ArrayList<>();

			orderSpecifiers.add(new COrderParameter((ComparableExpressionBase) column, ascendingOrder));

			this.appendOrder(query, orderSpecifiers);
		}

		return query.distinct().list(column);
	}

	/**
	 * Append where part.
	 *
	 * @param query the query
	 * @param wheres the wheres
	 */
	protected void appendWherePart (final JPAQuery query, final List<? extends CFilterParameter> wheres)
	{
		if ( (wheres != null) && !wheres.isEmpty())
		{
			for (final CFilterParameter where : wheres)
			{
				query.where(createWherePredicate(where));
			}
		}
	}

	/**
	 * Creates the where predicate.
	 *
	 * @param where the where
	 * @return the predicate
	 */
	@SuppressWarnings ({"unchecked", "rawtypes"})
	private Predicate createWherePredicate (final CFilterParameter where)
	{
		final Object value = where.getValue();
		final EOperator operator = where.getOperator();
		final SimpleExpression<?> expression = where.getExpression();

		if (expression instanceof BooleanOperation)
		{
			return (BooleanOperation) expression;
		}
		else if (EOperator.LIKE_IGNORE_CASE == operator && (expression instanceof StringExpression))
		{
			return ((StringExpression) expression).containsIgnoreCase((String) value);
		}
		else if ( (operator != null) && (expression instanceof ComparableExpression))
		{
			switch (operator)
			{
				case GT:
					return ((ComparableExpression) expression).gt((Comparable) value);
				case GOE:
					return ((ComparableExpression) expression).goe((Comparable) value);
				case LT:
					return ((ComparableExpression) expression).lt((Comparable<?>) value);
				case LOE:
					return ((ComparableExpression) expression).loe((Comparable<?>) value);
				case BETWEEN_DATE_TIME:
					return createWhereBetweenDateTimePredicate(value, expression);
				default:
					if (expression instanceof BooleanExpression)
					{
						return ((ComparableExpression) expression).eq(Boolean.valueOf(value.toString()));
					}
					else
					{
						return ((ComparableExpression) expression).eq(value);
					}
			}
		}
		else if ( (operator != null) && (expression instanceof NumberPath) && (value instanceof Number))
		{
			switch (operator)
			{
				case GT:
					return ((NumberPath) expression).gt((Number) value);
				case GOE:
					return ((NumberPath) expression).goe((Number) value);
				case LT:
					return ((NumberPath) expression).lt((Number) value);
				case LOE:
					return ((NumberPath) expression).loe((Number) value);
				default:
					return ((NumberPath) expression).eq(value);
			}
		}
		else
		{
			return ((SimpleExpression) expression).eq(value);
		}
	}

	/**
	 * Creates the where between date time predicate.
	 *
	 * @param value the value
	 * @param expression the expression
	 * @return the predicate
	 */
	@SuppressWarnings ({"unchecked", "rawtypes"})
	private Predicate createWhereBetweenDateTimePredicate (final Object value, final SimpleExpression<?> expression)
	{
		if (value instanceof DateTime)
		{
			DateTime valueDT = (DateTime) value;
			DateTime start = valueDT.withTimeAtStartOfDay();
			DateTime end = start.plusDays(1).minusMillis(1);
			return ((ComparableExpression) expression).goe(start).and( ((ComparableExpression) expression).loe(end));
		}
		else
		{
			throw new IllegalArgumentException("Unsuported value type for BETWEEN_DATE_TIME: " + value.getClass());
		}
	}

	/**
	 * Append order by part.
	 *
	 * @param q the q
	 * @param orderSpecifiers the order specifiers
	 */
	@SuppressWarnings ("rawtypes")
	protected void appendOrder (final JPAQuery q, final List<COrderParameter> orderSpecifiers)
	{
		if ( (orderSpecifiers != null) && !orderSpecifiers.isEmpty())
		{
			for (final COrderParameter orderSpecifier : orderSpecifiers)
			{
				final ComparableExpressionBase<? extends Comparable<?>> exp = orderSpecifier.getExpression();
				final ComparableExpressionBase expComp = exp;

				q.orderBy(orderSpecifier.isAscending() ? expComp.asc() : expComp.desc());
			}
		}
	}

}
