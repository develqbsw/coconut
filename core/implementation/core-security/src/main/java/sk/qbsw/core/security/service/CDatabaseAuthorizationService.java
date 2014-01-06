package sk.qbsw.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service (value = "databaseAuthorizationService")
public class CDatabaseAuthorizationService implements IAuthorizationService
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
	public void checkAccessRights (String login, CRole role, String unit, String category) throws CSecurityException
	{
		//find user by login with corresponding groups and roles
		CUser user = userDao.findByLogin(login);

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

			if (unit != null)
			{
				CUnit localUnit = unitDao.findByName(unit);

				if (localUnit == null)
				{
					throw new CSecurityException("There is not a unit with name " + unit);
				}
				else
				{
					if (user.isInUnit(localUnit) == false)
					{
						throw new CSecurityException("User is not is unit with name " + localUnit.getName());
					}
				}
			}

			if (category != null && user.hasCategory(category) == false)
			{
				throw new CSecurityException("User has not a category with name " + category);
			}
		}
	}
}
