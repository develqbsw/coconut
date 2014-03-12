package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for unit management.
 *
 * @author Tomas Lauro
 * @version 1.7.1
 * @since 1.6.0
 */
@Service ("unitService")
public class CUnitService implements IUnitService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUnitService#getAll()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUnit> getAll ()
	{
		return unitDao.findAll();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUnitService#getAll(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUnit> getAll (CUser user)
	{
		return unitDao.findAll(user.getId());
	}
}
