package sk.qbsw.security.organization.simple.management.db.configuration;

import org.springframework.context.annotation.Bean;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.AccountInputDataMapper;
import sk.qbsw.security.core.service.mapper.AccountOutputDataMapper;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountData;
import sk.qbsw.security.organization.simple.base.model.SimpleOrganizationAccountInputData;
import sk.qbsw.security.organization.simple.core.service.mapper.SimpleOrganizationInputDataAccountMapperImpl;
import sk.qbsw.security.organization.simple.core.service.mapper.SimpleOrganizationOutputDataAccountMapperImpl;
import sk.qbsw.security.organization.simple.management.db.service.SimpleOrganizationAccountCredentialManagementServiceImpl;
import sk.qbsw.security.organization.simple.management.db.service.SimpleOrganizationAccountManagementServiceImpl;
import sk.qbsw.security.organization.simple.management.db.service.SimpleOrganizationAccountPermissionManagementServiceImpl;

/**
 * The security management configuration.
 * 
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class SecuritySimpleOrganizationManagementConfiguration extends SecurityCoreConfiguration
{
	@Bean
	public AccountInputDataMapper<SimpleOrganizationAccountInputData, Account> accountInputDataMapper ()
	{
		return new SimpleOrganizationInputDataAccountMapperImpl();
	}

	@Bean
	public AccountOutputDataMapper<SimpleOrganizationAccountData, Account> accountOutputDataMapper ()
	{
		return new SimpleOrganizationOutputDataAccountMapperImpl();
	}

	@Bean
	public AccountCredentialManagementService accountCredentialManagementService (AccountDao<Account> accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		return new SimpleOrganizationAccountCredentialManagementServiceImpl(accountDao, authenticationParamsDao, digester, securityCoreConfigurator);
	}

	@Bean
	public AccountManagementService<SimpleOrganizationAccountInputData, SimpleOrganizationAccountData> accountManagementService (AccountDao<Account> accountDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<SimpleOrganizationAccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<SimpleOrganizationAccountData, Account> accountOutputDataMapper)
	{
		return new SimpleOrganizationAccountManagementServiceImpl(accountDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}

	@Bean
	public AccountPermissionManagementService accountPermissionManagementService (AccountDao accountDao, GroupDao groupDao, UnitDao unitDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		return new SimpleOrganizationAccountPermissionManagementServiceImpl(accountDao, groupDao, unitDao, accountUnitGroupDao);
	}
}
