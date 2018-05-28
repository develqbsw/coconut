package sk.qbsw.security.management.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.filter.AccountAssociationsFilter;
import sk.qbsw.security.core.model.order.OrderModel;
import sk.qbsw.security.core.model.order.OrderSpecification;
import sk.qbsw.security.core.model.order.OrderSpecifiers;
import sk.qbsw.security.core.model.order.UserOrderByAttributeSpecifiers;
import sk.qbsw.security.core.model.order.OrderByAttributeSpecifier;
import sk.qbsw.security.management.service.UsersValidationService;

/**
 * Service for validation users.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Service ("cUsersValidationService")
public class UsersValidationServiceImpl extends AService implements UsersValidationService
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersValidationServiceImpl.class);

	/** The organization dao. */
	@Autowired
	private OrganizationDao organizationDao;

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

	/** The auth params dao. */
	@Autowired
	private AuthenticationParamsDao authParamsDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUsersValidationService#isOrganizationExists(sk.qbsw.security.core.core.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isOrganizationExists (Organization organization)
	{
		//checks if there is an organization with id exists
		if (organization.getId() != null)
		{
			Organization persistedOrganization = organizationDao.findById(organization.getId());
			if (persistedOrganization != null)
			{
				return true;
			}
			return false;
		}
		else
		{
			//checks if there is an organization with given name
			return isOrganizationExists(organization.getName());
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUsersValidationService#isUserExists(sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isUserExists (Account user)
	{
		Boolean exists = false;
		Account userOld;

		try
		{
			userOld = userDao.findOneByLogin(user.getLogin());
		}
		catch (NoResultException | CSecurityException ex)
		{
			LOGGER.debug("User does not exist", ex);
			userOld = null;
		}

		if (userOld != null && ! (userOld.getId().equals(user.getId())))
		{
			exists = true;
		}

		return exists;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUsersValidationService#leastOneAdmin(sk.qbsw.security.core.core.model.domain.CUser, sk.qbsw.security.core.core.model.domain.COrganization, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean leastOneAdmin (Account user, Organization organization, String group)
	{

		Boolean leastOneAdmin = false;

		Group adminGroup = groupDao.findByCode(group).get(0);

		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setOrganization(organization);
		filter.setGroup(adminGroup);
		filter.setExcludedUser(user);
		filter.setEnabled(true);

		OrderModel<UserOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<OrderByAttributeSpecifier>(UserOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		List<Account> users = userDao.findByUserAssociationsFilter(filter, orderModel);

		if (users.isEmpty() && (!user.getGroups().iterator().next().getCode().equals(adminGroup.getCode()) || !user.getFlagEnabled()))
		{
			leastOneAdmin = true;
		}

		return leastOneAdmin;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUsersValidationService#isOrganizationExists(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isOrganizationExists (String name)
	{
		if (name != null)
		{
			List<Organization> persistedOrganizations = organizationDao.findByName(name);
			if (persistedOrganizations != null && !persistedOrganizations.isEmpty())
			{
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUsersValidationService#isUserExists(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isUserExists (String login)
	{

		Boolean exists = false;

		Account userOld;

		try
		{
			userOld = userDao.findOneByLogin(login);
		}
		catch (NoResultException | CSecurityException ex)
		{
			LOGGER.debug("User not found", ex);
			userOld = null;
		}

		if (userOld != null)
		{
			exists = true;
		}

		return exists;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUsersValidationService#isUserExistsPin(sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isUserExistsPin (Account userOld)
	{
		Boolean exists = false;
		AuthenticationParams oldUserAuthParams = authParamsDao.findOneByUserId(userOld.getId());

		try
		{
			List<Account> users = userDao.findByPinCode(oldUserAuthParams.getPin());

			//if the users is not empty - any user has assigned given pin
			if (users != null && !users.isEmpty())
			{
				exists = true;
			}
		}
		catch (CBusinessException e)
		{
			LOGGER.debug("User with pin not found", e);
			//the user with pin null does not exists
		}

		return exists;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IUsersValidationService#isUserExistsPin(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isUserExistsPin (String pin)
	{
		Boolean exists = false;

		try
		{
			List<Account> users = userDao.findByPinCode(pin);

			if (users != null && !users.isEmpty())
			{
				exists = true;
			}
		}
		catch (CBusinessException e)
		{
			LOGGER.debug("User not found", e);
			//the user with pin null does not exists
		}

		return exists;
	}
}
