package sk.qbsw.security.organization.management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import sk.qbsw.organization.management.configuration.OrganizationManagementConfiguration;
import sk.qbsw.security.management.configuration.SecurityManagementConfiguration;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.organization.core.configuration.SecurityOrganizationCoreConfiguration;
import sk.qbsw.security.organization.core.dao.UserAccountDao;
import sk.qbsw.security.organization.management.service.UserAccountService;
import sk.qbsw.security.organization.management.service.UserAccountServiceImpl;

@Import({OrganizationManagementConfiguration.class, SecurityManagementConfiguration.class})
public class SecurityOrganizationManagementConfiguration extends SecurityOrganizationCoreConfiguration
{
	@Bean
	public UserAccountService userAccountService (AccountManagementService accountManagementService, UserAccountDao userAccountDao)
	{
		return new UserAccountServiceImpl(accountManagementService, userAccountDao);
	}
}
