package sk.qbsw.security.core.dao;

import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.model.domain.QUser;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user jpa dao.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class UserJpaDao extends AEntityQDslDao<Long, User> implements UserDao
{
	/**
	 * Instantiates a new User jpa dao.
	 */
	public UserJpaDao ()
	{
		super(QUser.user, Long.class);
	}
}
