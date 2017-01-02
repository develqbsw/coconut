package sk.qbsw.et.browser.api.mapping;

import java.util.HashMap;
import java.util.Map;

import sk.qbsw.et.browser.client.model.IFilterableType;

/**
 * The browser entity type class pair builder.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.1
 * @since 1.16.1
 */
public class CBrwEntityTypeDefinitionsBuilder
{
	/** The pairs. */
	private final Map<IFilterableType, Class<?>> pairs = new HashMap<>();

	/**
	 * Adds the pair.
	 *
	 * @param type the type
	 * @param clazz the clazz
	 * @return the c brw entity type class pair builder
	 */
	public CBrwEntityTypeDefinitionsBuilder addPair (IFilterableType type, Class<?> clazz)
	{
		pairs.put(type, clazz);
		return this;
	}

	/**
	 * Builds the pairs.
	 *
	 * @return the map
	 */
	public Map<IFilterableType, Class<?>> buildPairs ()
	{
		return pairs;
	}
}
