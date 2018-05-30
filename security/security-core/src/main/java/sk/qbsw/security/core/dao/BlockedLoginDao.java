package sk.qbsw.security.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.BlockedLogin;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * The blocked login dao.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.12.2
 */
public interface BlockedLoginDao extends IEntityDao<Long, BlockedLogin>
{
	/**
	 * Find the blocked login by login and ip.
	 *
	 * @param login the login (mandatory)
	 * @param ip the ip (optional)
	 * @return the authentication black list record
	 * @throws CSecurityException throws if the login is null
	 * @throws NonUniqueResultException there is no unique result
	 * @throws NoResultException there is no result
	 */
	BlockedLogin findOneByLoginAndIp (String login, String ip) throws CSecurityException, NonUniqueResultException, NoResultException;

	/**
	 * Count currently blocked by login and ip.
	 *
	 * @param login the login (mandatory)
	 * @param ip the ip (optional)
	 * @return the count
	 * @throws CSystemException throws if the login is null
	 */
	long countCurrentlyBlockedByLoginAndIp (String login, String ip) throws CSystemException;
}
