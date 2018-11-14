package sk.qbsw.security.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.management.service.AccountPermissionManagementService;

import java.util.List;

/**
 * The organization service.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.0.0
 */
public class OrganizationServiceImpl extends AService implements OrganizationService
{
	private final OrganizationDao organizationDao;

	private final AccountPermissionManagementService accountPermissionManagementService;

	/**
	 * Instantiates a new Organization service.
	 *
	 * @param organizationDao the organization dao
	 * @param accountPermissionManagementService the account permission management service
	 */
	public OrganizationServiceImpl (OrganizationDao organizationDao, AccountPermissionManagementService accountPermissionManagementService)
	{
		this.organizationDao = organizationDao;
		this.accountPermissionManagementService = accountPermissionManagementService;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Organization create (String code, String name, String email, ActivityStates state)
	{
		Organization organization = new Organization();
		organization.setCode(code);
		organization.setName(name);
		organization.setEmail(email);
		organization.setState(state);

		return organizationDao.update(organization);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Organization register (String code, String name, String email, Account account, String groupCode) throws CBusinessException
	{
		Organization organization = create(code, name, email, ActivityStates.ACTIVE);

		account.setState(ActivityStates.ACTIVE);
		account.setOrganization(organization);
		accountPermissionManagementService.assignAccountToGroup(account.getLogin(), groupCode);

		return organization;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Organization read (Long id)
	{
		return organizationDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Organization> findByName (String name)
	{
		return organizationDao.findByName(name);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Organization> findAll ()
	{
		return organizationDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean existsByName (String name)
	{
		if (name != null)
		{
			List<Organization> currentOrganizations = organizationDao.findByName(name);
			return !currentOrganizations.isEmpty();
		}

		return false;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Organization update (Organization organization)
	{
		return organizationDao.update(organization);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void activate (Long id)
	{
		Organization currentOrganization = organizationDao.findById(id);
		currentOrganization.setState(ActivityStates.ACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void inactivate (Long id)
	{
		Organization currentOrganization = organizationDao.findById(id);
		currentOrganization.setState(ActivityStates.INACTIVE);
	}
}
