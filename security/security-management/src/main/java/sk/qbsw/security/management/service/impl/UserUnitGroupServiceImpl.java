package sk.qbsw.security.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.UserUnitGroupDao;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.model.domain.UserUnitGroup;
import sk.qbsw.security.management.service.UserUnitGroupService;

/**
 * Service for UserUnitGroup entity operations 
 *
 * @author farkas.roman
 * @version 1.7.0
 * @since 1.7.0
 */
@Service ("xUserUnitGroupService")
public class UserUnitGroupServiceImpl extends AService implements UserUnitGroupService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The XUserUnitGroup dao. */
	@Autowired
	private UserUnitGroupDao xuugDao;

	@Override
	@Transactional (readOnly = true)
	public List<UserUnitGroup> getAll (User user, Unit unit, Group group)
	{
		return xuugDao.findByUserAndUnitAndGroup(user, unit, group);
	}

	@Override
	@Transactional (readOnly = true)
	public List<UserUnitGroup> getAllByUser (User user)
	{
		return getAll(user, null, null);
	}

	@Override
	@Transactional
	public void save (UserUnitGroup xuug)
	{
		xuugDao.update(xuug);
	}

	@Override
	@Transactional
	public void saveAll (List<UserUnitGroup> xuugList)
	{
		for (UserUnitGroup xuug : xuugList)
		{
			xuugDao.update(xuug);
		}
	}

	@Override
	@Transactional
	public void remove (UserUnitGroup xuug)
	{
		if(xuug == null || xuug.getId() == null)
		{
			throw new IllegalArgumentException("input object to remove cannot be null and must have id");
		}
		
		xuug = xuugDao.findById(xuug.getId());
		
		xuugDao.remove(xuug);
	}
}