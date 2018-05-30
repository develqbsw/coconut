package sk.qbsw.security.management.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.management.service.*;

/**
 * The security management configuration.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SecurityManagementConfiguration extends SecurityCoreConfiguration
{
	@Bean
	public AccountCredentialManagementService accountCredentialManagementService (AccountDao accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		return new AccountCredentialManagementServiceImpl(accountDao, authenticationParamsDao, digester, securityCoreConfigurator);
	}

	@Bean
	public AccountManagementService accountManagementService (AccountDao accountDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService)
	{
		return new AccountManagementServiceImpl(accountDao, organizationDao, authenticationParamsDao, authenticationService);
	}

	@Bean
	public AccountPermissionManagementService accountPermissionManagementService (AccountDao accountDao, GroupDao groupDao, UnitDao unitDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		return new AccountPermissionManagementServiceImpl(accountDao, groupDao, unitDao, accountUnitGroupDao);
	}

	@Bean
	public AccountUnitGroupService accountUnitGroupService (AccountUnitGroupDao accountUnitGroupDao)
	{
		return new AccountUnitGroupServiceImpl(accountUnitGroupDao);
	}

	@Bean
	public GroupService groupService (GroupDao groupDao)
	{
		return new GroupServiceImpl(groupDao);
	}

	@Bean
	public LoginBlockingService loginBlockingService (BlockedLoginDao blockedLoginJpaDao, AccountDao accountDao)
	{
		return new LoginBlockingServiceImpl(blockedLoginJpaDao, accountDao);
	}

	@Bean
	public OrganizationService organizationService (OrganizationDao organizationDao, GroupService groupService, AccountPermissionManagementService accountPermissionManagementService)
	{
		return new OrganizationServiceImpl(organizationDao, groupService, accountPermissionManagementService);
	}

	@Bean
	public UnitService unitService (UnitDao unitDao)
	{
		return new UnitServiceImpl(unitDao);
	}
}
