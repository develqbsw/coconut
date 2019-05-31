package sk.qbsw.et.rquery.rsql.binding.mapper;

import sk.qbsw.et.rquery.core.model.CoreFilterable;

/**
 * The filterable mapper.
 *
 * @param <C> the core property type
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface FilterableMapper<C extends CoreFilterable>
{
	/**
	 * Map normalized selector to core filterable c.
	 *
	 * @param selector the selector
	 * @return the c
	 */
	default C mapNormalizedSelectorToCoreFilterable (String selector)
	{
		String normalizedSelector = selector.replaceAll("[^\\w\\s]", "_").toUpperCase();
		return mapToCoreFilterable(normalizedSelector);
	}

	/**
	 * Map to core filterable c.
	 *
	 * @param selector the selector
	 * @return the c
	 */
	C mapToCoreFilterable (String selector);
}
