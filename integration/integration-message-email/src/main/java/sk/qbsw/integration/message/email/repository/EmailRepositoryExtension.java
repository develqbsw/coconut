package sk.qbsw.integration.message.email.repository;

import sk.qbsw.integration.message.email.model.domain.Email;

import java.util.List;

/**
 * The email repository extension.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public interface EmailRepositoryExtension
{
	/**
	 * Find by count and max attempt count list.
	 *
	 * @param count the count
	 * @param maxAttemptCount the max attempt count
	 * @return the list
	 */
	List<Email> findByCountAndMaxAttemptCount (long count, int maxAttemptCount);
}
