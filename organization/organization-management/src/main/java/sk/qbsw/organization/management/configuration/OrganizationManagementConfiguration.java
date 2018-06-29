package sk.qbsw.organization.management.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.organization.core.configuration.OrganizationCoreConfiguration;
import sk.qbsw.organization.core.dao.OrganizationDao;
import sk.qbsw.organization.core.dao.UnitDao;
import sk.qbsw.organization.core.dao.UserDao;
import sk.qbsw.organization.management.service.UnitService;
import sk.qbsw.organization.management.service.UnitServiceImpl;
import sk.qbsw.organization.management.service.OrganizationService;
import sk.qbsw.organization.management.service.OrganizationServiceImpl;
import sk.qbsw.organization.management.service.UserService;
import sk.qbsw.organization.management.service.UserServiceImpl;

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
	public OrganizationService organizationService (OrganizationDao organizationDao)
	{
		return new OrganizationServiceImpl(organizationDao);
	}

	@Bean
	public UnitService unitService (UnitDao unitDao, UserDao userDao)
	{
		return new UnitServiceImpl(unitDao, userDao);
	}

	@Bean
	public UserService userService (UserDao userDao)
	{
		return new UserServiceImpl(userDao);
	}
}
