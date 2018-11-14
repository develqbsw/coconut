package sk.qbsw.security.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * The unit service.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
public class UnitServiceImpl extends AService implements UnitService
{
	private final UnitDao unitDao;

	/**
	 * Instantiates a new Unit service.
	 *
	 * @param unitDao the unit dao
	 */
	public UnitServiceImpl (UnitDao unitDao)
	{
		this.unitDao = unitDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Unit> findAll ()
	{
		return unitDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Unit> findByAccount (Account user) throws CSecurityException
	{
		return unitDao.findByAccountId(user.getId());
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Unit> findByAccountId (Long accountId) throws CSecurityException
	{
		return unitDao.findByAccountId(accountId);
	}
}
