/**
 * 
 */
package sk.qbsw.core.security.oauth.dao;

import sk.qbsw.core.persistence.dao.ICrudDao;
import sk.qbsw.core.security.oauth.model.CSecurityToken;

/**
 * 
 * @author podmajersky
 * @version 1.3.0
 * @since 1.3.0
 *
 */
public interface ISecurityTokenDao extends ICrudDao<Long, CSecurityToken> {

	public CSecurityToken findByUserId (long userId);

	public CSecurityToken findByToken (String token, String ip);

}
