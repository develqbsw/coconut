package sk.qbsw.core.security.service;

import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.CService;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.core.security.dao.IAddressDao;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.dao.IXUserUnitGroupDao;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.CXUserUnitGroup;

/**
 * Service for user management.
 *
 * @author Dalibor Rak
 * @author Tomas Leken
 * @author Michal Lacko
 * @author Tomas Lauro
 * 
 * @version 1.11.8
 * @since 1.0.0
 */
@Service ("cUserService")
public class CUserService extends CService implements IUserService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The organization dao. */
	@Autowired
	private IOrganizationDao organizationDao;

	/** The authentication params dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

	/**  The address dao. */
	@Autowired
	private IAddressDao addressDao;

	/** The authentication service. */
	@Autowired
	private IAuthenticationService authenticationService;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The cross user unit group dao. */
	@Autowired
	private IXUserUnitGroupDao crossUserUnitGroupDao;

	/**
	 * Disable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.core.security.service.ISecurityService#disableUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional (readOnly = false)
	public void disableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		userDao.save(toModify);
	}

	/**
	 * Enable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.core.security.service.ISecurityService#enableUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional (readOnly = false)
	public void enableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		userDao.save(toModify);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#get(java.lang.Long)
	 */
	@Transactional (readOnly = true)
	public CUser get (Long id)
	{
		return userDao.findById(id);
	}

	/**
	 * Gets the all users.
	 *
	 * @param organization the organization
	 * @return the all users
	 * @see sk.qbsw.core.security.service.ISecurityService#getAllUsers(sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getAllUsers (COrganization organization)
	{
		return userDao.findAllUsers(organization);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getOtherActiveUsers(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{
		return userDao.getOtherActiveUsers(organization, group, user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserByLogin(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser getUserByLogin (String login)
	{
		return userDao.findByLogin(login);
	}


	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserByPin(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public CUser getUserByPin (String pin)
	{
		CUser user = userDao.findByPinNull(pin);
		if (user.getFlagEnabled())
		{
			return user;
		}
		else
		{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserForModification(java.lang.Long)
	 */
	@Transactional (readOnly = true)
	public CUser getUserForModification (Long id)
	{
		return userDao.findForModification(id);
	}


	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers()
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers ()
	{
		return userDao.findAllUsers();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled)
	{
		return userDao.findAllUsers(organization, enabled);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsersOrderByOrganization(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group)
	{
		return userDao.findAllUsersOrderByOrganization(organization, enabled, group);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group)
	{
		return userDao.findAllUsers(organization, enabled, group);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers(sk.qbsw.winnetou.security.model.COrganization)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, CRole role)
	{
		if (role != null)
		{
			return userDao.findAllUsersByRole(organization, role);
		}
		else
		{
			return userDao.findAllUsers(organization);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled)
	{
		return userDao.findAllUsers(name, surname, login, enabled);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, COrganization organization)
	{
		return userDao.findAllUsers(name, surname, login, enabled, organization);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, String groupPrefix)
	{
		return userDao.findAllUsers(name, surname, login, enabled, groupPrefix);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String email)
	{
		return userDao.findAllUsers(email);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#registerNewUser(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = false)
	public void registerNewUser (CUser user, @CNotLogged @CNotAuditLogged String password, COrganization organization) throws CSecurityException
	{
		//register new user with empty password
		registerNewUser(user, organization);

		if (password == null)
		{
			throw new CSecurityException("The password is required", "error.security.invalidPassword");
		}

		//set password and save
		authenticationService.changeEncryptedPassword(user.getLogin(), password);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#registerNewUser(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Transactional (readOnly = false)
	public void registerNewUser (CUser user, COrganization organization) throws CSecurityException
	{
		try
		{
			//checks if user already exists
			userDao.findByLogin(user.getLogin());
			throw new CSecurityException("User with login " + user.getLogin() + " already exists", "error.security.loginused");
		}
		catch (NoResultException nre)
		{
			//do nothing
		}

		if (user.getLogin() == null || organization == null || organization.getName() == null)
		{
			throw new CSecurityException("Not enough parameter to create user", "error.security.loginused");
		}

		try
		{
			organization = organizationDao.findByName(organization.getName());
		}
		catch (NoResultException nre)
		{
			throw new CSecurityException("Security exception", "error.security.invalidOrganization");
		}

		user.setOrganization(organization);
		userDao.save(user);

		//create and save empty authentication params
		CAuthenticationParams authParams = new CAuthenticationParams();
		authParams.setUser(user);
		authParams.setPassword(null);
		authParams.setPasswordDigest(null);
		authParams.setPin(null);
		authenticationParamsDao.save(authParams);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#updateUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Transactional
	public void updateUser (CUser user)
	{
		userDao.save(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#setAddress(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CAddress)
	 */
	@Transactional
	public void setAddress (CUser user, CAddress address)
	{
		//set address to user
		user.setAddress(address);

		//save entities
		addressDao.save(address);
		userDao.save(user);

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#unsetUserFromGroup(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (CUser user, CGroup group, CUnit unit) throws CSecurityException
	{
		//validates input objects
		if ( (user == null || user.getId() == null || group == null || group.getId() == null) || (unit != null && unit.getId() == null))
		{
			throw new CSecurityException("The incorrect input object");
		}

		//defines the persisted objects
		CUser persistedUser = (CUser) getPersistedEntity(user, userDao);
		CGroup persistedGroup = (CGroup) getPersistedEntity(group, groupDao);
		CUnit persistedUnit = null;
		if (unit != null)
		{
			persistedUnit = (CUnit) getPersistedEntity(unit, unitDao);
		}

		//find user <-> group mapping records - the list should contains only one record, but the method handles the case if not
		List<CXUserUnitGroup> userUnitGroupRecords = crossUserUnitGroupDao.findAll(persistedUser, persistedUnit, persistedGroup);

		//remove records
		for (CXUserUnitGroup userUnitGroupRecord : userUnitGroupRecords)
		{
			if (persistedUser.getxUserUnitGroups().contains(userUnitGroupRecord))
			{
				persistedUser.getxUserUnitGroups().remove(userUnitGroupRecord);
			}
			crossUserUnitGroupDao.remove(userUnitGroupRecord);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#unsetUserFromGroup(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (CUser user, CGroup group) throws CSecurityException
	{
		unsetUserFromGroup(user, group, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#setUserToGroup(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	@Transactional
	public void setUserToGroup (CUser user, CGroup group, CUnit unit) throws CSecurityException
	{
		//validates input objects
		if ( (user == null || user.getId() == null || group == null || group.getId() == null) || (unit != null && unit.getId() == null))
		{
			throw new CSecurityException("The incorrect input object");
		}

		//defines the persisted objects
		CUser persistedUser = (CUser) getPersistedEntity(user, userDao);
		CGroup persistedGroup = (CGroup) getPersistedEntity(group, groupDao);
		CUnit persistedUnit = null;

		if (unit != null)
		{
			persistedUnit = (CUnit) getPersistedEntity(unit, unitDao);
		}

		// if group which will be added to user is excluded another group then cannot be added
		Boolean isAddedGroupExcluded = Boolean.FALSE;
		//if already exist combination of user group unit 
		Boolean isGroupAlreadyAdded = Boolean.FALSE;

		//find all groups assigned to user
		List<CXUserUnitGroup> userUnitGroupRecords = crossUserUnitGroupDao.findAll(persistedUser, persistedUnit, null);

		for (CXUserUnitGroup userUnitGroup : userUnitGroupRecords)
		{
			if (persistedGroup.equals(userUnitGroup))
			{
				//if is group already added
				isGroupAlreadyAdded = Boolean.TRUE;
				break;
			}

			//if added group is excluded by group which user already have then group cannot be added
			Set<CGroup> excludedGroups = userUnitGroup.getGroup().getExcludedGroups();
			if (excludedGroups.contains(persistedGroup))
			{
				isAddedGroupExcluded = Boolean.TRUE;
				break;
			}

		}

		//if is not combination user group unit already added
		//or if existing group assigned to user not exclude added group
		if (!isGroupAlreadyAdded && !isAddedGroupExcluded)
		{
			CXUserUnitGroup userUnitGroupRecord = new CXUserUnitGroup();
			userUnitGroupRecord.setUser(persistedUser);
			userUnitGroupRecord.setGroup(persistedGroup);
			userUnitGroupRecord.setUnit(persistedUnit);
			crossUserUnitGroupDao.save(userUnitGroupRecord);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#setUserToGroup(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	@Transactional
	public void setUserToGroup (CUser user, CGroup group) throws CSecurityException
	{
		setUserToGroup(user, group, null);
	}

	/**
	 * Gets the persisted entity from transaction manager for input entity.
	 *
	 * @param <T> the generic type for entity
	 * @param entity the entity
	 * @param dao the dao for operations with entity
	 * @return the persisted entity
	 * @throws CSecurityException the security exception occures if the input parameters are incorrect or if there is no entity in database
	 */
	private <T extends IEntity<Long>>T getPersistedEntity (T entity, IEntityDao<Long, T> dao) throws CSecurityException
	{
		if (entity != null && entity.getId() != null)
		{
			try
			{
				T persistedEntity = dao.findById(entity.getId());
				if (persistedEntity == null)
				{
					throw new NoResultException();
				}
				else
				{
					return persistedEntity;
				}
			}
			catch (NoResultException ex)
			{
				throw new CSecurityException("The invalid input entity");
			}
		}
		else
		{
			throw new CSecurityException("The invalid input entity");
		}
	}
}
