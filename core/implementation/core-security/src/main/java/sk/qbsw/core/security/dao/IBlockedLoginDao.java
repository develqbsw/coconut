package sk.qbsw.core.security.dao;

import java.io.Serializable;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CBlockedLogin;

/**
 * The Interface IBlockedLoginDao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
public interface IBlockedLoginDao extends Serializable, IEntityDao<Long, CBlockedLogin>
{
	/**
	 * Find the blocked login by login and ip.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @return the authentication black list record
	 * @throws CSystemException throws if the login is null
	 */
	public CBlockedLogin findByLoginAndIp (String login, String ip) throws CSystemException;

	/**
	 * Count currently blocked by login and ip;.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @return the count
	 * @throws CSystemException throws if the login is null
	 */
	public long countCurrentlyBlocked (String login, String ip) throws CSystemException;
}
