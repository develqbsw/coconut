package sk.qbsw.security.service;

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
import sk.qbsw.security.dao.IAuthenticationParamsDao;
import sk.qbsw.security.dao.IGroupDao;
import sk.qbsw.security.dao.IOrganizationDao;
import sk.qbsw.security.dao.IUserDao;
import sk.qbsw.security.model.domain.CAuthenticationParams;
import sk.qbsw.security.model.domain.CGroup;
import sk.qbsw.security.model.domain.COrganization;
import sk.qbsw.security.model.domain.CUser;
import sk.qbsw.security.model.filter.CUserAssociationsFilter;
import sk.qbsw.security.model.order.COrderModel;
import sk.qbsw.security.model.order.COrderSpecification;
import sk.qbsw.security.model.order.EOrderSpecifier;
import sk.qbsw.security.model.order.EUserOrderByAttributeSpecifier;
import sk.qbsw.security.model.order.IOrderByAttributeSpecifier;

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
public class CUsersValidationService extends AService implements IUsersValidationService
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CUserService.class);

	/** The organization dao. */
	@Autowired
	private IOrganizationDao organizationDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** The auth params dao. */
	@Autowired
	private IAuthenticationParamsDao authParamsDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.IUsersValidationService#isOrganizationExists(sk.qbsw.security.model.domain.COrganization)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isOrganizationExists (COrganization organization)
	{
		//checks if there is an organization with id exists
		if (organization.getId() != null)
		{
			COrganization persistedOrganization = organizationDao.findById(organization.getId());
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
	 * @see sk.qbsw.security.service.IUsersValidationService#isUserExists(sk.qbsw.security.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isUserExists (CUser user)
	{
		Boolean exists = false;
		CUser userOld;

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
	 * @see sk.qbsw.security.service.IUsersValidationService#leastOneAdmin(sk.qbsw.security.model.domain.CUser, sk.qbsw.security.model.domain.COrganization, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean leastOneAdmin (CUser user, COrganization organization, String group)
	{

		Boolean leastOneAdmin = false;

		CGroup adminGroup = groupDao.findByCode(group).get(0);

		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setOrganization(organization);
		filter.setGroup(adminGroup);
		filter.setExcludedUser(user);
		filter.setEnabled(true);

		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		List<CUser> users = userDao.findByUserAssociationsFilter(filter, orderModel);

		if (users.isEmpty() && (!user.getGroups().iterator().next().getCode().equals(adminGroup.getCode()) || !user.getFlagEnabled()))
		{
			leastOneAdmin = true;
		}

		return leastOneAdmin;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.IUsersValidationService#isOrganizationExists(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isOrganizationExists (String name)
	{
		if (name != null)
		{
			List<COrganization> persistedOrganizations = organizationDao.findByName(name);
			if (persistedOrganizations != null && !persistedOrganizations.isEmpty())
			{
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.IUsersValidationService#isUserExists(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isUserExists (String login)
	{

		Boolean exists = false;

		CUser userOld;

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
	 * @see sk.qbsw.security.service.IUsersValidationService#isUserExistsPin(sk.qbsw.security.model.domain.CUser)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isUserExistsPin (CUser userOld)
	{
		Boolean exists = false;
		CAuthenticationParams oldUserAuthParams = authParamsDao.findOneByUserId(userOld.getId());

		try
		{
			List<CUser> users = userDao.findByPinCode(oldUserAuthParams.getPin());

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
	 * @see sk.qbsw.security.service.IUsersValidationService#isUserExistsPin(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public Boolean isUserExistsPin (String pin)
	{
		Boolean exists = false;

		try
		{
			List<CUser> users = userDao.findByPinCode(pin);

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
