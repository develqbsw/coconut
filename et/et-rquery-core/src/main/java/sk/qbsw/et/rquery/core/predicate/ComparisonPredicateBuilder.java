package sk.qbsw.et.rquery.core.predicate;

import com.querydsl.core.types.Predicate;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreOperator;

import java.util.List;

/**
 * The default comparison predicate builder implementation.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface ComparisonPredicateBuilder
{
	/**
	 * Build predicate predicate.
	 *
	 * @param <F> the type parameter
	 * @param property the property
	 * @param values the values
	 * @param operator the operator
	 * @param mapping the mapping
	 * @return the predicate
	 */
	<F extends CoreFilterable> Predicate buildPredicate (F property, List<String> values, CoreOperator operator, EntityConfiguration<F> mapping);
}
