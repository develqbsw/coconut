package sk.qbsw.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The database authorization service.
 *
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Service (value = "authorizationService")
public class CAuthorizationService implements IAuthorizationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthorizationService#checkAccessRights(java.lang.String, sk.qbsw.core.security.model.domain.CRole, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public void checkAccessRights (String login, CRole role, String unit, String category) throws CSecurityException
	{
		CUnit localUnit = getUnitByName(unit);
		CUser user = getUserByLoginAndUnit(login, localUnit);

		if (user == null)
		{
			throw new CWrongPasswordException("User with login " + login + " not recognised");
		}
		else
		{
			//if the user has not a role throw an exception
			if (user.hasRole(role) == false)
			{
				throw new CSecurityException("User has not a role with code " + role.getCode());
			}

			if (localUnit != null)
			{
				if (user.isInUnit(localUnit) == false)
				{
					throw new CSecurityException("User is not is unit with name " + localUnit.getName());
				}
			}

			if (category != null && user.hasCategory(category, role) == false)
			{
				throw new CSecurityException("User has not a category with name " + category);
			}
		}
	}

	/**
	 * Gets the user by login and unit.
	 *
	 * @param login the login
	 * @param unit the unit
	 * @return the user by login and unit
	 */
	private CUser getUserByLoginAndUnit (String login, CUnit unit)
	{
		if (unit == null)
		{
			return userDao.findByLogin(login);
		}
		else
		{
			return userDao.findByLogin(login, unit);
		}
	}

	/**
	 * Gets the unit from DB by name.
	 *
	 * @param unitName the unit name
	 * @return the unit by name
	 * @throws CSecurityException the security exception
	 */
	private CUnit getUnitByName (String unitName) throws CSecurityException
	{
		if (unitName != null)
		{
			CUnit localUnit = unitDao.findByName(unitName);

			if (localUnit != null)
			{
				return localUnit;
			}
			else
			{
				throw new CSecurityException("There is not a unit with name " + unitName);
			}
		}
		else
		{
			return null;
		}
	}
}
