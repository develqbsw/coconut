package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.model.domain.CGroup;

/**
 * Service for projects management
 * 
 * @author Michal Lacko
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
@Service ("cGroupService")
public class CGroupService implements IGroupService
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IGroupDao groupDao;

	@Transactional (readOnly = true)
	public List<CGroup> getAll ()
	{
		return groupDao.findAll();
	}

	@Transactional (readOnly = true)
	public List<CGroup> getByCode (String code)
	{
		return groupDao.findByCode(code);
	}
}
