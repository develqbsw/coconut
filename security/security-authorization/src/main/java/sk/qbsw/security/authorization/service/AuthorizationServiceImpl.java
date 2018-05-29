package sk.qbsw.security.authorization.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.base.exception.InvalidAccountException;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Unit;

import javax.persistence.NoResultException;

/**
 * The authorization service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
public class AuthorizationServiceImpl extends AService implements AuthorizationService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

	private final UnitDao unitDao;

	private final AccountDao accountDao;

	/**
	 * Instantiates a new Authorization service.
	 *
	 * @param unitDao the unit dao
	 * @param accountDao the account dao
	 */
	@Autowired
	public AuthorizationServiceImpl (UnitDao unitDao, AccountDao accountDao)
	{
		this.unitDao = unitDao;
		this.accountDao = accountDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void checkAccessRights (String login, Role role, String unit, String category) throws CSecurityException
	{
		Unit localUnit = findUnitByName(unit);
		Account account;
		try
		{
			account = findAccountByLoginAndUnit(login, localUnit);

			// if the account has not a role throw an exception
			if (!account.hasRole(role))
			{
				throw new CSecurityException("Account has not a role with code " + role.getCode());
			}

			// if the account is not in unit, throw an exception
			if (localUnit != null && !account.isInUnit(localUnit))
			{
				throw new CSecurityException("Account is not is unit with name " + localUnit.getName());
			}

			// if the account has not category, throw exception
			if (category != null && !account.hasCategory(category, role))
			{
				throw new CSecurityException("Account has not a category with name " + category);
			}

		}
		catch (NoResultException nre)
		{
			LOGGER.error("account not found", nre);
			throw new InvalidAccountException("Account with login " + login + " not recognised");
		}
	}

	private Unit findUnitByName (String unitName) throws CSecurityException
	{
		if (unitName != null)
		{
			try
			{
				return unitDao.findOneByName(unitName);

			}
			catch (NoResultException ex)
			{
				throw new CSecurityException("There is not a unit with name " + unitName, ex);
			}
		}
		else
		{
			return null;
		}
	}

	private Account findAccountByLoginAndUnit (String login, Unit unit) throws CSecurityException
	{
		Account account;

		if (unit == null)
		{
			account = accountDao.findOneByLogin(login);
		}
		else
		{
			account = accountDao.findOneByLoginAndUnit(login, unit);
		}


		if (account == null)
		{
			throw new InvalidAccountException("Account with login " + login + " not recognised");
		}
		return account;

	}
}
