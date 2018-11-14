package sk.qbsw.security.organization.complex.management.db.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.service.mapper.*;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.management.db.service.AccountCredentialManagementServiceImpl;
import sk.qbsw.security.management.db.service.AccountPermissionManagementServiceImpl;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.organization.complex.core.configuration.CXOSecurityCoreConfiguration;
import sk.qbsw.security.organization.complex.core.dao.CXOOrganizationDao;
import sk.qbsw.security.organization.complex.core.dao.CXOUnitDao;
import sk.qbsw.security.organization.complex.core.dao.CXOUserDao;
import sk.qbsw.security.organization.complex.core.service.mapper.CXOUserInputDataMapperImpl;
import sk.qbsw.security.organization.complex.core.service.mapper.CXOUserOutputDataMapperImpl;
import sk.qbsw.security.organization.complex.management.db.service.CXOAccountManagementServiceImpl;
import sk.qbsw.security.organization.complex.management.db.service.CXOOrganizationServiceImpl;
import sk.qbsw.security.organization.complex.management.db.service.CXOUnitServiceImpl;
import sk.qbsw.security.organization.complex.management.db.service.CXOUserServiceImpl;
import sk.qbsw.security.organization.complex.management.service.CXOOrganizationService;
import sk.qbsw.security.organization.complex.management.service.CXOUnitService;
import sk.qbsw.security.organization.complex.management.service.CXOUserService;

/**
 * The complex organization security management configuration.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class CXOSecurityManagementConfiguration extends CXOSecurityCoreConfiguration
{
	@Bean
	public CXOOrganizationService organizationService (CXOOrganizationDao organizationComplexDao)
	{
		return new CXOOrganizationServiceImpl(organizationComplexDao);
	}

	@Bean
	public CXOUnitService unitService (CXOUnitDao unitDao, CXOUserDao userDao)
	{
		return new CXOUnitServiceImpl(unitDao, userDao);
	}

	@Bean
	public CXOUserService userService (CXOUserDao userDao)
	{
		return new CXOUserServiceImpl(userDao);
	}

	@Bean
	public UserInputDataMapper userInputDataMapper ()
	{
		return new CXOUserInputDataMapperImpl();
	}

	@Bean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new CXOUserOutputDataMapperImpl();
	}

	@Bean
	public AccountInputDataMapper<AccountInputData, Account> accountInputDataMapper (UserInputDataMapper userInputDataMapper)
	{
		return new AccountInputDataMapperImpl(userInputDataMapper);
	}

	@Bean
	public AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper (UserOutputDataMapper userOutputDataMapper)
	{
		return new AccountOutputDataMapperImpl(userOutputDataMapper);
	}

	@Bean
	public AccountCredentialManagementService accountCredentialManagementService (AccountDao<Account> accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		return new AccountCredentialManagementServiceImpl(accountDao, authenticationParamsDao, digester, securityCoreConfigurator);
	}

	@Bean
	public AccountManagementService<AccountInputData, AccountData> accountManagementService (AccountDao<Account> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<AccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper, CXOUnitDao unitDao)
	{
		return new CXOAccountManagementServiceImpl(accountDao, userDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper, unitDao);
	}

	@Bean
	public AccountPermissionManagementService accountPermissionManagementService (AccountDao<Account> accountDao, GroupDao groupDao, UnitDao unitDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		return new AccountPermissionManagementServiceImpl(accountDao, groupDao, unitDao, accountUnitGroupDao);
	}
}
