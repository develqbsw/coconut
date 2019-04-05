package sk.qbsw.et.rquery.core.configuration.expression;

import sk.qbsw.et.rquery.core.model.CoreFilterableType;

import java.util.HashMap;
import java.util.Map;

/**
 * The entity type class pair builder.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public class EntityTypeDefinitionsBuilder
{
	private final Map<CoreFilterableType, Class<?>> pairs = new HashMap<>();

	/**
	 * Add pair entity type definitions builder.
	 *
	 * @param type the type
	 * @param clazz the clazz
	 * @return the entity type definitions builder
	 */
	public EntityTypeDefinitionsBuilder addPair (CoreFilterableType type, Class<?> clazz)
	{
		pairs.put(type, clazz);
		return this;
	}

	/**
	 * Build pairs map.
	 *
	 * @return the map
	 */
	public Map<CoreFilterableType, Class<?>> buildPairs ()
	{
		return pairs;
	}
}
