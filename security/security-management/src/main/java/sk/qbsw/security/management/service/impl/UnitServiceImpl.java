package sk.qbsw.security.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.AddressDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Address;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.management.service.UnitService;

/**
 * Service for unit management.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
@Service ("unitService")
public class UnitServiceImpl extends AService implements UnitService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The unit dao. */
	@Autowired
	private UnitDao unitDao;

	/** The unit dao. */
	@Autowired
	private AddressDao addressDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUnitService#getAll()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Unit> getAll ()
	{
		return unitDao.findAll();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUnitService#getAll(sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Unit> getAll (User user) throws CSecurityException
	{
		return unitDao.findByUserId(user.getId());
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUnitService#setAddress(sk.qbsw.security.core.core.model.domain.CUnit, sk.qbsw.security.core.core.model.domain.CAddress)
	 */
	@Override
	@Transactional
	public void setAddress (Unit unit, Address address)
	{
		//set address to unit
		unit.setAddress(address);

		//save entities
		addressDao.update(address);
		unitDao.update(unit);

	}
}
