package sk.qbsw.organization.complex.management.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.complex.core.dao.OrganizationDao;
import sk.qbsw.organization.complex.core.model.domain.Organization;

import java.util.List;

/**
 * The organization service.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public class OrganizationServiceImpl extends AService implements OrganizationService
{
	private final OrganizationDao organizationComplexDao;

	public OrganizationServiceImpl (OrganizationDao organizationComplexDao)
	{
		this.organizationComplexDao = organizationComplexDao;
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

		return organizationComplexDao.update(organization);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Organization read (Long id)
	{
		return organizationComplexDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Organization> findByName (String name)
	{
		return organizationComplexDao.findByName(name);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<Organization> findAll ()
	{
		return organizationComplexDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean existsByName (String name)
	{
		if (name != null)
		{
			List<Organization> currentOrganizations = organizationComplexDao.findByName(name);
			return !currentOrganizations.isEmpty();
		}

		return false;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public Organization update (Organization organization)
	{
		return organizationComplexDao.update(organization);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void activate (Long id)
	{
		Organization currentOrganization = organizationComplexDao.findById(id);
		currentOrganization.setState(ActivityStates.ACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void inactivate (Long id)
	{
		Organization currentOrganization = organizationComplexDao.findById(id);
		currentOrganization.setState(ActivityStates.INACTIVE);
	}
}
