package sk.qbsw.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.dao.IXUserUnitGroupDao;
import sk.qbsw.security.model.domain.CGroup;
import sk.qbsw.security.model.domain.CUnit;
import sk.qbsw.security.model.domain.CUser;
import sk.qbsw.security.model.domain.CXUserUnitGroup;

/**
 * Service for CXUserUnitGroup entity operations 
 *
 * @author farkas.roman
 * @version 1.7.0
 * @since 1.7.0
 */
@Service ("xUserUnitGroupService")
public class CXUserUnitGroupService extends AService implements IXUserUnitGroupService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The XUserUnitGroup dao. */
	@Autowired
	private IXUserUnitGroupDao xuugDao;

	@Override
	@Transactional (readOnly = true)
	public List<CXUserUnitGroup> getAll (CUser user, CUnit unit, CGroup group)
	{
		return xuugDao.findByUserAndUnitAndGroup(user, unit, group);
	}

	@Override
	@Transactional (readOnly = true)
	public List<CXUserUnitGroup> getAllByUser (CUser user)
	{
		return getAll(user, null, null);
	}

	@Override
	@Transactional
	public void save (CXUserUnitGroup xuug)
	{
		xuugDao.update(xuug);
	}

	@Override
	@Transactional
	public void saveAll (List<CXUserUnitGroup> xuugList)
	{
		for (CXUserUnitGroup xuug : xuugList)
		{
			xuugDao.update(xuug);
		}
	}

	@Override
	@Transactional
	public void remove (CXUserUnitGroup xuug)
	{
		if(xuug == null || xuug.getId() == null)
		{
			throw new IllegalArgumentException("input object to remove cannot be null and must have id");
		}
		
		xuug = xuugDao.findById(xuug.getId());
		
		xuugDao.remove(xuug);
	}
}
