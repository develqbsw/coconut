package sk.qbsw.integration.message.email.configuration.properties;

import sk.qbsw.integration.message.configuration.ProcessMessageTaskConfiguration;

/**
 * The process email task configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface ProcessEmailTaskConfiguration extends ProcessMessageTaskConfiguration
{
	/**
	 * Gets max send count.
	 *
	 * @return the max send count
	 */
	Integer getMaxSendCount ();

	/**
	 * Gets max attempt count.
	 *
	 * @return the max attempt count
	 */
	Integer getMaxAttemptCount ();
}
