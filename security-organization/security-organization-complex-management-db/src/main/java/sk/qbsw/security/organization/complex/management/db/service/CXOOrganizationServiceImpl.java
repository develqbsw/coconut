package sk.qbsw.security.organization.complex.management.db.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.organization.complex.core.dao.CXOOrganizationDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;
import sk.qbsw.security.organization.complex.management.service.CXOOrganizationService;

import java.util.List;

/**
 * The complex organization service implementation.
 *
 * @author Tomas Leken
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class CXOOrganizationServiceImpl extends AService implements CXOOrganizationService
{
	private final CXOOrganizationDao organizationComplexDao;

	/**
	 * Instantiates a new Organization service.
	 *
	 * @param organizationComplexDao the organization complex dao
	 */
	public CXOOrganizationServiceImpl (CXOOrganizationDao organizationComplexDao)
	{
		this.organizationComplexDao = organizationComplexDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOOrganization create (String code, String name, String email, ActivityStates state)
	{
		CXOOrganization organization = new CXOOrganization();
		organization.setCode(code);
		organization.setName(name);
		organization.setEmail(email);
		organization.setState(state);

		return organizationComplexDao.update(organization);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOOrganization read (Long id)
	{
		return organizationComplexDao.findById(id);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<CXOOrganization> findByName (String name)
	{
		return organizationComplexDao.findByName(name);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public List<CXOOrganization> findAll ()
	{
		return organizationComplexDao.findAll();
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean existsByName (String name)
	{
		if (name != null)
		{
			List<CXOOrganization> currentOrganizations = organizationComplexDao.findByName(name);
			return !currentOrganizations.isEmpty();
		}

		return false;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public CXOOrganization update (CXOOrganization organization)
	{
		return organizationComplexDao.update(organization);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void activate (Long id)
	{
		CXOOrganization currentOrganization = organizationComplexDao.findById(id);
		currentOrganization.setState(ActivityStates.ACTIVE);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void inactivate (Long id)
	{
		CXOOrganization currentOrganization = organizationComplexDao.findById(id);
		currentOrganization.setState(ActivityStates.INACTIVE);
	}
}
