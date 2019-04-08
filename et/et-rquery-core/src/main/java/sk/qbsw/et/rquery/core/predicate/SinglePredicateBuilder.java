package sk.qbsw.et.rquery.core.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreOperator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

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
	 * @param values the values
	 * @param operator the operator
	 * @param property the property
	 * @param mapping the mapping
	 * @return the predicate
	 */
	<F extends CoreFilterable> Predicate buildTypePredicate (EntityPathBase path, List<String> values, CoreOperator operator, F property, EntityConfiguration<F> mapping);

	/**
	 * Build enum predicate predicate.
	 *
	 * @param <F> the type parameter
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @param property the property
	 * @param mapping the mapping
	 * @return the predicate
	 */
	<F extends CoreFilterable> Predicate buildEnumPredicate (EnumPath path, List<String> values, CoreOperator operator, F property, EntityConfiguration<F> mapping);

	/**
	 * Build boolean predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildBooleanPredicate (BooleanPath path, List<String> values, CoreOperator operator);

	/**
	 * Build date predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildDatePredicate (DatePath<LocalDate> path, List<String> values, CoreOperator operator);

	/**
	 * Build time predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildTimePredicate (TimePath<LocalTime> path, List<String> values, CoreOperator operator);

	/**
	 * Build date time predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildDateTimePredicate (DateTimePath<OffsetDateTime> path, List<String> values, CoreOperator operator);

	/**
	 * Build string predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildStringPredicate (StringPath path, List<String> values, CoreOperator operator);

	/**
	 * Build short predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildShortPredicate (NumberPath<Short> path, List<String> values, CoreOperator operator);

	/**
	 * Build byte predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildBytePredicate (NumberPath<Byte> path, List<String> values, CoreOperator operator);

	/**
	 * Build integer predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildIntegerPredicate (NumberPath<Integer> path, List<String> values, CoreOperator operator);

	/**
	 * Build long predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildLongPredicate (NumberPath<Long> path, List<String> values, CoreOperator operator);

	/**
	 * Build float predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildFloatPredicate (NumberPath<Float> path, List<String> values, CoreOperator operator);

	/**
	 * Build double predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildDoublePredicate (NumberPath<Double> path, List<String> values, CoreOperator operator);

	/**
	 * Build big decimal predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildBigDecimalPredicate (NumberPath<BigDecimal> path, List<String> values, CoreOperator operator);

	/**
	 * Build simple expression predicate predicate.
	 *
	 * @param path the path
	 * @param values the values
	 * @param operator the operator
	 * @return the predicate
	 */
	Predicate buildSimpleExpressionPredicate (SimpleExpression path, List<String> values, CoreOperator operator);
}
