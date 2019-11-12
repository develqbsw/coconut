package sk.qbsw.et.rquery.core.model;

import java.util.List;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;

/**
 * The filtering operator.
 *
 * @author David Durcak
 * @version 2.2.3
 * @since 2.2.3
 */
public interface CoreOperator
{
	/**
	 * Get predicate for given path in case of null value
	 *
	 * @param path the path
	 * @return the predicate
	 */
	Predicate getNullValuePredicate (SimpleExpression path);

	/**
	 * Get predicate for given path in case of type values
	 *
	 * @param path the path
	 * @param types the types
	 * @return the predicate
	 */
	Predicate getTypePredicate (EntityPathBase path, List<Class<?>> types);

	/**
	 * Get predicate for given path in case of string value
	 *
	 * @param path the path
	 * @param value the value
	 * @return the predicate
	 */
	Predicate getStringPredicate (StringPath path, String value);

	/**
	 * Get predicate for given string path in case of number value
	 *
	 * @param path the path
	 * @param values the values
	 * @return the predicate
	 */
	<T extends Number & Comparable<?>> Predicate getNumberPredicate (NumberPath<T> path, List<T> values);

	/**
	 * Get predicate for given simple expression path in case of string values
	 *
	 * @param path the path
	 * @param values the values
	 * @return the predicate
	 */
	Predicate getSimpleExpressionPredicate (SimpleExpression path, List<String> values);

	/**
	 * Get predicate for given path in case of comparable value
	 *
	 * @param path the path
	 * @param values the values
	 * @return the predicate
	 */
	<T extends Comparable, E extends ComparableExpression<T>> Predicate getComparablePredicate (E path, List<T> values);
}