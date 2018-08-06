package sk.qbsw.organization.complex.management.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.organization.complex.core.configuration.OrganizationCoreConfiguration;
import sk.qbsw.organization.complex.core.dao.OrganizationDao;
import sk.qbsw.organization.complex.core.dao.UnitDao;
import sk.qbsw.organization.complex.core.dao.UserDao;
import sk.qbsw.organization.complex.management.service.OrganizationService;
import sk.qbsw.organization.complex.management.service.OrganizationServiceImpl;
import sk.qbsw.organization.complex.management.service.UnitService;
import sk.qbsw.organization.complex.management.service.UnitServiceImpl;
import sk.qbsw.organization.complex.management.service.UserService;
import sk.qbsw.organization.complex.management.service.UserServiceImpl;

/**
 * The type Organization management configuration.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class OrganizationManagementConfiguration extends OrganizationCoreConfiguration
{
	@Bean
	public OrganizationService organizationComplexService (OrganizationDao organizationComplexDao)
	{
		return new OrganizationServiceImpl(organizationComplexDao);
	}

	@Bean
	public UnitService unitComplexService (UnitDao unitDao, UserDao userDao)
	{
		return new UnitServiceImpl(unitDao, userDao);
	}

	@Bean
	public UserService userService (UserDao userDao)
	{
		return new UserServiceImpl(userDao);
	}
}
