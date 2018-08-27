package sk.qbsw.core.base.configuration;

import java.io.Serializable;

/**
 * The database schemas.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class DatabaseSchemas implements Serializable
{
	private static final long serialVersionUID = 8298843471114130181L;

	/**
	 * The constant SECURITY.
	 */
	public static final String SECURITY = "sec";

	/**
	 * The constant ORGANIZATION.
	 */
	public static final String ORGANIZATION = "org";

	/**
	 * The constant MESSAGING.
	 */
	public static final String MESSAGING = "msg";
}
