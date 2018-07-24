package sk.qbsw.organization.complex.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.organization.complex.core.model.domain.User;

import java.util.List;

/**
 * The interface User dao.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public interface UserDao extends IEntityDao<Long, User>
{
	/**
	 * Find by unit list.
	 *
	 * @param unit the unit
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<User> findByUnit (Unit unit) throws CSecurityException;
}
