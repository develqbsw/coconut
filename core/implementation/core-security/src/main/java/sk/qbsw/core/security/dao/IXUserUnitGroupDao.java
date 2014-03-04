package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.CXUserUnitGroup;

/**
 * DAO for cross entities between user, unit and group
 * 
 * @author farkas.roman
 * @Since 1.7.0
 */
public interface IXUserUnitGroupDao extends Serializable, IEntityDao<Long, CXUserUnitGroup>
{

	/**
	 * Find all cross entities by user, not in unit
	 * 
	 * @param user - optional
	 * @param unit - optional
	 * @return
	 */
	List<CXUserUnitGroup> findByUserNotInUnit (CUser user, CUnit unit);

}
