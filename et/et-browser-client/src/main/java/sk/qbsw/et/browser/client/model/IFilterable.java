package sk.qbsw.et.browser.client.model;

import java.io.Serializable;

/**
 * The filterable item - the enum with entity variables as values.
 *
 * @author Tomas Lauro
 * 
 * @since 1.16.0
 * @version 1.16.0
 */
public interface IFilterable extends Serializable
{
	/**
	 * Gets the variable name.
	 *
	 * @return the variable name
	 */
	String getVariableName ();
}
