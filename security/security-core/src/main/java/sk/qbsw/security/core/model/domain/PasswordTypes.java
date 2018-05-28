package sk.qbsw.security.core.model.domain;

/**
 * The password type.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.12.0
 */
public enum PasswordTypes
{
	/**
	 * The durable password.
	 */
	DURABLE,

	/**
	 * The first time password - to be changed.
	 */
	FIRST_TIME
}
