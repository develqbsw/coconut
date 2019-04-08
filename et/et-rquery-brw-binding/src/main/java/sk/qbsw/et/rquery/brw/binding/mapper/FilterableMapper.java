package sk.qbsw.et.rquery.brw.binding.mapper;

import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.core.model.CoreFilterable;

/**
 * The filterable mapper.
 *
 * @param <F> the property type
 * @param <C> the core property type
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface FilterableMapper<F extends Filterable, C extends CoreFilterable>
{
	/**
	 * Map to core filterable c.
	 *
	 * @param filterable the filterable
	 * @return the c
	 */
	C mapToCoreFilterable (F filterable);
}
