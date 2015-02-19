package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

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
public class CGroupService extends AService implements IGroupService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IGroupService#getAll()
	 */
	@Transactional (readOnly = true)
	public List<CGroup> getAll ()
	{
		return groupDao.findAll();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IGroupService#getByCode(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public List<CGroup> getByCode (String code)
	{
		return groupDao.findByCode(code);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IGroupService#getByUnit(sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	public List<CGroup> getByUnit (CUnit unit)
	{
		return groupDao.findByUnit(unit);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IGroupService#getByUnitUser(sk.qbsw.core.security.model.domain.CUnit, sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	public List<CGroup> getByUnitUser (CUnit unit, CUser user)
	{
		return groupDao.findByUnitAndUser(unit, user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IGroupService#getByCodeAndUnit(java.lang.String, sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	public List<CGroup> getByCodeAndUnit (String code, CUnit unit)
	{
		return groupDao.findByCodeAndUnit(code, unit);
	}
}
