package sk.qbsw.security.organization.complex.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;

import java.util.List;

/**
 * The user account dao.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface UserAccountDao extends AccountDao<UserAccount>
{
	/**
	 * Find by unit code list.
	 *
	 * @param unitCode the unit code
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<UserAccount> findByUnitCode (String unitCode) throws CSecurityException;
}
