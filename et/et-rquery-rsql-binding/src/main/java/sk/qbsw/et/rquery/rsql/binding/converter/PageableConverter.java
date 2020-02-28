package sk.qbsw.et.rquery.rsql.binding.converter;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import sk.qbsw.et.rquery.core.configuration.EntityConfiguration;
import sk.qbsw.et.rquery.core.model.CoreFilterable;
import sk.qbsw.et.rquery.rsql.binding.mapper.FilterableMapper;
import sk.qbsw.et.rquery.rsql.binding.model.OffsetPageable;

/**
 * The pageable converter.
 *
 * @author Tomas Lauro
 * @version 2.3.1
 * @since 2.2.0
 */
public interface PageableConverter
{
	/**
	 * Convert to pageable pageable.
	 *
	 * @param <C> the type parameter
	 * @param pageable the pageable
	 * @param configuration the configuration
	 * @param mapper the mapper
	 * @return the pageable
	 */
	<C extends CoreFilterable> Pageable convertToPageable (Pageable pageable, EntityConfiguration<C> configuration, FilterableMapper<C> mapper);

	/**
	 * Convert to pageable pageable.
	 *
	 * @param <C> the type parameter
	 * @param pageable the offset pageable
	 * @param configuration the configuration
	 * @param mapper the mapper
	 * @return the pageable
	 */
	<C extends CoreFilterable> Pageable convertToPageable (OffsetPageable pageable, EntityConfiguration<C> configuration, FilterableMapper<C> mapper);

	/**
	 * Convert to sort sort.
	 *
	 * @param <C> the type parameter
	 * @param sort the sort
	 * @param configuration the configuration
	 * @param mapper the mapper
	 * @return the sort
	 */
	<C extends CoreFilterable> Sort convertToSort (Sort sort, EntityConfiguration<C> configuration, FilterableMapper<C> mapper);
}
