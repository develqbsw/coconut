package sk.qbsw.security.organization.complex.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;

import java.util.List;
/**
 * The interface User account dao.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface UserAccountDao extends IEntityDao<Long, UserAccount>
{
	public List<UserAccount> findByUnitCode (String unitCode) throws CSecurityException;
}
