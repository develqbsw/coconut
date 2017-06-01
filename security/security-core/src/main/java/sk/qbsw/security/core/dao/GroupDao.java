/**
 * 
 */
package sk.qbsw.security.core.dao;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;

import java.util.List;

/**
 * The Interface GroupDao.
 *
 * @author rosenberg
 * @author lacko
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
public interface GroupDao extends IEntityDao<Long, Group>
{
	/** The I d_ or g_ admin. */
	public static Long ID_ORG_ADMIN = 2l;

	/** The I d_ sy s_ admin. */
	public static Long ID_SYS_ADMIN = 1l;

	/**
	 * Find by flag system.
	 *
	 * @param flagSystem the flag system (mandatory)
	 * @return the list
	 */
	List<Group> findByFlagSystem (boolean flagSystem);

	/**
	 * Find groups by code ordered by code asc.
	 *
	 * @param code the code (mandatory)
	 * @return the list
	 * @deprecated the code is unique, use {@link GroupDao#findOneByCode(String)}
	 */
	List<Group> findByCode (String code);

	/**
	 * Find group by code.
	 *
	 * @param code the code (mandatory)
	 * @return the group with defined code
	 * @throws CSecurityException the code is missing
	 */
	Group findOneByCode (String code) throws CSecurityException;

	/**
	 * Find groups by code and unit - if the unit is null the only group with empty units list is returned. The roles and units for group are fetched.
	 *
	 * @param code the code of requested group (mandatory)
	 * @param unit the unit of group (optional)
	 * @return the list of groups
	 * @throws CSecurityException the code is missing
	 * @deprecated the code is unique
	 */
	List<Group> findByCodeAndUnit (String code, Unit unit) throws CSecurityException;

	/**
	 * Find group by code and unit - if the unit is null the only group with empty units list is returned. The roles and units for group are fetched.
	 *
	 * @param code the code of requested group (mandatory)
	 * @param unit the unit of group (optional)
	 * @return the group
	 * @throws CSecurityException the code is missing
	 */
	Group findOneByCodeAndUnit (String code, Unit unit) throws CSecurityException;

	/**
	 * Find groups by unit.
	 *
	 * @param unit the unit of group (optional)
	 * @return the list of groups
	 */
	List<Group> findByUnit (Unit unit);

	/**
	 * find groups by unit and user (if not null).
	 *
	 * @param unit the unit (optional)
	 * @param user the user (optional)
	 * @return the list
	 */
	List<Group> findByUnitAndUser (Unit unit, User user);
}
