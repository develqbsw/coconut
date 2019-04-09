package sk.qbsw.et.rquery.core.configuration;

import sk.qbsw.et.rquery.core.model.CoreFilterable;

/**
 * The entity configurable.
 *
 * @param <F> the property type
 * @author Jana Branisova
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface EntityConfigurable<F extends CoreFilterable>
{
	/**
	 * Create builder entity configuration builder.
	 *
	 * @return the entity configuration builder
	 */
	EntityConfigurationBuilder<F> createBuilder ();
}
