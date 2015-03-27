package sk.qbsw.core.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.dao.IAddressDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Service for unit management.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
@Service ("unitService")
public class CUnitService extends AService implements IUnitService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The unit dao. */
	@Autowired
	private IAddressDao addressDao;

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
	public List<CUnit> getAll (CUser user) throws CSecurityException
	{
		return unitDao.findByUserId(user.getId());
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUnitService#setAddress(sk.qbsw.core.security.model.domain.CUnit, sk.qbsw.core.security.model.domain.CAddress)
	 */
	@Override
	@Transactional
	public void setAddress (CUnit unit, CAddress address)
	{
		//set address to unit
		unit.setAddress(address);

		//save entities
		addressDao.update(address);
		unitDao.update(unit);

	}
}
