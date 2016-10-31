package sk.qbsw.core.security.service;

import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.persistence.model.domain.IEntity;
import sk.qbsw.core.security.dao.IAddressDao;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.dao.IXUserUnitGroupDao;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.CXUserUnitGroup;
import sk.qbsw.core.security.model.filter.CUserAssociationsFilter;
import sk.qbsw.core.security.model.filter.CUserDetailFilter;
import sk.qbsw.core.security.model.order.COrderModel;
import sk.qbsw.core.security.model.order.COrderSpecification;
import sk.qbsw.core.security.model.order.EOrderSpecifier;
import sk.qbsw.core.security.model.order.EOrganizationOrderByAttributeSpecifier;
import sk.qbsw.core.security.model.order.EUserOrderByAttributeSpecifier;
import sk.qbsw.core.security.model.order.IOrderByAttributeSpecifier;

/**
 * Service for user management.
 *
 * @author Dalibor Rak
 * @author Tomas Leken
 * @author Michal Lacko
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Service ("cUserService")
public class CUserService extends AService implements IUserService
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CUserService.class);

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
	private IAuthenticationModifierService authenticationService;

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
	@Override
	@Transactional (readOnly = false)
	public void disableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.FALSE);
		userDao.update(toModify);
	}

	/**
	 * Enable user.
	 *
	 * @param user the user
	 * @see sk.qbsw.core.security.service.ISecurityService#enableUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = false)
	public void enableUser (CUser user)
	{
		CUser toModify = userDao.findById(user.getId());
		toModify.setFlagEnabled(Boolean.TRUE);
		userDao.update(toModify);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#get(java.lang.Long)
	 */
	@Override
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
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getAllUsers (COrganization organization)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getOtherActiveUsers(sk.qbsw.core.security.model.domain.COrganization, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getOtherActiveUsers (COrganization organization, CGroup group, CUser user)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setGroup(group);
		filter.setExcludedUser(user);
		filter.setEnabled(true);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserByLogin(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser getUserByLogin (String login) throws CSecurityException
	{
		return userDao.findOneByLogin(login);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUserForModification(java.lang.Long)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser getUserForModification (Long id)
	{
		return userDao.findForModification(id);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers()
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers ()
	{
		return userDao.findAll();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsersOrderByOrganization(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsersOrderByOrganization (COrganization organization, Boolean enabled, CGroup group)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);
		filter.setGroup(group);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EOrganizationOrderByAttributeSpecifier.NAME, EOrderSpecifier.ASC));
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(sk.qbsw.core.security.model.domain.COrganization, java.lang.Boolean, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, Boolean enabled, CGroup group)
	{
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setEnabled(enabled);
		filter.setGroup(group);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserAssociationsFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.stavbyveduci.management.service.IUserService#getUsers(sk.qbsw.winnetou.security.model.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (COrganization organization, CRole role)
	{
		if (role != null)
		{
			CUserAssociationsFilter filter = new CUserAssociationsFilter();
			filter.setOrganization(organization);
			filter.setRole(role);

			COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
			orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

			return userDao.findByUserAssociationsFilter(filter, orderModel);
		}
		else
		{
			CUserAssociationsFilter filter = new CUserAssociationsFilter();
			filter.setOrganization(organization);

			COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
			orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

			return userDao.findByUserAssociationsFilter(filter, orderModel);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled)
	{
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, COrganization organization)
	{
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);
		filter.setOrganization(organization);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String name, String surname, String login, Boolean enabled, String groupPrefix)
	{
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(name);
		filter.setSurname(surname);
		filter.setLogin(login);
		filter.setEnabled(enabled);
		filter.setGroupCodePrefix(groupPrefix);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#getUsers(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public List<CUser> getUsers (String email)
	{
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setEmail(email);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return userDao.findByUserDetailFilter(filter, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#registerNewUser(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = false)
	public void registerNewUser (CUser user, @CNotLogged @CNotAuditLogged String password, COrganization organization) throws CSecurityException
	{
		//register new user with empty password
		registerNewUser(user, organization);

		if (password == null)
		{
			throw new CSecurityException(ECoreErrorResponse.PASSWORD_MANDATORY);
		}

		//set password and save
		authenticationService.changeEncryptedPassword(user.getLogin(), password);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#registerNewUser(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = false)
	public void registerNewUser (CUser user, COrganization organization) throws CSecurityException
	{
		try
		{
			//checks if user already exists
			userDao.findOneByLogin(user.getLogin());
			throw new CSecurityException("User with login " + user.getLogin() + " already exists.", ECoreErrorResponse.USER_ALREADY_EXISTS);
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("Login not used, can continue", nre);
		}

		if (user.getLogin() == null || organization == null || organization.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.USER_NOT_ALL_PARAMETERS);
		}

		COrganization redOrg;
		try
		{
			redOrg = organizationDao.findById(organization.getId());
		}
		catch (NoResultException nre)
		{
			LOGGER.debug("Organization not found", nre);
			throw new CSecurityException(ECoreErrorResponse.ORGANIZATION_NOT_VALID);
		}

		user.setOrganization(redOrg);
		userDao.update(user);

		//create and save empty authentication params
		CAuthenticationParams authParams = new CAuthenticationParams();
		authParams.setUser(user);
		authParams.setPassword(null);
		authParams.setPasswordDigest(null);
		authParams.setPin(null);
		authenticationParamsDao.update(authParams);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#updateUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = false)
	public void updateUser (CUser user)
	{
		userDao.update(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#setAddress(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CAddress)
	 */
	@Override
	@Transactional
	public void setAddress (CUser user, CAddress address)
	{
		//set address to user
		user.setAddress(address);

		//save entities
		addressDao.update(address);
		userDao.update(user);

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#unsetUserFromGroup(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (Long userId, Long groupId, Long unitId) throws CSecurityException
	{
		if (userId == null || groupId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		//get persisted objects
		CUser user = userDao.findById(userId);
		CGroup group = groupDao.findById(groupId);
		CUnit unit = null;

		if (unitId != null)
		{
			unit = unitDao.findById(unitId);
		}

		//call method with logic
		if (unit != null)
		{
			unsetUserFromGroup(user, group, unit);
		}
		else
		{
			unsetUserFromGroup(user, group);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#unsetUserFromGroup(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (Long userId, Long groupId) throws CSecurityException
	{
		unsetUserFromGroup(userId, groupId, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#unsetUserFromGroup(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	@Transactional
	public void unsetUserFromGroup (CUser user, CGroup group, CUnit unit) throws CSecurityException
	{
		//validates input objects
		if (user == null || user.getId() == null || group == null || group.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		if (unit != null && unit.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		removeUserUnitGroup(user, group, unit);
	}

	private void removeUserUnitGroup (CUser user, CGroup group, CUnit unit)
	{
		//find user <-> group mapping records - the list should contains only one record, but the method handles the case if not
		List<CXUserUnitGroup> userUnitGroupRecords = crossUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, group);

		//remove records
		for (CXUserUnitGroup userUnitGroupRecord : userUnitGroupRecords)
		{
			if (user.getxUserUnitGroups().contains(userUnitGroupRecord))
			{
				user.getxUserUnitGroups().remove(userUnitGroupRecord);
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
	 * @see sk.qbsw.core.security.service.IUserService#setUserToGroup(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public void setUserToGroup (Long userId, Long groupId, Long unitId) throws CBusinessException
	{
		if (userId == null || groupId == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		//get persisted objects
		CUser user = userDao.findById(userId);
		CGroup group = groupDao.findById(groupId);
		CUnit unit = null;

		if (unitId != null)
		{
			unit = unitDao.findById(unitId);
		}

		//call method with logic
		if (unit != null)
		{
			setUserToGroup(user, group, unit);
		}
		else
		{
			setUserToGroup(user, group);
		}

	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#setUserToGroup(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional
	public void setUserToGroup (Long userId, Long groupId) throws CBusinessException
	{
		setUserToGroup(userId, groupId, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#setUserToGroup(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	@Transactional
	public void setUserToGroup (CUser user, CGroup group, CUnit unit) throws CBusinessException
	{
		//validates input objects
		if (!CUser.isKnown(user) || !CGroup.isKnown(group))
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		if (unit != null && unit.getId() == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		boolean canBeAdded = canBeGroupUnitAssignedToUser(user, group, unit);
		if (canBeAdded)
		{
			CXUserUnitGroup userUnitGroupRecord = new CXUserUnitGroup();
			userUnitGroupRecord.setUser(user);
			userUnitGroupRecord.setGroup(group);
			userUnitGroupRecord.setUnit(unit);
			crossUserUnitGroupDao.update(userUnitGroupRecord);
		}
		else
		{
			throw new CBusinessException("The group " + group.getCode() + " cannot be set to user: it's excluded or already has been set to user.");
		}
	}

	private boolean canBeGroupUnitAssignedToUser (CUser user, CGroup group, CUnit unit)
	{
		//find all groups assigned to user
		List<CXUserUnitGroup> userUnitGroupRecords = crossUserUnitGroupDao.findByUserAndUnitAndGroup(user, unit, null);

		for (CXUserUnitGroup userUnitGroup : userUnitGroupRecords)
		{
			if (group.equals(userUnitGroup.getGroup()))
			{
				//if is group already added
				return false;
			}

			//if added group is excluded by group which user already have then group cannot be added
			Set<CGroup> excludedGroups = userUnitGroup.getGroup().getExcludedGroups();
			if (excludedGroups != null && excludedGroups.contains(group))
			{
				return false;
			}
		}
		//if is not combination user group unit already added
		//or if existing group assigned to user not exclude added group
		return true;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#setUserToGroup(sk.qbsw.core.security.model.domain.CUser, sk.qbsw.core.security.model.domain.CGroup)
	 */
	@Override
	@Transactional
	public void setUserToGroup (CUser user, CGroup group) throws CBusinessException
	{
		setUserToGroup(user, group, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IUserService#checksUserExist(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean checksUserExist (String login)
	{
		try
		{
			userDao.findOneByLogin(login);
			return true;
		}
		catch (NoResultException | CSecurityException e)
		{
			LOGGER.debug("User does not exist", e);
			return false;
		}
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
	private <T extends IEntity<Long>> T getPersistedEntity (T entity, IEntityDao<Long, T> dao) throws CSecurityException
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
				LOGGER.debug("Entity not found", ex);

				throw new CSecurityException("The invalid input entity");
			}
		}
		else
		{
			throw new CSecurityException("The invalid input entity");
		}
	}
}
