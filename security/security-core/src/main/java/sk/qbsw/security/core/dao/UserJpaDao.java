package sk.qbsw.security.core.dao;

import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.model.domain.QUser;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user jpa dao.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
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
