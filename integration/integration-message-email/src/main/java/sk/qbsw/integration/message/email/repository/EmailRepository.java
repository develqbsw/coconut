package sk.qbsw.integration.message.email.repository;

import sk.qbsw.core.persistence.dao.support.IFetchCapableQueryDslJpaRepository;
import sk.qbsw.integration.message.email.model.domain.Email;

/**
 * The email repository.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface EmailRepository extends IFetchCapableQueryDslJpaRepository<Email, Long>, EmailRepositoryExtension
{
}
