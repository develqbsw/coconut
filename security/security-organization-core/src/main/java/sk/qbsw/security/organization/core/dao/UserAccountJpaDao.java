package sk.qbsw.security.organization.core.dao;

import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.organization.core.model.domain.QUserAccount;
import sk.qbsw.security.organization.core.model.domain.UserAccount;

/**
 * The type User account jpa dao.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class UserAccountJpaDao extends AEntityQDslDao<Long, UserAccount> implements UserAccountDao
{
	/**
	 * Instantiates a new User account jpa dao.
	 */
	public UserAccountJpaDao ()
	{
		super(QUserAccount.userAccount, Long.class);
	}
}
