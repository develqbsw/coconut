package sk.qbsw.et.rquery.core.predicate;

import com.querydsl.core.types.Predicate;

import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.core.model.CoreOperator;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.exception.RQBusinessException;

/**
 * The default comparison predicate builder implementation.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface ComparisonPredicateBuilder
{
	/**
	 * Build predicate predicate.
	 *
	 * @param <F> the type parameter
	 * @param property the property
	 * @param value the value
	 * @param operator the operator
	 * @param mapping the mapping
	 * @return the predicate
	 * @throws RQBusinessException the rq business exception
	 */
	<F extends CoreFilterable> Predicate buildPredicate (F property, String value, CoreOperator operator, EntityConfiguration<F> mapping) throws RQBusinessException;
}
