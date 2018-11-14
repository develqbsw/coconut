package sk.qbsw.security.organization.complex.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.organization.complex.core.dao.CXOUserDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;
import sk.qbsw.security.organization.complex.management.service.CXOUserService;

import java.util.List;

/**
 * The complex organization user service implementation.
 *
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
public class CXOUserServiceImpl extends AService implements CXOUserService
{
	private final CXOUserDao userDao;

	/**
	 * Instantiates a new User service.
	 *
	 * @param userDao the user dao
	 */
	public CXOUserServiceImpl (CXOUserDao userDao)
	{
		this.userDao = userDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOUser create (ActivityStates state)
	{
		CXOUser user = new CXOUser();
		user.setState(state);

		return userDao.update(user);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOUser read (Long id)
	{
		return userDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<CXOUser> findAll ()
	{
		return userDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOUser update (CXOUser user)
	{
		return userDao.update(user);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void activate (Long id)
	{
		CXOUser currentUser = userDao.findById(id);
		currentUser.setState(ActivityStates.ACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void inactivate (Long id)
	{
		CXOUser currentUser = userDao.findById(id);
		currentUser.setState(ActivityStates.INACTIVE);
	}
}
