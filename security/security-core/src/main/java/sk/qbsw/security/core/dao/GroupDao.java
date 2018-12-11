package sk.qbsw.security.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.GroupTypes;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * The group dao.
 *
 * @author rosenberg
 * @author lacko
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.0.0
 */
public interface GroupDao extends IEntityDao<Long, Group>
{
	/**
	 * Find by type list.
	 *
	 * @param type the type
	 * @return the list
	 */
	List<Group> findByType (GroupTypes type);

	/**
	 * Find one by code group.
	 *
	 * @param code the code
	 * @return the group
	 * @throws CSecurityException the c security exception
	 */
	Group findOneByCode (String code) throws CSecurityException;

	/**
	 * Find one by code and unit group.
	 *
	 * @param code the code
	 * @param unit the unit
	 * @return the group
	 * @throws CSecurityException the c security exception
	 */
	Group findOneByCodeAndUnit (String code, Unit unit) throws CSecurityException;

	/**
	 * Find by unit list.
	 *
	 * @param unit the unit
	 * @return the list
	 */
	List<Group> findByUnit (Unit unit);

	/**
	 * Find by unit and user list.
	 *
	 * @param unit the unit
	 * @param accountId the account id
	 * @return the list
	 */
	List<Group> findByUnitAndAccountId (Unit unit, Long accountId);
}
