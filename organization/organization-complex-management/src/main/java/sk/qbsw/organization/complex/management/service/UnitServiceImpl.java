package sk.qbsw.organization.complex.management.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.complex.core.dao.UnitDao;
import sk.qbsw.organization.complex.core.dao.UserDao;
import sk.qbsw.organization.complex.core.model.domain.Organization;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.organization.complex.core.model.domain.User;

import java.util.List;

/**
 * The type Unit service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class UnitServiceImpl extends AService implements UnitService
{
	private final UnitDao unitDao;

	private final UserDao userDao;

	/**
	 * Instantiates a new Unit service.
	 *
	 * @param unitDao the unit dao
	 * @param userDao the user dao
	 */
	public UnitServiceImpl (UnitDao unitDao, UserDao userDao)
	{
		this.unitDao = unitDao;
		this.userDao = userDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Unit create (String code, String name, Organization organization, ActivityStates state)
	{
		Unit unit = new Unit();
		unit.setCode(code);
		unit.setName(name);
		unit.setOrganization(organization);
		unit.setState(state);

		return unitDao.update(unit);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Unit read (Long id)
	{
		return unitDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Unit> findAll ()
	{
		return unitDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Unit> findByUser (User user) throws CSecurityException
	{
		return unitDao.findByUserId(user.getId());
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Unit update (Unit unit)
	{
		return unitDao.update(unit);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void activate (Long id)
	{
		Unit currentUnit = unitDao.findById(id);
		currentUnit.setState(ActivityStates.ACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void inactivate (Long id)
	{
		Unit currentUnit = unitDao.findById(id);
		currentUnit.setState(ActivityStates.INACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void addUser (Long id, Long userId)
	{
		Unit unit = unitDao.findWithUsers(id);
		unit.getUsers().add(userDao.findById(userId));
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void removeUser (Long id, Long userId)
	{
		Unit unit = unitDao.findWithUsers(id);
		unit.getUsers().removeIf(user -> user.getId().equals(userId));
	}
}
