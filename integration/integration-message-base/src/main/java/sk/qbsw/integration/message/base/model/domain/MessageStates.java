package sk.qbsw.integration.message.base.model.domain;

/**
 * The message types.
 *
 * @author farkas.roman
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public enum MessageStates
{
	/**
	 * New message states.
	 */
	NEW,

	/**
	 * Sent message states.
	 */
	SENT,

	/**
	 * Error message states.
	 */
	ERROR,

	/**
	 * Network error message states.
	 */
	NETWORK_ERROR,

	/**
	 * Deleted message states.
	 */
	DELETED
}
