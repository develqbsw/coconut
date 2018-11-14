package sk.qbsw.integration.message.email.repository;

import sk.qbsw.core.persistence.dao.support.IFetchCapableQueryDslJpaRepository;
import sk.qbsw.integration.message.email.model.domain.Email;

/**
 * The email repository.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public interface EmailRepository extends IFetchCapableQueryDslJpaRepository<Email, Long>, EmailRepositoryExtension
{
}
