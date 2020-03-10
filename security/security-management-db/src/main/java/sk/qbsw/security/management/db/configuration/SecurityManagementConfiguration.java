package sk.qbsw.security.management.db.configuration;

import org.springframework.context.annotation.Bean;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.core.security.base.model.GroupOutputData;
import sk.qbsw.security.core.configuration.SecurityCoreConfiguration;
import sk.qbsw.security.core.configuration.SecurityCoreConfigurator;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.service.mapper.*;
import sk.qbsw.security.core.service.signature.PasswordDigester;
import sk.qbsw.security.management.db.dao.GroupManageableDao;
import sk.qbsw.security.management.db.dao.GroupManageableJpaDao;
import sk.qbsw.security.management.db.mapper.GroupManageableOutputDataMapperImpl;
import sk.qbsw.security.management.db.model.domain.GroupManageable;
import sk.qbsw.security.management.db.service.*;
import sk.qbsw.security.management.model.GroupManageableOutputData;
import sk.qbsw.security.management.service.AccountCredentialManagementService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.management.service.GroupManagementService;

/**
 * The security management configuration.
 * 
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.0.0
 */
public class SecurityManagementConfiguration extends SecurityCoreConfiguration
{
	@Bean
	public UserInputDataMapper userInputDataMapper ()
	{
		return new UserInputDataMapperImpl();
	}

	@Bean
	public UserOutputDataMapper userOutputDataMapper ()
	{
		return new UserOutputDataMapperImpl();
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
	public RoleOutputDataMapper roleOutputDataMapper ()
	{
		return new RoleOutputDataMapperImpl();
	}

	@Bean
	public GroupOutputDataMapper<GroupOutputData, Group> groupOutputDataMapper (RoleOutputDataMapper roleOutputDataMapper)
	{
		return new GroupOutputDataMapperImpl(roleOutputDataMapper);
	}

	@Bean
	public GroupOutputDataMapper<GroupManageableOutputData, GroupManageable> groupManageableOutputDataMapper (RoleOutputDataMapper roleOutputDataMapper, UserOutputDataMapper userOutputDataMapper)
	{
		return new GroupManageableOutputDataMapperImpl(roleOutputDataMapper, userOutputDataMapper);
	}

	@Bean
	public AccountCredentialManagementService accountCredentialManagementService (AccountDao<Account> accountDao, AuthenticationParamsDao authenticationParamsDao, PasswordDigester digester, SecurityCoreConfigurator securityCoreConfigurator)
	{
		return new AccountCredentialManagementServiceImpl(accountDao, authenticationParamsDao, digester, securityCoreConfigurator);
	}

	@Bean
	public AccountManagementService<AccountInputData, AccountData> accountManagementService (AccountDao<Account> accountDao, UserDao userDao, OrganizationDao organizationDao, AuthenticationParamsDao authenticationParamsDao, AccountCredentialManagementService authenticationService, AccountInputDataMapper<AccountInputData, Account> accountAccountInputDataMapper, AccountOutputDataMapper<AccountData, Account> accountOutputDataMapper)
	{
		return new AccountManagementServiceImpl(accountDao, userDao, organizationDao, authenticationParamsDao, authenticationService, accountAccountInputDataMapper, accountOutputDataMapper);
	}

	@Bean
	public AccountPermissionManagementService accountPermissionManagementService (AccountDao<Account> accountDao, GroupDao groupDao, UnitDao unitDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		return new AccountPermissionManagementServiceImpl(accountDao, groupDao, unitDao, accountUnitGroupDao);
	}

	@Bean
	public AccountUnitGroupService accountUnitGroupService (AccountUnitGroupDao accountUnitGroupDao)
	{
		return new AccountUnitGroupServiceImpl(accountUnitGroupDao);
	}

	@Bean
	public GroupManageableDao groupManageableDao ()
	{
		return new GroupManageableJpaDao();
	}

	@Bean
	public GroupManagementService groupManagementService (GroupManageableDao groupManageableDao, RoleDao roleDao, UserDao userDao, GroupOutputDataMapper<GroupManageableOutputData, GroupManageable> groupManageableOutputDataMapper)
	{
		return new GroupManageableServiceImpl(groupManageableDao, roleDao, userDao, groupManageableOutputDataMapper);
	}

	@Bean
	public LoginBlockingService loginBlockingService (BlockedLoginDao blockedLoginJpaDao, AccountDao accountDao)
	{
		return new LoginBlockingServiceImpl(blockedLoginJpaDao, accountDao);
	}

	@Bean
	public OrganizationService organizationService (OrganizationDao organizationDao, AccountPermissionManagementService accountPermissionManagementService)
	{
		return new OrganizationServiceImpl(organizationDao, accountPermissionManagementService);
	}

	@Bean
	public UnitService unitService (UnitDao unitDao)
	{
		return new UnitServiceImpl(unitDao);
	}
}
