package sk.qbsw.integration.message.email.repository;

import sk.qbsw.integration.message.email.model.domain.Email;

import java.util.List;

/**
 * The email repository extension.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
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
