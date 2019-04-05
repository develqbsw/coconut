package sk.qbsw.et.rquery.core.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.exception.RQBusinessException;
import sk.qbsw.et.rquery.core.exception.RQUnsupportedOperatorException;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreOperator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

/**
 * The single predicate builder.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface SinglePredicateBuilder
{
	/**
	 * Build type predicate predicate.
	 *
	 * @param <F> the type parameter
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @param property the property
	 * @param mapping the mapping
	 * @return the predicate
	 * @throws RQBusinessException the rq business exception
	 */
	<F extends CoreFilterable> Predicate buildTypePredicate (EntityPathBase path, String value, CoreOperator operator, F property, EntityConfiguration<F> mapping) throws RQBusinessException;

	/**
	 * Build enum predicate predicate.
	 *
	 * @param <F> the type parameter
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @param property the property
	 * @param mapping the mapping
	 * @return the predicate
	 * @throws RQBusinessException the rq business exception
	 */
	<F extends CoreFilterable> Predicate buildEnumPredicate (EnumPath path, String value, CoreOperator operator, F property, EntityConfiguration<F> mapping) throws RQBusinessException;

	/**
	 * Build boolean predicate predicate.
	 *
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @return the predicate
	 * @throws RQUnsupportedOperatorException the rq unsupported operator exception
	 */
	Predicate buildBooleanPredicate (BooleanPath path, String value, CoreOperator operator) throws RQUnsupportedOperatorException;

	/**
	 * Build date predicate predicate.
	 *
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @return the predicate
	 * @throws RQUnsupportedOperatorException the rq unsupported operator exception
	 */
	Predicate buildDatePredicate (DatePath<LocalDate> path, String value, CoreOperator operator) throws RQUnsupportedOperatorException;

	/**
	 * Build time predicate predicate.
	 *
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @return the predicate
	 * @throws RQUnsupportedOperatorException the rq unsupported operator exception
	 */
	Predicate buildTimePredicate (TimePath<LocalTime> path, String value, CoreOperator operator) throws RQUnsupportedOperatorException;

	/**
	 * Build date time predicate predicate.
	 *
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @return the predicate
	 * @throws RQUnsupportedOperatorException the rq unsupported operator exception
	 */
	Predicate buildDateTimePredicate (DateTimePath<OffsetDateTime> path, String value, CoreOperator operator) throws RQUnsupportedOperatorException;

	/**
	 * Build string predicate predicate.
	 *
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @return the predicate
	 * @throws RQUnsupportedOperatorException the rq unsupported operator exception
	 */
	Predicate buildStringPredicate (StringPath path, String value, CoreOperator operator) throws RQUnsupportedOperatorException;

	/**
	 * Build number predicate predicate.
	 *
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @return the predicate
	 * @throws RQUnsupportedOperatorException the rq unsupported operator exception
	 */
	Predicate buildNumberPredicate (NumberPath<Long> path, String value, CoreOperator operator) throws RQUnsupportedOperatorException;

	/**
	 * Build simple expression predicate predicate.
	 *
	 * @param path the path
	 * @param value the value
	 * @param operator the operator
	 * @return the predicate
	 * @throws RQUnsupportedOperatorException the rq unsupported operator exception
	 */
	Predicate buildSimpleExpressionPredicate (SimpleExpression path, String value, CoreOperator operator) throws RQUnsupportedOperatorException;
}
