package sk.qbsw.security.authorization.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.base.exception.InvalidUserException;
import sk.qbsw.security.core.dao.RoleDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.Account;

/**
 * The database authorization service.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
@Service (value = "authorizationService")
public class AuthorizationServiceImpl extends AService implements AuthorizationService
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

	/** The role dao. */
	@Autowired
	private RoleDao roleDao;

	/** The unit dao. */
	@Autowired
	private UnitDao unitDao;

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthorizationService#checkAccessRights(java.lang.String, sk.qbsw.security.core.core.model.domain.CRole, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public void checkAccessRights (String login, Role role, String unit, String category) throws CSecurityException
	{
		Unit localUnit = getUnitByName(unit);
		Account user;
		try
		{
			user = getUserByLoginAndUnit(login, localUnit);

			//if the user has not a role throw an exception
			if (!user.hasRole(role))
			{
				throw new CSecurityException("User has not a role with code " + role.getCode());
			}

			// if the user is not in unit, throw an exception
			if (localUnit != null && !user.isInUnit(localUnit))
			{
				throw new CSecurityException("User is not is unit with name " + localUnit.getName());
			}

			// if the user has not category, throw exception
			if (category != null && !user.hasCategory(category, role))
			{
				throw new CSecurityException("User has not a category with name " + category);
			}

		}
		catch (NoResultException nre)
		{
			LOGGER.error("User not found", nre);
			throw new InvalidUserException("User with login " + login + " not recognised");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthorizationService#getRoleByCode(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<Role> getRoleByCode (String code)
	{

		return roleDao.findByCode(code);
	}

	/**
	 * Gets the unit from DB by name.
	 *
	 * @param unitName the unit name
	 * @return the unit by name
	 * @throws CSecurityException the security exception
	 */
	private Unit getUnitByName (String unitName) throws CSecurityException
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

	/**
	 * Gets the user by login and unit.
	 *
	 * @param login the login
	 * @param unit the unit
	 * @return the user by login and unit
	 * 
	 * @throws NoResultException there is no result
	 * @throws CSecurityException the login is null
	 */
	private Account getUserByLoginAndUnit (String login, Unit unit) throws CSecurityException
	{
		Account user;

		if (unit == null)
		{
			user = userDao.findOneByLogin(login);
		}
		else
		{
			user = userDao.findOneByLoginAndUnit(login, unit);
		}


		if (user == null)
		{
			throw new InvalidUserException("User with login " + login + " not recognised");
		}
		return user;

	}
}
