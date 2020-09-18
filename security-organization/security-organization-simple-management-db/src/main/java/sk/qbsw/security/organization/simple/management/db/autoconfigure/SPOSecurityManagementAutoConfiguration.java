package sk.qbsw.security.organization.simple.management.db.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.autoconfigure.SecurityCoreAutoConfiguration;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.*;
import sk.qbsw.security.management.db.autoconfigure.SecurityManagementAutoConfigurationCommon;
import sk.qbsw.security.management.db.service.*;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.organization.simple.core.service.mapper.SPOUserOutputDataMapperImpl;
import sk.qbsw.security.organization.simple.management.SPOAccountManagementService;
import sk.qbsw.security.organization.simple.management.db.service.SPOAccountManagementServiceImpl;

/**
 * The simple organization security management auto configuration.
 * 
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.6.0
 * @since 2.0.0
 */
@Configuration
@AutoConfigureAfter ({SecurityCoreAutoConfiguration.class, SecurityManagementAutoConfigurationCommon.class})
public class SPOSecurityManagementAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public UserInputDataMapper userInputDataMapper ()
	{
		return new UserInputDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new SPOUserOutputDataMapperImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public SPOAccountManagementService<AccountInputData, AccountData> accountManagementService (AccountDao<Account> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<AccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		return new SPOAccountManagementServiceImpl(accountDao, userDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public AccountUnitGroupService accountUnitGroupService (AccountUnitGroupDao accountUnitGroupDao)
	{
		return new AccountUnitGroupServiceImpl(accountUnitGroupDao);
	}

	@Bean
	@ConditionalOnMissingBean
	public OrganizationService organizationService (OrganizationDao organizationDao, AccountPermissionManagementService accountPermissionManagementService)
	{
		return new OrganizationServiceImpl(organizationDao, accountPermissionManagementService);
	}

	@Bean
	@ConditionalOnMissingBean
	public UnitService unitService (UnitDao unitDao)
	{
		return new UnitServiceImpl(unitDao);
	}
}
