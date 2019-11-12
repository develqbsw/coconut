package sk.qbsw.et.rquery.core.model;

import java.util.List;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;

import sk.qbsw.et.rquery.core.exception.RQUnsupportedOperatorException;
import sk.qbsw.et.rquery.core.predicate.SinglePredicateBuilderImpl;

/**
 * The filtering operator.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public enum DefaultCoreOperator implements CoreOperator
{
	/**
	 * The eq.
	 */
	EQ
	{
		@Override
		public Predicate getNullValuePredicate (SimpleExpression path)
		{
			return path.isNull();
		}

		@Override
		public Predicate getTypePredicate (EntityPathBase path, List<Class<?>> types)
		{
			return path.instanceOf(types.get(0));
		}

		@Override
		public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
		{
			return path.eq(values.get(0));
		}

		@Override
		public Predicate getSimpleExpressionPredicate (SimpleExpression path, List<String> values)
		{
			return path.eq(values.get(0));
		}

		@Override
		public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
		{
			return path.eq(values.get(0));
		}
	},

	/**
	 * The ne.
	 */
	NE
	{
		@Override
		public Predicate getNullValuePredicate (SimpleExpression path)
		{
			return path.isNotNull();
		}

		@Override
		public Predicate getTypePredicate (EntityPathBase path, List<Class<?>> types)
		{
			return path.instanceOf(types.get(0)).not();
		}

		@Override
		public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
		{
			return path.ne(values.get(0));
		}

		@Override
		public Predicate getSimpleExpressionPredicate (SimpleExpression path, List<String> values)
		{
			return path.ne(values.get(0));
		}

		@Override
		public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
		{
			return path.ne(values.get(0));
		}
	},

	/**
	 * The gt.
	 */
	GT
	{
		@Override
		public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
		{
			return path.gt(values.get(0));
		}

		@Override
		public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
		{
			return path.gt(values.get(0));
		}
	},

	/**
	 * The goe.
	 */
	GOE
	{
		@Override
		public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
		{
			return path.goe(values.get(0));
		}

		@Override
		public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
		{
			return path.goe(values.get(0));
		}
	},

	/**
	 * The lt.
	 */
	LT
	{
		@Override
		public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
		{
			return path.lt(values.get(0));
		}

		@Override
		public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
		{
			return path.lt(values.get(0));
		}
	},

	/**
	 * The loe.
	 */
	LOE
	{
		@Override
		public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
		{
			return path.loe(values.get(0));
		}

		@Override
		public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
		{
			return path.loe(values.get(0));
		}
	},

	/**
	 * In core operator.
	 */
	IN
	{
		@Override
		public Predicate getTypePredicate (EntityPathBase path, List<Class<?>> types)
		{
			return path.instanceOfAny(types.toArray(new Class[0]));
		}

		@Override
		public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
		{
			return path.in(values);
		}

		@Override
		public Predicate getSimpleExpressionPredicate (SimpleExpression path, List<String> values)
		{
			return path.in(values);
		}

		@Override
		public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
		{
			return path.in(values);
		}
	},

	/**
	 * Not in core operator.
	 */
	NOT_IN
	{
		@Override
		public Predicate getTypePredicate (EntityPathBase path, List<Class<?>> types)
		{
			return path.instanceOfAny(types.toArray(new Class[0])).not();
		}

		@Override
		public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
		{
			return path.notIn(values);
		}

		@Override
		public Predicate getSimpleExpressionPredicate (SimpleExpression path, List<String> values)
		{
			return path.notIn(values);
		}

		@Override
		public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
		{
			return path.notIn(values);
		}
	},

	/**
	 * Like core operator.
	 */
	LIKE
	{
		@Override
		public Predicate getStringPredicate (StringPath path, String value)
		{
			return path.like(value);
		}
	},

	/**
	 * Not like core operator.
	 */
	NOT_LIKE
	{
		@Override
		public Predicate getStringPredicate (StringPath path, String value)
		{
			return path.notLike(value);
		}
	},

	/**
	 * The like ignore case.
	 */
	LIKE_IGNORE_CASE
	{
		@Override
		public Predicate getStringPredicate (StringPath path, String value)
		{
			return path.likeIgnoreCase(value);
		}
	}

	;


	@Override
	public Predicate getNullValuePredicate (SimpleExpression path)
	{
		return getUnsupportedPredicateType();
	}

	@Override
	public Predicate getTypePredicate (EntityPathBase path, List<Class<?>> types)
	{
		return getUnsupportedPredicateType();
	}

	@Override
	public Predicate getStringPredicate (StringPath path, String value)
	{
		return getUnsupportedPredicateType();
	}

	@Override
	public <T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values)
	{
		return getUnsupportedPredicateType();
	}

	@Override
	public Predicate getSimpleExpressionPredicate (SimpleExpression path, List<String> values)
	{
		return getUnsupportedPredicateType();
	}

	public <T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values)
	{
		return getUnsupportedPredicateType();
	}

	private final Predicate getUnsupportedPredicateType ()
	{
		throw new RQUnsupportedOperatorException(SinglePredicateBuilderImpl.UNSUPPORTED_OPERATOR_EXCEPTION_MESSAGE + this);
	}

}
