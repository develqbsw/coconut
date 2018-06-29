package sk.qbsw.organization.management.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.core.dao.UserDao;
import sk.qbsw.organization.core.model.domain.User;

import java.util.List;

/**
 * The type User service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class UserServiceImpl extends AService implements UserService
{
	private final UserDao userDao;

	/**
	 * Instantiates a new User service.
	 *
	 * @param userDao the user dao
	 */
	public UserServiceImpl (UserDao userDao)
	{
		this.userDao = userDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public User create (ActivityStates state)
	{
		User user = new User();
		user.setState(state);

		return userDao.update(user);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public User read (Long id)
	{
		return userDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<User> findAll ()
	{
		return userDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public User update (User user)
	{
		return userDao.update(user);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void activate (Long id)
	{
		User currentUser = userDao.findById(id);
		currentUser.setState(ActivityStates.ACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void inactivate (Long id)
	{
		User currentUser = userDao.findById(id);
		currentUser.setState(ActivityStates.INACTIVE);
	}
}
