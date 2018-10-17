package sk.qbsw.security.organization.complex.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;

import java.util.List;

/**
 * The complex organization user dao.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface CXOUserDao extends IEntityDao<Long, CXOUser>
{
	/**
	 * Find by unit list.
	 *
	 * @param unitCode the unit code
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<CXOUser> findByUnit (String unitCode) throws CSecurityException;
}
