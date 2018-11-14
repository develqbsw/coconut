package sk.qbsw.security.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * Service for group management.
 *
 * @author Michal Lacko
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.0.0
 */
public class GroupServiceImpl extends AService implements GroupService
{
	private final GroupDao groupDao;

	/**
	 * Instantiates a new Group service.
	 *
	 * @param groupDao the group dao
	 */
	public GroupServiceImpl (GroupDao groupDao)
	{
		this.groupDao = groupDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Group read (Long id)
	{
		return groupDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Group findByCode (String code) throws CSecurityException
	{
		return groupDao.findOneByCode(code);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Group> findByUnit (Unit unit)
	{
		return groupDao.findByUnit(unit);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Group> findByUnitAndAccount (Unit unit, Account account)
	{
		return groupDao.findByUnitAndAccount(unit, account);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Group findByCodeAndUnit (String code, Unit unit) throws CSecurityException
	{
		return groupDao.findOneByCodeAndUnit(code, unit);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Group> findAll ()
	{
		return groupDao.findAll();
	}
}
