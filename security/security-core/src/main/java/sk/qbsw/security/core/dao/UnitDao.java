package sk.qbsw.security.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * The unit dao.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
public interface UnitDao extends IEntityDao<Long, Unit>
{
	/**
	 * Find one by name unit.
	 *
	 * @param name the name
	 * @return the unit
	 * @throws CSecurityException the c security exception
	 */
	Unit findOneByName (String name) throws CSecurityException;

	/**
	 * Find by account id list.
	 *
	 * @param accountId the account id
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<Unit> findByAccountId (Long accountId) throws CSecurityException;
}
