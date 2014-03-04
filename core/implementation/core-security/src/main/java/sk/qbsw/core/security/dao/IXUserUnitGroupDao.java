package sk.qbsw.core.security.dao;

import java.io.Serializable;
import java.util.List;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CGroup;
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
	 * Find all
	 * 
	 * @param user - optional
	 * @param unit - optional
	 * @param group - optional
	 * @return
	 */
	List<CXUserUnitGroup> findAll (CUser user, CUnit unit, CGroup group);

}
