package sk.qbsw.security.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.management.service.GroupService;

/**
 * Service for group management.
 *
 * @author Michal Lacko
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Service ("cGroupService")
public class GroupServiceImpl extends AService implements GroupService
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

	/**
	 * Gets the role group.
	 *
	 * @param id the pk id
	 * @return the role group
	 * @see sk.qbsw.security.core.core.management.service.ISecurityService#read(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public Group read (Long id)
	{
		return groupDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IGroupService#getAll()
	 */
	@Transactional (readOnly = true)
	public List<Group> getAll ()
	{
		return groupDao.findAll();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IGroupService#getByCode(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public List<Group> getByCode (String code)
	{
		return groupDao.findByCode(code);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IGroupService#getByUnit(sk.qbsw.security.core.core.model.domain.CUnit)
	 */
	@Override
	public List<Group> getByUnit (Unit unit)
	{
		return groupDao.findByUnit(unit);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IGroupService#getByUnitUser(sk.qbsw.security.core.core.model.domain.CUnit, sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	public List<Group> getByUnitUser (Unit unit, User user)
	{
		return groupDao.findByUnitAndUser(unit, user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IGroupService#getByCodeAndUnit(java.lang.String, sk.qbsw.security.core.core.model.domain.CUnit)
	 */
	@Override
	public List<Group> getByCodeAndUnit (String code, Unit unit) throws CSecurityException
	{
		return groupDao.findByCodeAndUnit(code, unit);
	}
}
