package sk.qbsw.security.organization.complex.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.organization.complex.core.dao.CXOUnitDao;
import sk.qbsw.security.organization.complex.core.dao.CXOUserDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;
import sk.qbsw.security.organization.complex.management.service.CXOUnitService;

import java.util.List;

/**
 * The complex organization unit service implementation.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public class CXOUnitServiceImpl extends AService implements CXOUnitService
{
	private final CXOUnitDao unitDao;

	private final CXOUserDao userDao;

	/**
	 * Instantiates a new Unit service.
	 *
	 * @param unitDao the unit dao
	 * @param userDao the user dao
	 */
	public CXOUnitServiceImpl (CXOUnitDao unitDao, CXOUserDao userDao)
	{
		this.unitDao = unitDao;
		this.userDao = userDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOUnit create (String code, String name, CXOOrganization organization, ActivityStates state)
	{
		CXOUnit unit = new CXOUnit();
		unit.setCode(code);
		unit.setName(name);
		unit.setOrganization(organization);
		unit.setState(state);

		return unitDao.update(unit);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOUnit read (Long id)
	{
		return unitDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<CXOUnit> findAll ()
	{
		return unitDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<CXOUnit> findByUser (CXOUser user) throws CSecurityException
	{
		return unitDao.findByUserId(user.getId());
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOUnit update (CXOUnit unit)
	{
		return unitDao.update(unit);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void activate (Long id)
	{
		CXOUnit currentUnit = unitDao.findById(id);
		currentUnit.setState(ActivityStates.ACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void inactivate (Long id)
	{
		CXOUnit currentUnit = unitDao.findById(id);
		currentUnit.setState(ActivityStates.INACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void addUser (Long id, Long userId)
	{
		CXOUnit unit = unitDao.findWithUsers(id);
		unit.getUsers().add(userDao.findById(userId));
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void removeUser (Long id, Long userId)
	{
		CXOUnit unit = unitDao.findWithUsers(id);
		unit.getUsers().removeIf(user -> user.getId().equals(userId));
	}
}
