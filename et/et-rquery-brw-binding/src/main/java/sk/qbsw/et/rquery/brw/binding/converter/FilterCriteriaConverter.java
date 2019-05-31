package sk.qbsw.et.rquery.brw.binding.converter;

import com.querydsl.core.types.Predicate;

import sk.qbsw.et.rquery.brw.binding.mapper.FilterableMapper;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.criteria.FilterCriteria;
import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;

/**
 * The filter criteria converter.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface FilterCriteriaConverter
{
	/**
	 * Convert to predicate predicate.
	 *
	 * @param <F> the property type
	 * @param <C> the core property parameter
	 * @param filterCriteria the filter criteria
	 * @param mapping the mapping
	 * @param filterableMapper the filterable mapper
	 * @return the predicate
	 */
	<F extends Filterable, C extends CoreFilterable> Predicate convertToPredicate (FilterCriteria<F> filterCriteria, EntityConfiguration<C> mapping, FilterableMapper<F, C> filterableMapper);
}
