/**
 * 
 */
package sk.qbsw.core.security.oauth.dao;

import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.core.security.oauth.model.CSecurityToken;

/**
 * The Interface ISecurityTokenDao.
 *
 * @author podmajersky
 * @author Tomas Lauro
 * @version 1.3.0
 * @since 1.3.0
 */
public interface ISecurityTokenDao extends ICrudDao<Long, CSecurityToken>
{
	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the c security token
	 */
	CSecurityToken findByUserId (long userId);

	/**
	 * Find by token.
	 *
	 * @param token the token
	 * @param ip the ip
	 * @return the c security token
	 */
	CSecurityToken findByToken (String token, String ip);
}
